package fr.islandswars.api.net.packet.login.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketLoginOutSetCompression;

/**
 * File <b>SetCompressionPacket</b> located on fr.islandswars.api.net.packet.login.server
 * SetCompressionPacket is a part of islands.
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
 * Created the 05/02/2021 at 14:16
 * @since TODO edit
 */
public class SetCompressionPacket extends GamePacket<PacketLoginOutSetCompression> {

	protected SetCompressionPacket(PacketLoginOutSetCompression handle) {
		super(handle);
	}

	/**
	 * If compression is active (meaning this packet has been sent by a client) the
	 * server will encode all packets using zlib according to this scheme :
	 * No	Packet Length	  VarInt	Length of Data Length + compressed length of (Packet ID + Data)
	 * No	Data Length	      VarInt	Length of uncompressed (Packet ID + Data) or 0
	 * Yes	Packet ID	      Varint	zlib compressed packet ID (see the sections below)
	 * Data	        Byte Array	zlib compressed packet data (see the sections below)
	 *
	 * @return the compression label
	 */
	public int getThresholdValue() {
		return (int) getHandleValue("a");
	}

	public void setThresholdValue(int threshold) {
		setHandleValue("a", threshold);
	}

	@Override
	public PacketType getType() {
		return PacketType.Login.Server.COMPRESSION;
	}
}

