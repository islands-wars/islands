package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInTeleportAccept;

/**
 * File <b>TeleportAcceptInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * TeleportAcceptInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:32
 * @since 0.1
 */
public class TeleportAcceptInPacket extends GamePacket<PacketPlayInTeleportAccept> {

	protected TeleportAcceptInPacket(PacketPlayInTeleportAccept handle) {
		super(handle);
	}

	/**
	 * @return the id given by other packet
	 */
	public int getID() {
		return handle.b();
	}

	/**
	 * @param id new transaction id
	 */
	public void setID(int id) {
		setHandleValue("a", id);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.TELEPORT_ACCEPT_IN;
	}
}
