package fr.islandswars.core.internal.listener;

import com.destroystokyo.paper.event.player.PlayerClientOptionsChangeEvent;
import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.inventory.BasicInventory;
import fr.islandswars.api.inventory.IslandsInventory;
import fr.islandswars.api.inventory.item.CustomIslandsItem;
import fr.islandswars.api.inventory.item.IslandsItem;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.player.InternalPlayer;
import fr.islandswars.core.player.rank.BoardManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
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

    private final BoardManager      boardManager;
    private final BasicInventory    inv;
    private final CustomIslandsItem item;

    public PlayerListener(IslandsApi api) {
        super(api);
        this.boardManager = new BoardManager();
        this.inv = new IslandsInventory(api, 9 * 3, Component.text("le mien"));
        this.item = new CustomIslandsItem(IslandsItem.builder(Material.GOLD_BLOCK)).onClick((p, e) -> {
            e.setCancelled(true);
        });
        inv.setItem(item, 0);
        inv.build();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onConnect(PlayerJoinEvent event) {
        var p = new InternalPlayer(event.getPlayer());
        ((IslandsCore) api).addPlayer(p);
        event.getPlayer().sendMessage(Component.translatable("core.event.join.msg", p.getMainRank().getDisplayName()));
        sendHeader(p);

        p.getBukkitPlayer().openInventory(inv.getInventory());
        p.getBukkitPlayer().getInventory().clear();
        p.getBukkitPlayer().getInventory().setItem(0, item.build());
    }

    private void sendHeader(IslandsPlayer p) {
        final Component header = Component.translatable("core.tab.header");
        final Component footer = Component.translatable("core.tab.footer", Component.text("Game").color(TextColor.color(15642)));
        p.getBukkitPlayer().sendPlayerListHeaderAndFooter(header, footer);
        boardManager.registerPlayer(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        var islandsPlayer = getOptionalPlayer(event.getPlayer());
        islandsPlayer.ifPresent(boardManager::unregisterPlayer);
        islandsPlayer.ifPresent((p) -> api.getBarManager().unregisterPlayer(p));
        islandsPlayer.ifPresent(((IslandsCore) api)::removePlayer);
    }

    @EventHandler
    public void onLocaleChange(PlayerClientOptionsChangeEvent event) {
        if (event.hasLocaleChanged()) {
            //TODO NOT WORKING
            getOptionalPlayer(event.getPlayer()).ifPresent(boardManager::updatePlayer);
            getOptionalPlayer(event.getPlayer()).ifPresent(this::sendHeader);
        }
    }
}
