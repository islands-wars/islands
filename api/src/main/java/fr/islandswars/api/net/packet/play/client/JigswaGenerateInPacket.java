package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInJigsawGenerate;

/**
 * File <b>JigswaGenerateInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * JigswaGenerateInPacket is a part of islands.
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
 * Created the 05/02/2021 at 16:27
 * @since 0.1
 */
public class JigswaGenerateInPacket extends GamePacket<PacketPlayInJigsawGenerate> {

	protected JigswaGenerateInPacket(PacketPlayInJigsawGenerate handle) {
		super(handle);
	}

	/**
	 * @return position to generate the structure
	 */
	public BlockPosition getBlockPosition() {
		return handle.b();
	}

	/**
	 * @param position to generate the structure
	 */
	public void setBlockPosition(BlockPosition position) {
		setHandleValue("a", position);
	}

	/**
	 * @return value of the levels slider/max depth to generate
	 */
	public int getLevel() {
		return handle.c();
	}

	/**
	 * @param level value of the levels slider/max depth to generate
	 */
	public void setLevel(int level) {
		setHandleValue("b", level);
	}

	/**
	 * @return destroy jigsaw after clicking
	 */
	public boolean isKeepJigsaws() {
		return handle.d();
	}

	/**
	 * @param value destroy jigswa after clicking
	 */
	public void setKeepJigswa(boolean value) {
		setHandleValue("c", value);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
