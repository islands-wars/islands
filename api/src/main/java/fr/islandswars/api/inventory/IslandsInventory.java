package fr.islandswars.api.inventory;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.player.IslandsPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
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
 * Created the 31/03/2024 at 17:44
 * @since 0.1
 */
public class IslandsInventory extends BasicInventory {

    public IslandsInventory(IslandsApi api, int size, Component title) {
        super(api, size, title);
    }

    @Override
    public void openTo(IslandsPlayer player) {
        if (!isBuild())
            build();
        player.getBukkitPlayer().ifPresent(p -> p.openInventory(getInventory()));
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
