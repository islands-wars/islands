package fr.islandswars.api.item;

import fr.islandswars.api.player.IslandsPlayer;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.server.v1_16_R3.ItemStack;
import org.bukkit.event.Cancellable;

/**
 * File <b>IslandsItem</b> located on fr.islandswars.api.item
 * IslandsItem is a part of islands.
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
 * Created the 07/02/2021 at 08:58
 * @since 0.1
 */
public interface IslandsItem {

	/**
	 * @return this item wrapped in nms item
	 */
	ItemStack toNMSItem();

	/**
	 * @return this item wrapped in bukkit item
	 */
	org.bukkit.inventory.ItemStack toBukkitItem();

	/**
	 * Provide name and lore key for this item, can be null
	 *
	 * @param nameKey an i18n key
	 * @param loreKey another one ;)
	 * @return this item instance
	 */
	IslandsItem withInternalisation(String nameKey, String loreKey);

	/**
	 * Supply custom properties to this item
	 *
	 * @param propertiesConsumer a consumer to fill existing properties
	 * @return this item builder
	 */
	IslandsItem withProperties(Consumer<ItemProperties> propertiesConsumer);

	/**
	 * Param an event when a player click on this item
	 *
	 * @param event an event to execute
	 * @return this item instance
	 */
	IslandsItem onClick(BiConsumer<IslandsPlayer, Cancellable> event);

	/**
	 * @return this internal item id
	 */
	int getId();

}
