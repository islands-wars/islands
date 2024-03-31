package fr.islandswars.core.player;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.IslandsRank;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * File <b>InternalPlayer</b> located on fr.islandswars.core.player
 * InternalPlayer is a part of islands.
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
 * Created the 23/03/2024 at 22:33
 * @since 0.1
 */
public class InternalPlayer implements IslandsPlayer {

    private final transient Player       player;
    private final           List<String> ranks;

    public InternalPlayer(Player player) {
        this.player = player;
        if (player.getName().equals("Jangliu")) this.ranks = List.of("ADMIN");
        else this.ranks = List.of("PLAYER");
    }

    @Override
    public void disconnect() {
        player.kick();
    }

    @Override
    public IslandsRank getMainRank() {
        return PlayerRank.getHighest(ranks);
    }

    @Override
    public Player getBukkitPlayer() {
        return player;
    }
}
