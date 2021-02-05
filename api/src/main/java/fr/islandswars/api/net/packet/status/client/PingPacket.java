package fr.islandswars.api.net.packet.status.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketStatusInPing;

/**
 * File <b>PingPacket</b> located on fr.islandswars.api.net.packet.status.client
 * PingPacket is a part of islands.
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
 * @since TODO edit
 */
public class PingPacket extends GamePacket<PacketStatusInPing> {

	public PingPacket(PacketStatusInPing handle) {
		super(handle);
	}

	public long getPayload() {
		return handle.b();
	}

	@Override
	public PacketType getType() {
		return PacketType.Status.Client.PING;
	}

}
