package fr.islandswars.api.module.bukkit;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.inventory.item.FactoryItem;
import fr.islandswars.api.module.Module;

/**
 * File <b>ItemModule</b> located on fr.islandswars.api.module.bukkit
 * ItemModule is a part of islands.
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
 * Created the 30/03/2024 at 23:06
 * @since 0.1
 */
public class ItemModule extends Module {
    public ItemModule(IslandsApi api) {
        super(api);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        for (FactoryItem item : FactoryItem.values()) {
            api.getInfraLogger().logDebug("Register custom default item id= " + item.getItem().getKey());
        }
    }

    @Override
    public void onLoad() {

    }
}
