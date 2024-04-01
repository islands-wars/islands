package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.module.Module;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.RankTeam;
import fr.islandswars.api.scoreboard.IslandsBoard;
import fr.islandswars.api.scoreboard.ScoreboardManager;
import fr.islandswars.api.scoreboard.team.IslandsTeam;
import fr.islandswars.api.task.TaskType;
import fr.islandswars.api.task.TimeType;
import fr.islandswars.api.task.Updater;
import fr.islandswars.api.utils.Preconditions;
import fr.islandswars.core.bukkit.scoreboard.team.InternalTeam;
import fr.islandswars.core.player.rank.InternalRankTeam;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * File <b>InternalScoreboardManager</b> located on fr.islandswars.core.bukkit.scoreboard
 * InternalScoreboardManager is a part of islands.
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
 * Created the 31/03/2024 at 18:25
 * @since 0.1
 */
public class InternalScoreboardManager extends Module implements ScoreboardManager {

    private final InternalRankTeam         rankTeam;
    private final List<InternalTeam>       teams;
    private final List<InternalScoreboard> boards;
    private       int                      count;

    public InternalScoreboardManager(IslandsApi api) {
        super(api);
        this.teams = new ArrayList<>();
        this.boards = new ArrayList<>();
        this.rankTeam = new InternalRankTeam(this);
        this.count = 0;
    }

    @Override
    public void onDisable() {
        api.getUpdaterManager().stop(this);
    }

    @Override
    public void onEnable() {
        api.getUpdaterManager().register(this);
    }

    @Override
    public void onLoad() {
    }

    @Override
    public IslandsBoard createNewScoreboard() {
        var board = new InternalScoreboard(count++, this);
        boards.add(board);
        return board;
    }

    @Override
    public IslandsTeam createNewTeam(String name) {
        Preconditions.checkState(teams, (ref) -> ref.stream().noneMatch(t -> t.getName().equalsIgnoreCase(name)), "Team " + name + " already registered.");
        var team = new InternalTeam(name, this);
        teams.add(team);
        return team;
    }

    @Override
    public RankTeam getRankTeam() {
        return rankTeam;
    }

    @Override
    public void release(IslandsBoard board) {
        boards.remove((InternalScoreboard) board);
    }

    @Override
    public void release(IslandsPlayer player) {
        teams.forEach(t -> t.removePlayer(player));
        boards.forEach(b -> b.removePlayer(player));
    }

    public void update(InternalTeam team) {
        boards.forEach(board -> board.update(team));
    }

    public void addPlayer(InternalTeam team, IslandsPlayer player) {
        boards.forEach(board -> board.updateAddPlayer(team, player));
    }

    public void removePlayer(InternalTeam team, IslandsPlayer player) {
        boards.forEach(board -> board.updateRemovePlayer(team, player));
    }

    public void buildTeam(Scoreboard board, InternalTeam team) {
        var boardTeam = board.registerNewTeam(team.getName());
        boardTeam.prefix(team.getPrefix());
        boardTeam.suffix(team.getSuffix());
        boardTeam.color(team.getColor());
        boardTeam.displayName(team.getDisplayName());
        team.getMembers().forEach(ip -> boardTeam.addPlayer(ip.getBukkitPlayer()));
    }

    @Updater(type = TaskType.SYNC, time = TimeType.TICK, delta = 1)
    public void updateBoard() {
        boards.forEach(InternalScoreboard::updateDisplay);
    }
}
