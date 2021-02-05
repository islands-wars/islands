package fr.islandswars.api.net.packet.handshake.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.EnumProtocol;
import net.minecraft.server.v1_16_R3.PacketHandshakingInSetProtocol;

/**
 * File <b>HandShakePacket</b> located on fr.islandswars.api.net.packet.handshake.client
 * HandShakePacket is a part of islands.
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
 * Created the 04/02/2021 at 17:37
 * @since TODO edit
 */
public class HandShakePacket extends GamePacket<PacketHandshakingInSetProtocol> {

	protected HandShakePacket(PacketHandshakingInSetProtocol handle) {
		super(handle);
	}

	/**
	 * @return the next enum protocol state
	 */
	public EnumProtocol getEnumProtocol() {
		return (EnumProtocol) getHandleValue("d");
	}

	/**
	 * @return this server's host
	 */
	public String getHost() {
		return (String) getHandleValue("hostname");
	}

	/**
	 * @return this server's port
	 */
	public int getPort() {
		return (int) getHandleValue("port");
	}

	/**
	 * @return this current server implementation protocol version
	 */
	public int getProtocolVersion() {
		return (int) getHandleValue("a");
	}

	@Override
	public PacketType getType() {
		return PacketType.Handshake.Client.HANDSHAKE;
	}

	/**
	 * @param newHost a new host to send
	 */
	public void setHost(String newHost) {
		setHandleValue("hostname", newHost);
	}

	/**
	 * @param newPort a new port to send
	 */
	public void setPort(int newPort) {
		setHandleValue("port", newPort);
	}

	/**
	 * @param newProtocolversion a new version to send
	 * @deprecated the client will see this server outdated
	 */
	@Deprecated
	public void setProtocolVersion(int newProtocolversion) {
		setHandleValue("a", newProtocolversion);
	}
}
