package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInEnchantItem;

/**
 * File <b>EnchantItemInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * EnchantItemInPacket is a part of islands.
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
 * Created the 05/02/2021 at 15:34
 * @since 0.1
 * <p>
 * Used when clicking on window buttons. Until 1.14, this was only used by enchantment tables.
 * TODO debug...
 */
public class EnchantItemInPacket extends GamePacket<PacketPlayInEnchantItem> {

	protected EnchantItemInPacket(PacketPlayInEnchantItem handle) {
		super(handle);
	}

	/**
	 * @return the windows id send by {@link OpenWindowsInPacket}
	 */
	public int getWindowsID() {
		return handle.b();
	}

	/**
	 * Used only for enchant table since 1.14.
	 * 0 for topmost enchant, 1 for middle and 2 for bottom enchant
	 *
	 * @return position of the enchant
	 */
	public int getTableID() {
		return handle.c();
	}

	/**
	 * @param id the windows id send by {@link OpenWindowsInPacket}
	 */
	public void setWindowsID(int id) {
		setHandleValue("a", id);
	}

	/**
	 * Used only for enchant table since 1.14.
	 * 0 for topmost enchant, 1 for middle and 2 for bottom enchant
	 *
	 * @param id position of the enchant
	 */
	public void setTableID(int id) {
		setHandleValue("b", id);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
