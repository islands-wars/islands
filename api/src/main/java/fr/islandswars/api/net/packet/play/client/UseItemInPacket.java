package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.EnumHand;
import net.minecraft.server.v1_16_R3.MovingObjectPositionBlock;
import net.minecraft.server.v1_16_R3.PacketPlayInUseItem;

/**
 * File <b>UseItemInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * UseItemInPacket is a part of islands.
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
 * Created the 05/02/2021 at 18:06
 * @since 0.1
 */
public class UseItemInPacket extends GamePacket<PacketPlayInUseItem> {

	protected UseItemInPacket(PacketPlayInUseItem handle) {
		super(handle);
	}

	/**
	 * @return block data
	 */
	public MovingObjectPositionBlock getMovingPosBlock() {
		return handle.c();
	}

	/**
	 * @param pos block data
	 */
	public void setMovingPosBlock(MovingObjectPositionBlock pos) {
		setHandleValue("a", pos);
	}

	/**
	 * @return hand
	 */
	public EnumHand getHand() {
		return handle.b();
	}

	/**
	 * @param hand who interact
	 */
	public void setHand(EnumHand hand) {
		setHandleValue("b", hand);
	}

	/**
	 * @return timestamp
	 */
	public long getTimestamp() {
		return handle.timestamp;
	}

	/**
	 * @param timestamp init on constructor
	 */
	public void setTimestamp(long timestamp) {
		handle.timestamp = timestamp;
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.USE_ITEM_IN;
	}
}
