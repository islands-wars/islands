package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.InventoryClickType;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayInWindowClick;

/**
 * File <b>WindowsClickInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * WindowsClickInPacket is a part of islands.
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
 * Created the 05/02/2021 at 18:11
 * @since 0.1
 */
public class WindowsClickInPacket extends GamePacket<PacketPlayInWindowClick> {

	protected WindowsClickInPacket(PacketPlayInWindowClick handle) {
		super(handle);
	}

	/**
	 * @return the clicked slot number
	 */
	public int getSlot() {
		return handle.c();
	}

	/**
	 * @param slot the clicked slot number
	 */
	public void setSlot(int slot) {
		setHandleValue("slot", slot);
	}

	/**
	 * @return the button used in click
	 */
	public int getButton() {
		return handle.d();
	}

	/**
	 * @param button used in click
	 */
	public void setButton(int button) {
		setHandleValue("button", button);
	}

	/**
	 * @return targeted item
	 */
	public ItemStack getItem() {
		return handle.f();
	}

	/**
	 * @param item targeted item
	 */
	public void setItem(ItemStack item) {
		setHandleValue("item", item);
	}

	/**
	 * @return click type
	 */
	public InventoryClickType getClickType() {
		return handle.g();
	}

	/**
	 * @param type click type
	 */
	public void setClickType(InventoryClickType type) {
		setHandleValue("shift", type);
	}

	/**
	 * @return windows id
	 */
	public int getWindowsID() {
		return handle.b();
	}

	/**
	 * @param id windows id
	 */
	public void setWindowsID(int id) {
		setHandleValue("a", id);
	}

	public short getActionNumber() {
		return handle.e();
	}

	public void setActionNumber(short number) {
		setHandleValue("d", number);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.WINDOWS_CLICK_IN;
	}
}
