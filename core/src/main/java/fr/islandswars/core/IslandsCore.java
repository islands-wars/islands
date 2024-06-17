package fr.islandswars.core;

import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.BarManager;
import fr.islandswars.api.command.CommandManager;
import fr.islandswars.api.inventory.item.ItemManager;
import fr.islandswars.api.locale.Translatable;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.api.log.internal.Server;
import fr.islandswars.api.log.internal.ServerLog;
import fr.islandswars.api.module.Module;
import fr.islandswars.api.module.bukkit.ItemModule;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.ScoreboardManager;
import fr.islandswars.api.server.ServerType;
import fr.islandswars.api.task.UpdaterManager;
import fr.islandswars.api.utils.ReflectionUtil;
import fr.islandswars.commons.service.rabbitmq.RabbitMQConnection;
import fr.islandswars.commons.service.rabbitmq.packet.server.StatusRequestPacket;
import fr.islandswars.commons.service.redis.RedisConnection;
import fr.islandswars.commons.utils.DatabaseError;
import fr.islandswars.commons.utils.LogUtils;
import fr.islandswars.core.bukkit.bossbar.InternalBarManager;
import fr.islandswars.core.bukkit.command.InternalCommandManager;
import fr.islandswars.core.bukkit.command.PingCommand;
import fr.islandswars.core.bukkit.item.InternalItemManager;
import fr.islandswars.core.bukkit.scoreboard.InternalScoreboardManager;
import fr.islandswars.core.bukkit.task.TaskManager;
import fr.islandswars.core.internal.InternalServer;
import fr.islandswars.core.internal.listener.ItemListener;
import fr.islandswars.core.internal.listener.PlayerListener;
import fr.islandswars.core.internal.locale.TranslationLoader;
import fr.islandswars.core.internal.log.InternalLogger;
import fr.islandswars.core.internal.mq.IslandsExchange;
import fr.islandswars.core.player.InternalPlayer;
import fr.islandswars.core.player.PlayerRank;
import org.apache.logging.log4j.Level;
import org.bukkit.NamespacedKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * File <b>IslandsCore</b> located on fr.islandswars.core
 * IslandsCore is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2024 Islands Wars.
 * <p>
 * islands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Jangliu, {@literal <jangliu@islandswars.fr>}
 * Created the 23/03/2024 at 20:19
 * @since 0.1
 */
public class IslandsCore extends IslandsApi {

    private static final Logger                    log = LoggerFactory.getLogger(IslandsCore.class);
    private final        List<IslandsPlayer>       players;
    private final        List<Module>              modules;
    private final        TranslationLoader         translatable;
    private final        TaskManager               taskManager;
    private final        InternalLogger            logger;
    private final        InternalItemManager       itemManager;
    private final        InternalCommandManager    commandManager;
    private final        RedisConnection           redis;
    private final        RabbitMQConnection        rabbit;
    private final        InternalServer            server;
    private              IslandsExchange           isExchange;
    private              InternalScoreboardManager scoreboardManager;
    private              NamespacedKey             key;
    private              BarManager                barManager;

    public IslandsCore() {
        this.players = new CopyOnWriteArrayList<>();
        this.modules = new ArrayList<>();
        this.translatable = new TranslationLoader();
        this.itemManager = new InternalItemManager();
        this.commandManager = new InternalCommandManager();
        this.taskManager = new TaskManager();
        this.logger = new InternalLogger();
        this.redis = new RedisConnection();
        this.rabbit = new RabbitMQConnection();
        var type = ServerType.valueOf(System.getenv("SERVER_TYPE"));
        var id   = UUID.fromString(System.getenv("SERVER_ID"));
        this.server = new InternalServer(StatusRequestPacket.ServerStatus.STARTED, type, id);
    }

    @Override
    public void onLoad() {
        LogUtils.setErrorConsummer(logger::logError);
        try {
            redis.load();
            redis.connect();
            rabbit.load();
            rabbit.connect();
        } catch (Exception e) {
            logger.logError(e);
            getServer().shutdown();
        }
        this.isExchange = new IslandsExchange(rabbit, this);
        setServerStatus(StatusRequestPacket.ServerStatus.STARTED);
        modules.forEach(Module::onLoad);
        this.barManager = registerModule(InternalBarManager.class);
        this.scoreboardManager = registerModule(InternalScoreboardManager.class);
        registerModule(ItemModule.class);
        translatable.load("locale.core");
    }

    @Override
    public void onDisable() {
        modules.forEach(Module::onDisable);
        try {
            redis.close();
            rabbit.close();
        } catch (Exception e) {
            logger.logError(e);
        }
        setServerStatus(StatusRequestPacket.ServerStatus.SHUTDOWN); //TODO stopped....
    }

    @Override
    public void onEnable() {
        modules.forEach(Module::onEnable);
        taskManager.register(new PlayerListener(this, redis));
        new ItemListener(this);
        new PingCommand("ping", this, PlayerRank.PLAYER);
        if (!redis.isClosed() && !rabbit.isClosed()) setServerStatus(StatusRequestPacket.ServerStatus.READY);
        else {
            logger.log(Level.ERROR, "Database offline...");
            getServer().shutdown();
        }
        injectData();
        testrmq();
    }

    @Override
    public InfraLogger getInfraLogger() {
        return logger;
    }

    @Override
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public List<? extends IslandsPlayer> getPlayers() {
        return players;
    }

    @Override
    public Optional<IslandsPlayer> getPlayer(UUID playerId) {
        return players.stream().filter(p -> {
            if (p.getBukkitPlayer().isPresent()) return p.getBukkitPlayer().get().getUniqueId().equals(playerId);
            else return false;
        }).findFirst();
    }

    @Override
    public UpdaterManager getUpdaterManager() {
        return taskManager;
    }

    @Override
    public BarManager getBarManager() {
        return barManager;
    }

    @Override
    public Translatable getTranslatable() {
        return translatable;
    }

    @Override
    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    @Override
    public ItemManager getItemManager() {
        return itemManager;
    }

    @Override
    public NamespacedKey getKey() {
        return key == null ? key = new NamespacedKey(this, "is") : key;
    }

    public void addPlayer(IslandsPlayer player) {
        players.add(player);
    }

    public void removePlayer(IslandsPlayer player) {
        players.remove(player);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Module> Optional<T> getModule(Class<T> module) {
        return (Optional<T>) modules.stream().filter(m -> m.getClass().equals(module)).findFirst();
    }

    @Override
    public <T extends Module> T registerModule(Class<T> module) {
        try {
            T mod = ReflectionUtil.getConstructorAccessor(module, IslandsApi.class).newInstance(IslandsApi.getInstance());
            if (isEnabled()) {
                mod.onLoad();
                mod.onEnable();
            }
            modules.add(mod);
            logger.logDebug("Register Module on " + module.getName());
            return mod;
        } catch (Exception e) {
            getInfraLogger().logError(e);
            return null;
        }
    }

    @Override
    public <T extends Module> void unregisterModule(Class<T> module) {
        Optional<? extends Module> optionalModule = modules.stream().filter(m -> m.getClass().equals(module)).findFirst();
        optionalModule.ifPresent(mod -> {
            mod.onDisable();
            modules.remove(mod);
        });
    }

    public void setServerStatus(StatusRequestPacket.ServerStatus status) {
        redis.getConnection().set(server.getId().toString() + ":" + server.getServerType().toString(), status.toString()).whenCompleteAsync((r, t) -> {
            if (t != null) {
                logger.logError(new DatabaseError(t.getMessage(), t));
            } else {
                server.setStatus(status);
                var serverLog = new Server(status.toString(), server.getServerType(), server.getId());
                getInfraLogger().createCustomLog(ServerLog.class, Level.INFO, "Server is now in " + status.toString() + " state.").setServer(serverLog).log();
            }
        });
    }

    @Override
    public void stop() {
        //TODO close properly connection and player
        getServer().shutdown();
    }

    public IslandsExchange getIsExchange() {
        return isExchange;
    }

    public InternalServer getInternalServer() {
        return server;
    }

    //TODO remove
    private void injectData() {
        var uuid = UUID.fromString("44aa2d56-5257-44ff-9efc-60ec11f78f39");
        var p    = new InternalPlayer();
        p.setUuid(uuid);
        p.setRanks(List.of("ADMIN"));
        var gson = new Gson();
        var json = gson.toJson(p);
        redis.getConnection().set("44aa2d56-5257-44ff-9efc-60ec11f78f39:player", json);
    }

    //TODO remove
    private void testrmq() {
        var channel = rabbit.getConnection();
        try {
            final String              EXCHANGE_NAME = "islands";
            final BuiltinExchangeType EXCHANGE_TYPE = BuiltinExchangeType.TOPIC;
            final var                 queue         = "server";
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

            channel.basicPublish(EXCHANGE_NAME, queue + "." + server.getServerType().toString() + "." + server.getId(), null, "send to server.hub.1234".getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, queue + "." + server.getServerType().toString() + "." + UUID.randomUUID(), null, "should not be sent to server.hub.1234".getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, queue + "." + server.getServerType().toString() + ".all", null, "sent to all hub".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
