package fr.islandswars.core.internal.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.listener.LazyListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

/**
 * File <b>ItemListener</b> located on fr.islandswars.core.internal.listener
 * ItemListener is a part of islands.
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
 * Created the 30/03/2024 at 22:27
 * @since 0.1
 */
public class ItemListener extends LazyListener {

    public ItemListener(IslandsApi api) {
        super(api);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        var player = getOptionalPlayer(event.getPlayer());
        player.ifPresent(p -> {
            var it = event.getItem();
            if (isCustom(it)) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    var item = api.getItemManager().exist(getkey(it));
                    item.ifPresent(i -> i.handleInteract(p, event));
                }
            }
        });
    }

    private boolean isCustom(ItemStack item) {
        if (item != null && item.hasItemMeta()) {
            var meta = item.getItemMeta();
            return meta.getPersistentDataContainer().has(api.getKey(), PersistentDataType.INTEGER);
        } else return false;
    }

    private int getkey(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(api.getKey(), PersistentDataType.INTEGER);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player bukkitPlayer) {
            var player = getOptionalPlayer(bukkitPlayer);
            player.ifPresent(p -> {
                var it = event.getCurrentItem();
                if (isCustom(it)) {
                    var item = api.getItemManager().exist(getkey(it));
                    item.ifPresent(i -> i.handleClick(p, event));
                }
            });
        }
    }
}
