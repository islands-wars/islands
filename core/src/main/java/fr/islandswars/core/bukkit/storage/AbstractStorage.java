package fr.islandswars.core.bukkit.storage;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemType;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.storage.Storage;
import fr.islandswars.api.utils.Preconditions;
import org.bukkit.inventory.Inventory;

/**
 * File <b>AbstractStorage</b> located on fr.islandswars.core.bukkit.storage
 * AbstractStorage is a part of islands.
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
 * Created the 07/02/2021 at 21:50
 * @since 0.1
 */
public abstract class AbstractStorage implements Storage {

	private final int    size;
	private final String name;

	AbstractStorage(int size, String titleKey) {
		this.size = size;
		this.name = titleKey;
	}

	@Override
	public void addItem(IslandsItem item, IslandsPlayer player) {
		Preconditions.checkNotNull(item);

		set(getNextFreeSlot(player), item, player);
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public IslandsItem newItem(ItemType type) {
		return IslandsApi.getInstance().getStorageManager().newItem(type);
	}

	@Override
	public void removeItem(int row, int column, IslandsPlayer player) {
		Preconditions.checkState(row, ref -> ref >= 0 && ref <= size % 9);
		Preconditions.checkState(column, ref -> ref >= 1 && ref <= 9);

		removeItem(row * 9 + column - 1, player);
	}

	@Override
	public void setItem(int row, int column, IslandsItem item, IslandsPlayer player) {
		set(row * 9 + column - 1, item, player);
	}

	public abstract Inventory getHandle(IslandsPlayer player);

	public abstract int getNextFreeSlot(IslandsPlayer player);

	public abstract void remove(int slot, IslandsPlayer player);

	public abstract void set(int slot, IslandsItem item, IslandsPlayer player);
}
