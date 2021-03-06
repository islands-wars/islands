package fr.islandswars.api.inventory.item;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * File <b>CustomItem</b> located on fr.islandswars.api.inventory.item
 * CustomItem is a part of islands.
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
 * Created the 19/02/2021 at 14:34
 * @since 0.1
 */
public interface CustomItem {

	/**
	 * Register this item, the locale change will be handled and also interaction and click
	 *
	 * @param item an islands item to regist
	 * @return the id used to register this item
	 */
	int register(IslandsItem item);

	/**
	 * Return weither or not this item is registered
	 *
	 * @param id an id to try
	 * @return an IslandsItem if founded
	 */
	Optional<IslandsItem> exist(int id);

	/**
	 * Supply a stream of "static" item to register
	 *
	 * @param factoryItem a bunch of item to register
	 */
	void supply(Stream<CustomIslandsItem> factoryItem);
}
