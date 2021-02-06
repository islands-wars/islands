package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInTabComplete;

/**
 * File <b>TabCompleteInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * TabCompleteInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:28
 * @since 0.1
 */
public class TabCompleteInPacket extends GamePacket<PacketPlayInTabComplete> {

	protected TabCompleteInPacket(PacketPlayInTabComplete handle) {
		super(handle);
	}

	/**
	 * @return all text behind the cursor without the /
	 */
	public String getText() {
		return handle.c();
	}

	/**
	 * @param text all text behind the cursor without the /
	 */
	public void setText(String text) {
		setHandleValue("b", text);
	}

	/**
	 * @return the id received in the tab completion request packet, must match or the client will ignore this packet. Client generates this and increments it each time it sends another tab completion that doesn't get a response.
	 */
	public int getTransactionID() {
		return handle.b();
	}

	/**
	 * @param id the id received in the tab completion request packet, must match or the client will ignore this packet. Client generates this and increments it each time it sends another tab completion that doesn't get a response.
	 */
	public void setTransactionID(int id) {
		setHandleValue("a", id);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.TAB_COMPLETE_IN;
	}
}
