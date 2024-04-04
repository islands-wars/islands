package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.IslandsBoard;
import fr.islandswars.api.scoreboard.IslandsObjective;
import fr.islandswars.api.scoreboard.team.IslandsTeam;
import fr.islandswars.api.utils.Preconditions;
import fr.islandswars.core.bukkit.scoreboard.team.InternalTeam;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private       InternalObjective         objective;
    private       int                       index  = 0;
    private       boolean                   update = false;

    private int pause                  = 0;
    private int countTitle, countScore = 0;
    private int deltaTitle, deltaScore = 0;

    public InternalScoreboard(int id, InternalScoreboardManager manager) {
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        this.viewers = new ArrayList<>();
        this.teams = new ArrayList<>();
        this.manager = manager;
        this.id = id;
    }

    @Override
    public IslandsObjective createObjective(String name, List<Component> title) {
        Preconditions.checkState(title.size(), (ref) -> ref > 0);

        var bukkitObjective = board.registerNewObjective(name, Criteria.DUMMY, title.get(0));
        this.objective = new InternalObjective(bukkitObjective, title);
        return objective;
    }

    @Override
    public void addPlayer(IslandsPlayer player) {
        viewers.add(player);
        player.getBukkitPlayer().setScoreboard(board);
        if (objective != null) objective.render(player);
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

    @Override
    public void updateDisplay(boolean status, int deltaTitle, int deltaScore, int pause) {
        this.update = status;
        this.deltaTitle = deltaTitle;
        this.deltaScore = deltaScore;
        this.pause = pause;
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

    public void updateDisplay() {
        getObjective().ifPresent((obj) -> {
            if (update) {
                int totalTime = obj.title.size() * deltaTitle + pause;
                countTitle++;
                if (countTitle > totalTime - pause + 1) {
                    if (!obj.getObjective().displayName().equals(obj.title.get(0))) {
                        index = 0;
                        obj.getObjective().displayName(obj.title.get(index));
                    }
                    if (countTitle >= totalTime) {
                        countTitle = 0;
                        index = 0;
                    }
                } else if (countTitle % deltaTitle == 0) obj.getObjective().displayName(obj.title.get(index++));
                countScore++;
                if (countScore % deltaScore == 0) {
                    viewers.forEach(player -> obj.scores.forEach((name, line) -> {
                        var score = obj.getObjective().getScore(name);
                        score.customName(line.apply(player));
                    }));
                    countScore = 0;
                }
            }
        });
    }

    public Optional<InternalObjective> getObjective() {
        return Optional.ofNullable(objective);
    }
}
