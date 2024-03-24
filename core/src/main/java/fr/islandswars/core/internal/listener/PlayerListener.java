package fr.islandswars.core.internal.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.player.InternalPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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

    public PlayerListener(IslandsApi api) {
        super(api);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onConnect(PlayerJoinEvent event) {
        var p = new InternalPlayer(event.getPlayer());
        ((IslandsCore) api).addPlayer(p);
        event.getPlayer().sendMessage(Component.translatable("core.event.join.msg", p.getMainRank().getDisplayName()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        var islandsPlayer = api.getPlayer(event.getPlayer().getUniqueId());
        islandsPlayer.ifPresent(((IslandsCore) api)::removePlayer);
    }
}
