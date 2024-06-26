package fr.islandswars.core;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.BarManager;
import fr.islandswars.api.command.CommandManager;
import fr.islandswars.api.inventory.item.ItemManager;
import fr.islandswars.api.locale.Translatable;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.api.log.internal.Status;
import fr.islandswars.api.module.Module;
import fr.islandswars.api.module.bukkit.ItemModule;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.ScoreboardManager;
import fr.islandswars.api.task.UpdaterManager;
import fr.islandswars.api.utils.ReflectionUtil;
import fr.islandswars.core.bukkit.bossbar.InternalBarManager;
import fr.islandswars.core.bukkit.command.InternalCommandManager;
import fr.islandswars.core.bukkit.command.PingCommand;
import fr.islandswars.core.bukkit.item.InternalItemManager;
import fr.islandswars.core.bukkit.scoreboard.InternalScoreboardManager;
import fr.islandswars.core.bukkit.task.TaskManager;
import fr.islandswars.core.internal.listener.ItemListener;
import fr.islandswars.core.internal.listener.PlayerListener;
import fr.islandswars.core.internal.locale.TranslationLoader;
import fr.islandswars.core.internal.log.InternalLogger;
import fr.islandswars.core.player.PlayerRank;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final List<IslandsPlayer> players;
    private final List<Module> modules;
    private final TranslationLoader translatable;
    private final TaskManager taskManager;
    private final InternalLogger logger;
    private final InternalItemManager itemManager;
    private final InternalCommandManager commandManager;
    private InternalScoreboardManager scoreboardManager;
    private NamespacedKey key;
    private BarManager barManager;

    public IslandsCore() {
        this.players = new ArrayList<>();
        this.modules = new ArrayList<>();
        this.translatable = new TranslationLoader();
        this.itemManager = new InternalItemManager();
        this.commandManager = new InternalCommandManager();
        this.taskManager = new TaskManager();
        this.logger = new InternalLogger();
    }

    @Override
    public void onLoad() {
        modules.forEach(Module::onLoad);
        this.barManager = registerModule(InternalBarManager.class);
        this.scoreboardManager = registerModule(InternalScoreboardManager.class);
        registerModule(ItemModule.class);
        translatable.load("locale.core");
        setServerStatus(Status.LOAD);
    }

    @Override
    public void onDisable() {
        modules.forEach(Module::onDisable);
        setServerStatus(Status.DISABLE);
    }

    @Override
    public void onEnable() {
        modules.forEach(Module::onEnable);
        new PlayerListener(this);
        new ItemListener(this);
        new PingCommand("ping", this, PlayerRank.PLAYER);
        setServerStatus(Status.ENABLE);
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
        return players.stream().filter(p -> p.getBukkitPlayer().getUniqueId().equals(playerId)).findFirst();
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
}
