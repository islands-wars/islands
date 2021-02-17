package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_16_R3.TileEntityCommand.Type;

/**
 * File <b>SetCommandBlockInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * SetCommandBlockInPacket is a part of islands.
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
 * Created the 05/02/2021 at 16:51
 * @since 0.1
 */
public class SetCommandBlockInPacket extends GamePacket<PacketPlayInSetCommandBlock> {

	protected SetCommandBlockInPacket(PacketPlayInSetCommandBlock handle) {
		super(handle);
	}

	/**
	 * @return command block position
	 */
	public BlockPosition getPosition() {
		return handle.b();
	}

	/**
	 * @param position command block position
	 */
	public void setPosition(BlockPosition position) {
		setHandleValue("a", position);
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return handle.c();
	}

	/**
	 * @param cmd a new command
	 */
	public void setCommand(String cmd) {
		setHandleValue("b", cmd);
	}

	/**
	 * @return command type
	 */
	public Type getCmdType() {
		return handle.g();
	}

	/**
	 * @param type a new command type
	 */
	public void setType(Type type) {
		setHandleValue("f", type);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.SET_COMMAND_BLOCK_IN;
	}
}
