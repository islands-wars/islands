package fr.islandswars.api.inventory.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

/**
 * File <b>FactoryItem</b> located on fr.islandswars.api.inventory.item
 * FactoryItem is a part of islands.
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
 * Created the 30/03/2024 at 22:45
 * @since 0.1
 */
public enum FactoryItem {

    SUPER_SWORD(new CustomIslandsItem(IslandsItem.builder(Material.DIAMOND_SWORD, 1), 0).onInteract((p, e) -> {
        p.getBukkitPlayer().sendMessage(Component.text("Super sword magic power..."));
        e.setCancelled(true);
    }).onClick((p, e) -> e.setCancelled(true)));

    private final CustomIslandsItem item;

    FactoryItem(CustomIslandsItem item) {
        this.item = item;
    }

    public CustomIslandsItem getItem() {
        return item;
    }

}
