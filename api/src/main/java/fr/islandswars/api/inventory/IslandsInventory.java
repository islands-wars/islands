package fr.islandswars.api.inventory;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.utils.Preconditions;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * File <b>IslandsInventory</b> located on fr.islandswars.api.inventory
 * IslandsInventory is a part of islands.
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
public abstract class IslandsInventory implements InventoryHolder {
    protected final IslandsApi api;
    private final   Inventory  inventory;

    public IslandsInventory(IslandsApi api, int size) {
        this.api = api;
        Preconditions.checkState(size, (ref) -> size % 9 == 0);
        this.inventory = api.getServer().createInventory(this, size);
    }


    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
