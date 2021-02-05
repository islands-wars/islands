package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockPlace;

/**
 * File <b>BlockPlacePacket</b> located on fr.islandswars.api.net.packet.play.client
 * BlockPlacePacket is a part of islands.
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
 * Created the 05/02/2021 at 15:10
 * @since 0.1
 * <p>
 * TODO wtf is this packet
 */
public class BlockPlaceInPacket extends GamePacket<PacketPlayInBlockPlace> {

	protected BlockPlaceInPacket(PacketPlayInBlockPlace handle) {
		super(handle);
	}

	/**
	 * @return concerned hand
	 */
	public EnumHand getHand() {
		return handle.b();
	}

	/**
	 * @param hand concerned hand
	 */
	public void setHand(EnumHand hand) {
		setHandleValue("a", hand);
	}

	/**
	 * @return timestamp (init when the packet is serialized)
	 */
	public long getTimeStamp() {
		return handle.timestamp;
	}

	/**
	 * @param timeStamp a new timestamp
	 */
	public void setTimeStamp(long timeStamp) {
		handle.timestamp = timeStamp;
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.BLOCK_PLACE_IN;
	}
}
