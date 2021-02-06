package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInTransaction;

/**
 * File <b>TransactionInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * TransactionInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:46
 * @since 0.1
 * <p>
 * If a confirmation sent by the client was not accepted, the server will reply with a Window Confirmation (clientbound) packet with the Accepted field set to false.
 * When this happens, the client must send this packet to apologize (as with movement), otherwise the server ignores any successive confirmations.
 */
public class TransactionInPacket extends GamePacket<PacketPlayInTransaction> {

	protected TransactionInPacket(PacketPlayInTransaction handle) {
		super(handle);
	}

	/**
	 * @return window id
	 */
	public int getWindowID() {
		return handle.b();
	}

	/**
	 * @param id the window id
	 */
	public void setWindowID(int id) {
		setHandleValue("a", id);
	}

	/**
	 * @return an incremental action number
	 */
	public short getActionNumber() {
		return handle.c();
	}

	/**
	 * @param number the action numbner
	 */
	public void setActionNumber(short number) {
		setHandleValue("b", number);
	}

	/**
	 * @return client response
	 */
	public boolean getAccepted() {
		return (boolean) getHandleValue("c");
	}

	/**
	 * @param value custom response
	 */
	public void setAccepted(boolean value) {
		setHandleValue("c", value);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.TRANSACTION_IN;
	}
}
