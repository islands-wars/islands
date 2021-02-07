package fr.islandswars.core.bukkit.storage;

import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemType;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.storage.Storage;
import fr.islandswars.api.storage.StorageBuilder;
import fr.islandswars.api.storage.StorageManager;
import fr.islandswars.api.utils.Preconditions;
import fr.islandswars.core.bukkit.item.InternalIslandsItem;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * File <b>StorageFactory</b> located on fr.islandswars.core.bukkit.storage
 * StorageFactory is a part of islands.
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
 * Created the 07/02/2021 at 21:45
 * @since 0.1
 * <p>
 * TODO thread safe issues?
 */
public class StorageFactory implements StorageManager {

	private final AtomicInteger                               count;
	private final ConcurrentMap<Integer, InternalIslandsItem> itemsCache;

	public StorageFactory() {
		this.count = new AtomicInteger(0);
		this.itemsCache = new ConcurrentHashMap<>();
	}

	@Override
	public Storage createStorage(StorageBuilder builder) {
		return null;
	}

	@Override
	public Optional<IslandsItem> getItem(ItemStack bukkitItem) {
		Preconditions.checkNotNull(bukkitItem);

		var nmsCopy = CraftItemStack.asNMSCopy(bukkitItem);

		if (nmsCopy == null || !nmsCopy.hasTag() || !nmsCopy.getTag().hasKeyOfType("id", 3))
			return Optional.empty();
		return Optional.ofNullable(itemsCache.get(nmsCopy.getTag().getInt("id")));
	}

	@Override
	public IslandsItem newItem(ItemType type) {
		Preconditions.checkNotNull(type);

		InternalIslandsItem item = new InternalIslandsItem(type, count.incrementAndGet());
		itemsCache.put(item.getId(), item);
		return item;
	}

	public void translateItem(Optional<IslandsPlayer> player, net.minecraft.server.v1_16_R3.ItemStack item) {

	}
}
