package fr.islandswars.core.internal.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.IslandsBoard;
import fr.islandswars.api.utils.GradientComponent;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.player.InternalPlayer;
import fr.islandswars.core.player.rank.InternalRankTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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

    private final InternalRankTeam rankTeam;
    private final IslandsBoard     board;

    public PlayerListener(IslandsApi api) {
        super(api);
        this.rankTeam = (InternalRankTeam) api.getScoreboardManager().getRankTeam();
        this.board = api.getScoreboardManager().createNewScoreboard();
        var obj = board.createObjective("main", GradientComponent.highlightCharacters("Islands Wars", TextColor.color(252, 194, 3), TextColor.color(103, 252, 3)));
        obj.addUpdatedLine(2, "test", (player) -> Component.translatable("core.event.join.msg", Component.text(player.getBukkitPlayer().getLocation().getX()).color(NamedTextColor.AQUA)).color(NamedTextColor.RED));
        obj.addLine(1, "test2", Component.translatable("core.event.quit.fete").color(NamedTextColor.GREEN));
        rankTeam.getTeams().forEach(board::registerTeam);
        board.updateDisplay(true, 2, 10, 2 * 20);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onConnect(PlayerJoinEvent event) {
        var p = new InternalPlayer(event.getPlayer());
        ((IslandsCore) api).addPlayer(p);
        event.joinMessage(Component.empty());
        event.getPlayer().sendMessage(Component.translatable("core.event.join.msg", p.getMainRank().getDisplayName()));
        board.addPlayer(p);
        sendHeader(p);
    }

    private void sendHeader(IslandsPlayer p) {
        var             p1     = PlainTextComponentSerializer.plainText().serialize(Component.translatable("core.tab.header.p1"));
        var             p2     = PlainTextComponentSerializer.plainText().serialize(Component.translatable("core.tab.header.p2"));
        var             p1c    = GradientComponent.gradient(p1, TextColor.color(82, 82, 82), TextColor.color(252, 194, 3), false, 0);
        var             isc    = GradientComponent.gradient("Islands Wars", TextColor.color(252, 194, 3), TextColor.color(103, 252, 3), false, 0);
        var             p2c    = GradientComponent.gradient(p2, TextColor.color(103, 252, 3), TextColor.color(82, 82, 82), false, 0);
        var             header = p1c.appendSpace().append(isc).appendSpace().append(p2c).appendNewline();
        final Component footer = Component.translatable("core.tab.footer", Component.text("Game").color(TextColor.color(15642)));
        p.getBukkitPlayer().sendPlayerListHeaderAndFooter(header, footer);
        rankTeam.registerPlayer(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        var islandsPlayer = getOptionalPlayer(event.getPlayer());
        islandsPlayer.ifPresent(rankTeam::unregisterPlayer);
        islandsPlayer.ifPresent(p -> api.getScoreboardManager().release(p));
        islandsPlayer.ifPresent(p -> api.getBarManager().unregisterPlayer(p));
        islandsPlayer.ifPresent(((IslandsCore) api)::removePlayer);
        event.quitMessage(Component.empty());

        api.getScoreboardManager().release(board);
    }
}
