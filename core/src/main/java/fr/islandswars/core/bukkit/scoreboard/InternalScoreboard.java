package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.IslandsBoard;
import fr.islandswars.api.scoreboard.team.IslandsTeam;
import fr.islandswars.core.bukkit.scoreboard.team.InternalTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * File <b>InternalScoreboard</b> located on fr.islandswars.core.bukkit.scoreboard
 * InternalScoreboard is a part of islands.
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
 * Created the 31/03/2024 at 18:26
 * @since 0.1
 */
public class InternalScoreboard implements IslandsBoard {

    private final int                       id;
    private final Scoreboard                board;
    private final InternalScoreboardManager manager;
    private final List<IslandsTeam>         teams;
    private final List<IslandsPlayer>       viewers;

    public InternalScoreboard(int id, InternalScoreboardManager manager) {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.viewers = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.manager = manager;
        this.id = id;
    }

    @Override
    public IslandsBoard addLine(Component component) {
        return null;
    }

    @Override
    public void addPlayer(IslandsPlayer player) {
        viewers.add(player);
        player.getBukkitPlayer().setScoreboard(board);
    }

    @Override
    public void removePlayer(IslandsPlayer player) {
        viewers.remove(player);
    }

    @Override
    public void registerTeam(IslandsTeam team) {
        teams.add(team);
        update((InternalTeam) team);
    }

    public void update(InternalTeam team) {
        if (teams.contains(team)) {
            var bukkitTeam = board.getTeam(team.getName());
            if (bukkitTeam == null) {
                manager.buildTeam(board, team);
            } else {
                if (!bukkitTeam.prefix().equals(team.getPrefix())) bukkitTeam.prefix(team.getPrefix());
                if (!bukkitTeam.suffix().equals(team.getSuffix())) bukkitTeam.suffix(team.getSuffix());
                if (!bukkitTeam.displayName().equals(team.getDisplayName())) bukkitTeam.displayName(team.getDisplayName());
                if (!bukkitTeam.color().equals(team.getColor())) bukkitTeam.color(team.getColor());
            }
        }

    }

    public void updateAddPlayer(InternalTeam team, IslandsPlayer player) {
        if (teams.contains(team)) {
            var bukkitTeam = board.getTeam(team.getName());
            if (bukkitTeam != null) bukkitTeam.addPlayer(player.getBukkitPlayer());
        }
    }

    public void updateRemovePlayer(InternalTeam team, IslandsPlayer player) {
        if (teams.contains(team)) {
            var bukkitTeam = board.getTeam(team.getName());
            if (bukkitTeam != null) bukkitTeam.removePlayer(player.getBukkitPlayer());
        }
    }
}
