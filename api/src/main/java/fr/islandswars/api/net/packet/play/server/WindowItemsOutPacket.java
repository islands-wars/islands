package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import java.util.List;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayOutWindowItems;

/**
 * File <b>WindowItemOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * WindowItemOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 19:18
 * @since 0.1
 */
public class WindowItemsOutPacket extends GamePacket<PacketPlayOutWindowItems> {

	protected WindowItemsOutPacket(PacketPlayOutWindowItems handle) {
		super(handle);
	}

	/**
	 * @return window id
	 */
	public int getWindowID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param newId for window
	 */
	public void setWindowID(int newId) {
		setHandleValue("a", newId);
	}

	/**
	 * @return concerned itemstack
	 */
	@SuppressWarnings("unchecked")
	public List<ItemStack> getItemStacks() {
		return (List<ItemStack>) getHandleValue("b");
	}

	/**
	 * @param newItems to set
	 */
	public void setItemStacks(List<ItemStack> newItems) {
		setHandleValue("b", newItems);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.WINDOW_ITEMS_OUT;
	}
}
