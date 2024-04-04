package fr.islandswars.api.player;

import fr.islandswars.api.player.rank.IslandsRank;
import org.bukkit.entity.Player;

/**
 * File <b>IslandsPlayer</b> located on fr.islandswars.api.player
 * IslandsPlayer is a part of islands.
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
 * Created the 23/03/2024 at 22:24
 * @since 0.1
 */
public interface IslandsPlayer {

    void disconnect();

    IslandsRank getMainRank();

    Player getBukkitPlayer();

}
