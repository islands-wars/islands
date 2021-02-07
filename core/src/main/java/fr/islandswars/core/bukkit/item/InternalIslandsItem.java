package fr.islandswars.core.bukkit.item;

import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemType;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;

/**
 * File <b>InternalItem</b> located on fr.islandswars.core.bukkit.item
 * InternalItem is a part of islands.
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
 * Created the 07/02/2021 at 17:30
 * @since 0.1
 */
public class InternalIslandsItem implements IslandsItem {

	private final ItemType          type;
	private final PropertiesWrapper properties;
	private       ItemStack         wrapper;

	public InternalIslandsItem(ItemType type) {
		this.properties = new PropertiesWrapper(type);
		this.type = type;
		type.getCompound().set("tag", new NBTTagCompound());
	}

	public PropertiesWrapper getProperties() {
		return properties;
	}

	@Override
	public ItemStack toNMSItem() {
		if (wrapper == null)
			this.wrapper = ItemStack.a(type.getCompound());
		System.out.println("Wrapper is : "+this.getProperties().getCompound().toString());
		return wrapper;
	}
}
