package fr.islandswars.core.player.rank;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.core.player.PlayerRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

/**
 * File <b>BoardManager</b> located on fr.islandswars.core.player.rank
 * BoardManager is a part of islands.
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
 * Created the 24/03/2024 at 19:08
 * @since 0.1
 */
public class BoardManager {

    private static final Component             start = Component.text("[").color(TextColor.color(79, 79, 79)).decorate(TextDecoration.BOLD);
    private static final Component             end   = Component.text("]").color(TextColor.color(79, 79, 79)).decorate(TextDecoration.BOLD);
    private final        Scoreboard            board;
    private final        Map<PlayerRank, Team> ranks;

    public BoardManager() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.ranks = new HashMap<>();
        fillTeam();
    }

    private void fillTeam() {
        for (PlayerRank rank : PlayerRank.values()) {
            Team team = board.registerNewTeam(rank.name());
            team.prefix(start.append(Component.translatable(rank.getShortName()).color(rank.getRankColor())).append(end).appendSpace());
            ranks.put(rank, team);
        }
    }

    public void updatePlayer(IslandsPlayer player) {
        unregisterPlayer(player);
        registerPlayer(player);
    }

    public void unregisterPlayer(IslandsPlayer player) {
        ranks.get((PlayerRank) player.getMainRank()).removePlayer(player.getBukkitPlayer());
    }

    public void registerPlayer(IslandsPlayer player) {
        ranks.get((PlayerRank) player.getMainRank()).addPlayer(player.getBukkitPlayer());
        player.getBukkitPlayer().setScoreboard(board);
    }

}
