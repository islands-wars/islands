package fr.islandswars.api.net.packet.status.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.net.wrapper.ServerPingWrapper;
import net.minecraft.server.v1_16_R3.PacketStatusOutServerInfo;
import net.minecraft.server.v1_16_R3.ServerPing;

/**
 * File <b>ServerInfoPacket</b> located on fr.islandswars.api.net.packet.status.server
 * ServerInfoPacket is a part of islands.
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
 * Created the 04/02/2021 at 18:52
 * @since 0.1
 */
public class ServerInfoPacket extends GamePacket<PacketStatusOutServerInfo> {

	private ServerPingWrapper wrapper;

	public ServerInfoPacket(PacketStatusOutServerInfo handle) {
		super(handle);
	}

	public ServerPingWrapper getResponse() {
		if (wrapper == null)
			this.wrapper = new ServerPingWrapper((ServerPing) getHandleValue("b"));
		return wrapper;
	}

	@Override
	public PacketType getType() {
		return PacketType.Status.Server.SERVER_INFO;
	}

	public void setResponse(ServerPingWrapper wrapper) {
		setHandleValue("b", wrapper.getNMS());
	}
}
