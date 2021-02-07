package fr.islandswars.core.bukkit.item;

import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemProperties;
import fr.islandswars.api.item.ItemType;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.utils.Preconditions;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.event.Cancellable;

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

	private final int                                    id;
	private final ItemType                               type;
	private final PropertiesWrapper                      properties;
	private       BiConsumer<IslandsPlayer, Cancellable> clickEvent;
	private       ItemStack                              wrapper;
	private       String                                 loreKey, nameKey;

	public InternalIslandsItem(ItemType type, int id) {
		this.properties = new PropertiesWrapper(type);
		this.type = type;
		this.id = id;
		type.getCompound().set("tag", new NBTTagCompound());
		type.getCompound().getCompound("tag").setInt("id", id);
	}

	public PropertiesWrapper getProperties() {
		return properties;
	}

	@Override
	public org.bukkit.inventory.ItemStack toBukkitItem() {
		return CraftItemStack.asBukkitCopy(toNMSItem());
	}

	@Override
	public IslandsItem withInternalisation(String nameKey, String loreKey) {
		this.nameKey = nameKey;
		this.loreKey = loreKey;
		return this;
	}

	@Override
	public IslandsItem withProperties(Consumer<ItemProperties> propertiesConsumer) {
		propertiesConsumer.accept(properties);
		return this;
	}

	@Override
	public IslandsItem onClick(BiConsumer<IslandsPlayer, Cancellable> event) {
		Preconditions.checkNotNull(event);

		this.clickEvent = event;
		return this;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public ItemStack toNMSItem() {
		if (wrapper == null)
			this.wrapper = ItemStack.a(type.getCompound());
		return wrapper;
	}

	public void callEvent(IslandsPlayer player, Cancellable cancellable) {
		if (clickEvent != null)
			clickEvent.accept(player, cancellable);
	}
}
