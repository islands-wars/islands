package fr.islandswars.api.inventory.item;

import org.bukkit.Material;

/**
 * File <b>CustomIslandsItem</b> located on fr.islandswars.api.inventory.item
 * CustomIslandsItem is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2021 Islands Wars.
 * <p>
 * islands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 19/02/2021 at 14:43
 * @since 0.1
 */
public class CustomIslandsItem {

	private final int         id;
	private final IslandsItem item;

	public CustomIslandsItem(IslandsItem item, int id) {
		this.item = item;
		this.id = id;
	}

	/**
	 * @return this item id, must be negative to not break translated item registered on the fly
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return a wrapper for bukkit item
	 */
	public IslandsItem getItem() {
		return item;
	}
}
