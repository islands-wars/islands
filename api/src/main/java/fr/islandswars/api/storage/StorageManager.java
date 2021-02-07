package fr.islandswars.api.storage;

import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemType;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

/**
 * File <b>StorageManager</b> located on fr.islandswars.api.storage
 * StorageManager is a part of islands.
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
 * Created the 07/02/2021 at 21:47
 * @since 0.1
 */
public interface StorageManager {

	/**
	 * Create a new storage based on the given builder
	 *
	 * @param builder an storage builder
	 * @return a new Storage
	 */
	Storage createStorage(StorageBuilder builder);

	/**
	 * Get an islands wars item only if it has been created via {@link #newItem(ItemType)}
	 *
	 * @param bukkitItem a bukkit item to get from
	 * @return a wrapped item if exist
	 */
	Optional<IslandsItem> getItem(ItemStack bukkitItem);

	/**
	 * Create a new item based on the given type
	 *
	 * @param type an item type
	 * @return a new islands wars item
	 */
	IslandsItem newItem(ItemType type);

}
