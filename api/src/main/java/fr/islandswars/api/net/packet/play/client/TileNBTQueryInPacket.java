package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInTileNBTQuery;

/**
 * File <b>TileNBTQueryInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * TileNBTQueryInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:37
 * @since 0.1
 */
public class TileNBTQueryInPacket extends GamePacket<PacketPlayInTileNBTQuery> {

	protected TileNBTQueryInPacket(PacketPlayInTileNBTQuery handle) {
		super(handle);
	}

	/**
	 * @return the block query by the player
	 */
	public BlockPosition getPosition() {
		return handle.c();
	}

	/**
	 * @param position a new block position
	 */
	public void setPosition(BlockPosition position) {
		setHandleValue("b", position);
	}

	/**
	 * @return an incremental ID so that the client can verify that the response matches
	 */
	public int getTransactionID() {
		return handle.b();
	}

	/**
	 * @param id an incremental ID so that the client can verify that the response matches
	 */
	public void setTransactionID(int id) {
		setHandleValue("a", id);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.TILE_NBT_QUERY_IN;
	}
}
