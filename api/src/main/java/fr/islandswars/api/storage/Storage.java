package fr.islandswars.api.storage;

import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemType;
import fr.islandswars.api.player.IslandsPlayer;

/**
 * File <b>Storage</b> located on fr.islandswars.api.storage
 * Storage is a part of islands.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 07/02/2021 at 21:46
 * @since 0.1
 */
public interface Storage {

	/**
	 * Add an item to the next unused index
	 *
	 * @param item   the item to set
	 * @param player the target to add item
	 */
	void addItem(IslandsItem item, IslandsPlayer player);

	/**
	 * Get a column according to minecraft storing system
	 *
	 * @param index an integer representing a slot in an storage
	 * @return a column position
	 */
	default int getColumn(int index) {
		return index - getRow(index) * 9 + 1;
	}

	/**
	 * Get a row according to minecraft storing system
	 *
	 * @param index an integer representing a slot in an storage
	 * @return a row position
	 */
	default int getRow(int index) {
		return index / 9;
	}

	/**
	 * @return this storage size
	 */
	int getSize();

	/**
	 * Create a new item based on the given builder
	 *
	 * @param type an item to instanciate
	 * @return a new item
	 */
	IslandsItem newItem(ItemType type);

	/**
	 * Remove item (if exists) at this slot
	 *
	 * @param row    the index row, start to 0 from limit
	 * @param column the index column, must be in [1;9]
	 * @param player a player only if it's a personal storage, or else can be null
	 */
	void removeItem(int row, int column, IslandsPlayer player);

	/**
	 * Remove item (if exists) at this slot
	 *
	 * @param index  the storage index, start to 0 from limit
	 * @param player a player only if it's a personal storage, or else can be null
	 */
	default void removeItem(int index, IslandsPlayer player) {
		removeItem(getRow(index), getColumn(index), player);
	}

	/**
	 * Set an item to the storage at the given index
	 *
	 * @param row    the index row, start to 0 from limit
	 * @param column the index column, must be in [1;9]
	 * @param item   the item to set in this slot
	 * @param player a player only if it's a personal storage, or else can be null
	 */
	void setItem(int row, int column, IslandsItem item, IslandsPlayer player);

	/**
	 * Set an item to the storage at the given index
	 *
	 * @param index  the storage, start to 0 from limit
	 * @param item   the item to set in this index
	 * @param player a player only if it's a personal storage, or else can be null
	 */
	default void setItem(int index, IslandsItem item, IslandsPlayer player) {
		setItem(getRow(index), getColumn(index), item, player);
	}
}
