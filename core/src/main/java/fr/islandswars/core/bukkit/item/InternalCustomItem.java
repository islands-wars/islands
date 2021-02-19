package fr.islandswars.core.bukkit.item;

import fr.islandswars.api.inventory.item.CustomIslandsItem;
import fr.islandswars.api.inventory.item.CustomItem;
import fr.islandswars.api.inventory.item.IslandsItem;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * File <b>InternalCustomItem</b> located on fr.islandswars.core.bukkit.item
 * InternalCustomItem is a part of islands.
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
 * Created the 19/02/2021 at 14:44
 * @since 0.1
 */
public class InternalCustomItem implements CustomItem {

	private final AtomicInteger             count;
	private final Map<Integer, IslandsItem> items;

	public InternalCustomItem() {
		this.items = new ConcurrentHashMap<>();
		this.count = new AtomicInteger(0);
	}

	@Override
	public int register(IslandsItem item) {
		if (items.values().stream().anyMatch(it -> it.equals(item)))
			return items.entrySet().stream().filter(entry -> item.equals(entry.getValue())).map(Map.Entry::getKey).findFirst().get();
		var id = count.incrementAndGet();
		items.put(id, item);
		return id;
	}

	@Override
	public Optional<IslandsItem> exist(int id) {
		return Optional.ofNullable(items.get(id));
	}

	@Override
	public void supply(Stream<CustomIslandsItem> factoryItem) {
		factoryItem.forEach(item -> items.put(item.getID(), item.getItem()));
	}
}
