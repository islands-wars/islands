package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayOutSetSlot;

/**
 * File <b>SetSlotOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * SetSlotOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 18:25
 * @since 0.1
 */
public class SetSlotOutPacket extends GamePacket<PacketPlayOutSetSlot> {

	protected SetSlotOutPacket(PacketPlayOutSetSlot handle) {
		super(handle);
	}

	public SetSlotOutPacket(int windowID, int slot, ItemStack itemStack) {
		super(new PacketPlayOutSetSlot(windowID, slot, itemStack));
	}

	/**
	 * @return the item
	 */
	public ItemStack getItemStack() {
		return (ItemStack) getHandleValue("c");
	}

	/**
	 * @param itemStack a new item
	 */
	public void setItemStack(ItemStack itemStack) {
		setHandleValue("c", itemStack);
	}

	/**
	 * @return the concerned slot
	 */
	public int getSlot() {
		return (int) getHandleValue("b");
	}

	/**
	 * @param slot a new one
	 */
	public void setSlot(int slot) {
		setHandleValue("b", slot);
	}

	/**
	 * @return window transaction id
	 */
	public int getWindowID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param windowID a new window id
	 */
	public void setWindowID(int windowID) {
		setHandleValue("a", windowID);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.SET_SLOT_OUT;
	}
}
