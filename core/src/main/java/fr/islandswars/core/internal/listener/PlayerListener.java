package fr.islandswars.core.internal.listener;

import com.google.gson.Gson;
import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.event.PlayerDataSynchronizeEvent;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.commons.service.redis.RedisConnection;
import fr.islandswars.commons.utils.DatabaseError;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.player.InternalPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * File <b>PlayerListener</b> located on fr.islandswars.core.internal.listener
 * PlayerListener is a part of islands.
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
 * Created the 23/03/2024 at 23:21
 * @since 0.1
 */
public class PlayerListener extends LazyListener {

    private final RedisConnection redis;
    private final Gson            gson;

    public PlayerListener(IslandsApi api, RedisConnection redis) {
        super(api);
        this.redis = redis;
        this.gson = new Gson();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        retrievePlayerData(event.getUniqueId(), 0);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onConnect(PlayerJoinEvent event) {
        event.joinMessage(Component.empty());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onIslandsPlayerJoin(PlayerDataSynchronizeEvent event) {
        getLogger().logInfo("New player with rank : " + event.getPlayer().getMainRank() + " join the game...");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        var islandsPlayer = getOptionalPlayer(event.getPlayer());
        islandsPlayer.ifPresent(p -> api.getScoreboardManager().release(p));
        islandsPlayer.ifPresent(p -> api.getBarManager().unregisterPlayer(p));
        islandsPlayer.ifPresent(((IslandsCore) api)::removePlayer);
        event.quitMessage(Component.empty());
    }

    private void retrievePlayerData(UUID uuid, int count) {
        redis.getConnection().get(uuid + ":player").whenCompleteAsync((r, t) -> {
            if (t != null) {
                getLogger().logError(new DatabaseError(t.getMessage(), t));
                Bukkit.getScheduler().runTask(api, () -> kickPlayer(uuid)); //ensure thread safety, maybe overkill ?
            } else if (r != null) {
                var player = gson.fromJson(r, InternalPlayer.class);

                ((IslandsCore) api).addPlayer(player);
                Bukkit.getScheduler().runTask(api, () -> {
                    Bukkit.getPluginManager().callEvent(new PlayerDataSynchronizeEvent(player));
                });
            } else Bukkit.getScheduler().runTaskLaterAsynchronously(api, () -> {
                if (count >= 5) Bukkit.getScheduler().runTask(api, () -> kickPlayer(uuid));
                else retrievePlayerData(uuid, count + 1);
            }, 1);
        });
    }

    private void kickPlayer(UUID uuid) {
        var player = Bukkit.getServer().getPlayer(uuid);
        if (player != null) player.kick(Component.text("Database error..."));
        else Bukkit.getScheduler().runTaskLater(api, () -> kickPlayer(uuid), 5);
    }
}
