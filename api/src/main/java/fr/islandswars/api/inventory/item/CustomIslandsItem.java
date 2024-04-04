package fr.islandswars.api.inventory.item;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.utils.Preconditions;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.BiConsumer;

/**
 * File <b>CustomIslandsItem</b> located on fr.islandswars.api.inventory.item
 * CustomIslandsItem is a part of islands.
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
 * Created the 30/03/2024 at 22:04
 * @since 0.1
 */
public class CustomIslandsItem {

    private final int                                    key;
    private final IslandsItem                            item;
    private       BiConsumer<IslandsPlayer, Cancellable> click, interact;

    public CustomIslandsItem(IslandsItem item) {
        Preconditions.checkNotNull(item);
        this.item = item;
        this.key = IslandsApi.getInstance().getItemManager().register(this);
    }

    public CustomIslandsItem(IslandsItem item, int key) {
        Preconditions.checkNotNull(item);
        this.item = item;
        this.key = key;
        IslandsApi.getInstance().getItemManager().registerFactory(this, key);
    }


    public CustomIslandsItem onClick(BiConsumer<IslandsPlayer, Cancellable> click) {
        this.click = click;
        return this;
    }

    public CustomIslandsItem onInteract(BiConsumer<IslandsPlayer, Cancellable> interact) {
        this.interact = interact;
        return this;
    }

    public void handleClick(IslandsPlayer player, InventoryInteractEvent event) {
        if (click != null) click.accept(player, event);
    }

    public void handleInteract(IslandsPlayer player, PlayerInteractEvent event) {
        if (interact != null) interact.accept(player, event);
    }

    public ItemStack build() {
        var it   = item.build();
        var meta = it.getItemMeta();
        meta.getPersistentDataContainer().set(IslandsApi.getInstance().getKey(), PersistentDataType.INTEGER, key);
        it.setItemMeta(meta);
        return it;
    }

    public int getKey() {
        return key;
    }

}
