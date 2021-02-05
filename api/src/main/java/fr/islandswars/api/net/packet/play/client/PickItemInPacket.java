package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInPickItem;

/**
 * File <b>PickItemInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * PickItemInPacket is a part of islands.
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
 * Created the 05/02/2021 at 16:33
 * @since 0.1
 * <p>
 * Used to swap out an empty space on the hotbar with the item in the given inventory slot. The Notchain client uses this for pick block functionality (middle click) to retrieve items from the inventory.
 */
public class PickItemInPacket extends GamePacket<PacketPlayInPickItem> {

	protected PickItemInPacket(PacketPlayInPickItem handle) {
		super(handle);
	}

	public int getSlot() {
		return handle.b();
	}

	public void setSlot(int slot) {
		setHandleValue("a", slot);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
