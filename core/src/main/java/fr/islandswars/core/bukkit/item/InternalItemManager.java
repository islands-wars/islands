package fr.islandswars.core.bukkit.item;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.inventory.item.CustomIslandsItem;
import fr.islandswars.api.inventory.item.ItemManager;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File <b>InternalItemManager</b> located on fr.islandswars.core.bukkit.item
 * InternalItemManager is a part of islands.
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
 * Created the 30/03/2024 at 22:14
 * @since 0.1
 */
public class InternalItemManager implements ItemManager {
    private final AtomicInteger                   count;
    private final Map<Integer, CustomIslandsItem> items;

    public InternalItemManager() {
        this.items = new ConcurrentHashMap<>();
        this.count = new AtomicInteger(100);
    }

    @Override
    public int register(CustomIslandsItem item) {
        if (items.values().stream().anyMatch(it -> it.equals(item))) {
            IslandsApi.getInstance().getInfraLogger().logError(new Exception("Item " + item.toString() + "is already registered"));
            return 0;
        } else {
            var id = count.incrementAndGet();
            items.put(id, item);
            return id;
        }
    }

    @Override
    public void registerFactory(CustomIslandsItem item, int key) {
        if (items.values().stream().anyMatch(it -> it == item))
            IslandsApi.getInstance().getInfraLogger().logError(new Exception("Item " + item.toString() + "is already registered"));
        else if (items.containsKey(key)) {
            IslandsApi.getInstance().getInfraLogger().logError(new Exception("Key " + key + " is already registered in item manager!"));
        } else
            items.put(key, item);

    }

    @Override
    public Optional<CustomIslandsItem> exist(int key) {
        return Optional.ofNullable(items.get(key));
    }

}
