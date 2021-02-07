package fr.islandswars.core.bukkit.item;

import fr.islandswars.api.item.ItemProperties;
import fr.islandswars.api.item.ItemType;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.enchantments.CraftEnchantment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

/**
 * File <b>PropertiesWrapper</b> located on fr.islandswars.core.bukkit.item
 * PropertiesWrapper is a part of islands.
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
 * Created the 07/02/2021 at 17:31
 * @since 0.1
 */
public class PropertiesWrapper implements ItemProperties {

	private ItemType properties;
	private boolean  playerSkullItem;

	PropertiesWrapper(ItemType properties) {
		this.playerSkullItem = false;
		this.properties = properties;
		getTag();
	}

	@Override
	public ItemProperties addEnchantment(Enchantment enchantment, int level) {
		var tag = getTag();

		if (!tag.hasKeyOfType("Enchantments", 9))
			tag.set("Enchantments", new NBTTagList());

		var nbttaglist     = tag.getList("Enchantments", 10);
		var nbttagcompound = new NBTTagCompound();
		nbttagcompound.setString("id", String.valueOf(IRegistry.ENCHANTMENT.getKey(CraftEnchantment.getRaw(enchantment))));
		nbttagcompound.setShort("lvl", (short) level);
		nbttaglist.add(nbttagcompound);

		return this;
	}

	@Override
	public ItemProperties addItemFlags(ItemFlag... flags) {
		var tag = getTag();

		if (!tag.hasKeyOfType("HideFlags", 3))
			tag.setInt("HideFlags", 0);

		int hideFlag = tag.getInt("HideFlags");
		for (var flag : flags) {
			hideFlag |= this.getBitModifier(flag);
			tag.setInt("HideFlags", hideFlag);
		}
		return this;
	}

	@Override
	public ItemProperties addNBTTag(String key, NBTBase nbtTag) {
		getTag().set(key, nbtTag);
		return this;
	}

	@Override
	public int getAmount() {
		return properties.getAmount();
	}

	@Override
	public short getDurability() {
		return properties.getData();
	}

	@Override
	public Material getMaterial() {
		return properties.getMaterial();
	}

	@Override
	public ItemProperties setAmount(int amount) {
		if (amount < 1 || amount > 128)
			return this;
		properties.writeAmount(amount);
		return this;
	}

	@Override
	public ItemProperties setDamage(short durability) {
		var tag = getTag();

		tag.setShort("Damage", durability);
		return this;
	}

	@Override
	public ItemProperties setMaterial(Material material) {
		properties.writeMaterial(material);
		if (properties.getAmount() < 1 || properties.getAmount() > 128)
			setAmount(1);
		return this;
	}

	@Override
	public ItemProperties setUnbreakable(boolean value) {
		String key = "Unbreakable";

		if (value)
			getTag().setBoolean(key, true);
		else
			getTag().remove(key);

		return this;
	}

	@Override
	public ItemProperties usePlayerHead() {
		this.playerSkullItem = true;
		return this;
	}

	@Override
	public ItemProperties clone() {
		try {
			return new PropertiesWrapper(properties.clone());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean isPlayerSkullItem() {
		return this.playerSkullItem;
	}

	private byte getBitModifier(ItemFlag hideFlag) {
		return (byte) (1 << hideFlag.ordinal());
	}

	private NBTTagCompound getTag() {
		if (!properties.getCompound().hasKey("tag"))
			properties.getCompound().set("tag", new NBTTagCompound());
		return properties.getCompound().getCompound("tag");
	}

	/**
	 * @return this item compound
	 */
	NBTTagCompound getCompound() {
		return properties.getCompound();
	}
}
