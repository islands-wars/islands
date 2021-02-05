package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.EnumDirection;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;

/**
 * File <b>BlockDigInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * BlockDigInPacket is a part of islands.
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
 * Created the 05/02/2021 at 15:06
 * @since 0.1
 */
public class BlockDigInPacket extends GamePacket<PacketPlayInBlockDig> {

	protected BlockDigInPacket(PacketPlayInBlockDig handle) {
		super(handle);
	}

	/**
	 * @return the action
	 */
	public PacketPlayInBlockDig.EnumPlayerDigType getDigType() {
		return handle.d();
	}

	/**
	 * @param digType the action
	 */
	public void setDigType(PacketPlayInBlockDig.EnumPlayerDigType digType) {
		setHandleValue("c", digType);
	}

	/**
	 * @return the current block position
	 */
	public BlockPosition getBlockPosition() {
		return handle.b();
	}

	/**
	 * @param blockPosition the block position
	 */
	public void setBlockPosition(BlockPosition blockPosition) {
		setHandleValue("a", blockPosition);
	}

	/**
	 * @return the face
	 */
	public EnumDirection getDirection() {
		return handle.c();
	}

	/**
	 * @param dir the face
	 */
	public void setDirection(EnumDirection dir) {
		setHandleValue("b", dir);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.BLOCK_DIG_IN;
	}
}
