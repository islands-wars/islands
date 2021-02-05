package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityNBTQuery;

/**
 * File <b>EntityNBTQueryPacket</b> located on fr.islandswars.api.net.packet.play.client
 * EntityNBTQueryPacket is a part of islands.
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
 * Created the 05/02/2021 at 15:49
 * @since 0.1
 * <p>
 * Used when Shift+F3+I is pressed while looking at an entity.
 * TODO debug...
 */
public class EntityNBTQueryPacket extends GamePacket<PacketPlayInEntityNBTQuery> {

	protected EntityNBTQueryPacket(PacketPlayInEntityNBTQuery handle) {
		super(handle);
	}

	/**
	 * An incremental ID so that the client can verify that the response matches.
	 *
	 * @return transaction id
	 */
	public int getTransactionID() {
		return handle.b();
	}

	/**
	 * An incremental ID so that the client can verify that the response matches.
	 *
	 * @param id the transaction id
	 */
	public void setTransactionID(int id) {
		setHandleValue("a", id);
	}

	/**
	 * @return the ID of the entity to query.
	 */
	public int getEntityID() {
		return handle.c();
	}

	/**
	 * @param id the ID of the entity to query.
	 */
	public void setEntityID(int id) {
		setHandleValue("b", id);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
