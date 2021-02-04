package fr.islandswars.api.net;

import fr.islandswars.api.net.packet.handshake.server.HandShakePacket;
import fr.islandswars.api.net.packet.status.client.PingPacket;
import fr.islandswars.api.net.packet.status.client.StartPacket;
import fr.islandswars.api.net.packet.status.server.PongPacket;
import fr.islandswars.api.net.packet.status.server.ServerInfoPacket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_16_R3.*;
import static fr.islandswars.api.net.PacketType.Bound.IN;
import static fr.islandswars.api.net.PacketType.Bound.OUT;

/**
 * File <b>PacketType</b> located on fr.islandswars.api.net
 * PacketType is a part of islands.
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
 * Created the 04/02/2021 at 17:12
 * @since TODO edit
 */
public class PacketType<T extends GamePacket> {

	private static final Map<Class<? extends Packet>, Class<? extends GamePacket>> packetList = new HashMap<>();
	private static       Map<Class<? extends Packet>, Class<? extends GamePacket>> unmodifiablePacketList;
	private final        Class<T>                                                  packet;
	private final        Class<? extends Packet>                                   nmsPacket;
	private final        Protocol                                                  protocol;
	private final        Bound                                                     bound;

	public PacketType(Class<T> packet, Class<? extends Packet> nmsPacket, Protocol protocol, Bound bound) {
		this.protocol = protocol;
		this.nmsPacket = nmsPacket;
		this.packet = packet;
		this.bound = bound;
		packetList.put(nmsPacket, packet);
	}

	/**
	 * @return an immutable Map access (read only)
	 * @see Collections#unmodifiableMap(Map)
	 */
	public static Map<Class<? extends Packet>, Class<? extends GamePacket>> getPacketList() {
		return unmodifiablePacketList == null ? unmodifiablePacketList = Collections.unmodifiableMap(packetList) : unmodifiablePacketList;
	}

	/**
	 * @return the packet's bound
	 */
	public Bound getBound() {
		return bound;
	}

	/**
	 * @return this packet name
	 */
	public String getID() {
		return nmsPacket.getSimpleName();
	}

	/**
	 * @return the packet wrapper's class
	 */
	public Class<T> getPacket() {
		return packet;
	}

	/**
	 * @return the packet wrapper's protocol
	 */
	public Protocol getProtocol() {
		return protocol;
	}

	public enum Protocol {
		/**
		 * Id: -1
		 */
		HANDSHAKE,
		/**
		 * Id: 0
		 */
		PLAY,
		/**
		 * Id: 1
		 */
		STATUS,
		/**
		 * Id: 2
		 */
		LOGIN
	}

	public enum Bound {
		/**
		 * Incomming buffer
		 */
		IN,

		/**
		 * Outcomming buffer
		 */
		OUT
	}

	public static final class Status {

		public static final class Client {

			public static final PacketType<PingPacket>  PING  = new PacketType<>(PingPacket.class, PacketStatusInPing.class, Protocol.STATUS, IN);
			public static final PacketType<StartPacket> START = new PacketType<>(StartPacket.class, PacketStatusInStart.class, Protocol.STATUS, IN);
		}

		public static final class Server {

			public static final PacketType<PongPacket>       PONG        = new PacketType<>(PongPacket.class, PacketStatusOutPong.class, Protocol.STATUS, OUT);
			public static final PacketType<ServerInfoPacket> SERVER_INFO = new PacketType<>(ServerInfoPacket.class, PacketStatusOutServerInfo.class, Protocol.STATUS, OUT);
		}

	}

	public static final class Handshake {

		public static final class Server {

			public static final PacketType<HandShakePacket> HANDSHAKE = new PacketType<>(HandShakePacket.class, PacketHandshakingInSetProtocol.class, Protocol.HANDSHAKE, OUT);
		}

	}
}
