package fr.islandswars.api.inventory;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.inventory.item.CustomIslandsItem;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.utils.Preconditions;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * File <b>BasicInventory</b> located on fr.islandswars.api.inventory
 * BasicInventory is a part of islands.
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
 * Created the 30/03/2024 at 21:17
 * @since 0.1
 */
public abstract class BasicInventory implements InventoryHolder {

    protected final IslandsApi          api;
    protected final Inventory           inventory;
    private final   CustomIslandsItem[] items;
    private         boolean             build;

    public BasicInventory(IslandsApi api, int size, Component title) {
        this.api = api;
        Preconditions.checkState(size, (ref) -> size % 9 == 0);
        this.inventory = api.getServer().createInventory(this, size, title);
        this.items = new CustomIslandsItem[size];
        this.build = false;
    }

    public void addItem(CustomIslandsItem item) {
        for (int index = 0; index < inventory.getSize(); index++) {
            if (items[index] == null) {
                items[index] = item;
                return;
            }
        }
    }

    public void setItem(CustomIslandsItem item, int index) {
        items[index] = item;
    }

    public void build() {
        for (int index = 0; index < inventory.getSize(); index++) {
            if (items[index] != null) inventory.setItem(index, items[index].build());
        }
    }

    public boolean isBuild() {
        return build;
    }

    public abstract void openTo(IslandsPlayer player);
}
