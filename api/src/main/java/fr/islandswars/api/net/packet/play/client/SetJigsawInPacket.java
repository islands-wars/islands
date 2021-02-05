package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_16_R3.TileEntityJigsaw;

/**
 * File <b>SetJigsawInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * SetJigsawInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:03
 * @since 0.1
 * <p>
 * Sent when Done is pressed on the Jigsaw Block interface.
 */
public class SetJigsawInPacket extends GamePacket<PacketPlayInSetJigsaw> {

	protected SetJigsawInPacket(PacketPlayInSetJigsaw handle) {
		super(handle);
	}

	/**
	 * @return block entity location
	 */
	public BlockPosition getPosition() {
		return handle.b();
	}

	/**
	 * @param position block entity location
	 */
	public void setBlockPosition(BlockPosition position) {
		setHandleValue("a", position);
	}

	/**
	 * @return rollable if the attached piece can be rotated, else aligned.
	 */
	public String getFinalState() {
		return handle.f();
	}

	/**
	 * @param state rollable if the attached piece can be rotated, else aligned.
	 */
	public void setFinalState(String state) {
		setHandleValue("e", state);
	}

	/**
	 * @return the type
	 */
	public TileEntityJigsaw.JointType getJighsawType() {
		return handle.g();
	}

	/**
	 * @param type the type
	 */
	public void setJigsawType(TileEntityJigsaw.JointType type) {
		setHandleValue("f", type);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
