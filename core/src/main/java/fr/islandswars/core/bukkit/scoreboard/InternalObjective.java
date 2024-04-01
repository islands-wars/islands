package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.IslandsObjective;
import net.kyori.adventure.text.Component;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * File <b>InternalObjective</b> located on fr.islandswars.core.bukkit.scoreboard
 * InternalObjective is a part of islands.
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
 * Created the 01/04/2024 at 13:12
 * @since 0.1
 */
public class InternalObjective implements IslandsObjective {

    protected final Map<String, Function<IslandsPlayer, Component>> scores;
    protected final List<Component>                                 title;
    private final   Objective                                       objective;


    public InternalObjective(Objective objective, List<Component> title) {
        this.objective = objective;
        this.scores = new HashMap<>();
        this.title = title;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @Override
    public void addUpdatedLine(int id, String name, Function<IslandsPlayer, Component> line) {
        scores.put(name, line);
        var score = objective.getScore(name);
        score.setScore(id);
    }

    @Override
    public void addLine(int id, String name, Component line) {
        var score = objective.getScore(name);
        score.customName(line);
        score.setScore(id);
    }

    protected Objective getObjective() {
        return objective;
    }

    protected void render(IslandsPlayer player) {
        scores.forEach((name, line) -> {
            var score = objective.getScore(name);
            score.customName(line.apply(player));
        });
    }
}
