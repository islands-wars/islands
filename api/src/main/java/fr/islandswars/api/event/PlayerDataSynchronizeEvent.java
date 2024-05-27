package fr.islandswars.api.event;

import fr.islandswars.api.player.IslandsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * File <b>PlayerDataSynchronizeEvent</b> located on fr.islandswars.api.event
 * PlayerDataSynchronizeEvent is a part of islands.
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
 * Created the 24/03/2024 at 02:18
 * @since 0.1
 */
public class PlayerDataSynchronizeEvent extends Event {
    
    private static final HandlerList   HANDLERS = new HandlerList();
    private final        IslandsPlayer player;

    public PlayerDataSynchronizeEvent(IslandsPlayer player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public IslandsPlayer getPlayer() {
        return player;
    }
}
