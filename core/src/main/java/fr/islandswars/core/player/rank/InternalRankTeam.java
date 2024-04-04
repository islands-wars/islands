package fr.islandswars.core.player.rank;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.RankTeam;
import fr.islandswars.api.scoreboard.team.IslandsTeam;
import fr.islandswars.core.bukkit.scoreboard.InternalScoreboardManager;
import fr.islandswars.core.player.PlayerRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * File <b>InternalRankTeam</b> located on fr.islandswars.core.player.rank
 * InternalRankTeam is a part of islands.
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
 * Created the 31/03/2024 at 18:46
 * @since 0.1
 */
public class InternalRankTeam implements RankTeam {

    private static final Component                    start = Component.text("[").color(TextColor.color(79, 79, 79)).decorate(TextDecoration.BOLD);
    private static final Component                    end   = Component.text("]").color(TextColor.color(79, 79, 79)).decorate(TextDecoration.BOLD);
    private final        Map<PlayerRank, IslandsTeam> ranks;

    public InternalRankTeam(InternalScoreboardManager manager) {
        this.ranks = new HashMap<>();
        fillTeam(manager);
    }

    private void fillTeam(InternalScoreboardManager manager) {
        for (PlayerRank rank : PlayerRank.values()) {
            IslandsTeam team = manager.createNewTeam(rank.name());
            team.setPrefix(start.append(Component.translatable(rank.getShortName()).color(rank.getRankColor())).append(end).appendSpace());
            ranks.put(rank, team);
        }
    }

    public void unregisterPlayer(IslandsPlayer player) {
        ranks.get((PlayerRank) player.getMainRank()).removePlayer(player);
    }

    public void registerPlayer(IslandsPlayer player) {
        ranks.get((PlayerRank) player.getMainRank()).addPlayer(player);
    }

    @Override
    public Collection<IslandsTeam> getTeams() {
        return ranks.values();
    }
}
