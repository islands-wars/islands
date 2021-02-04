package fr.islandswars.core.internal.listener.packet;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.net.PacketEvent;
import fr.islandswars.api.net.PacketHandler;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.net.packet.handshake.server.HandShakePacket;
import fr.islandswars.api.net.packet.status.server.ServerInfoPacket;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

/**
 * File <b>HandShakePacketListener</b> located on fr.islandswars.core.internal.listener.packet
 * HandShakePacketListener is a part of islands.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:58
 * @since TODO edit
 */
public class ServerPingPacketListener extends PacketHandler<ServerInfoPacket> {

	public ServerPingPacketListener(PacketType<ServerInfoPacket> packetType) {
		super(packetType);
	}

	@Override
	public void handlePacket(PacketEvent<ServerInfoPacket> event) {
		event.getPacket().getResponse().setDescription(new ChatComponentText("mdr"));
		event.getPacket().getResponse().setOnlinePlayer(40);
		IslandsApi.getInstance().getInfraLogger().createDefaultLog("test");
	}
}
