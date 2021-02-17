package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;

/**
 * File <b>SetCommandMinecartInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * SetCommandMinecartInPacket is a part of islands.
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
 * Created the 05/02/2021 at 16:56
 * @since 0.1
 */
public class SetCommandMinecartInPacket extends GamePacket<PacketPlayInSetCommandMinecart> {

	protected SetCommandMinecartInPacket(PacketPlayInSetCommandMinecart handle) {
		super(handle);
	}

	/**
	 * @return the command block command
	 */
	public String getCommand() {
		return handle.b();
	}

	/**
	 * @param command a new command
	 */
	public void setCommand(String command) {
		setHandleValue("b", command);
	}

	/**
	 * @return the minecart ID
	 */
	public int getEntityID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param entityID the minecraft id
	 */
	public void setEntityID(int entityID) {
		setHandleValue("a", entityID);
	}

	/**
	 * If false, the output of the previous command will not be stored within the command block.
	 *
	 * @return outpout state
	 */
	public boolean trackOutput() {
		return handle.c();
	}

	/**
	 * If false, the output of the previous command will not be stored within the command block.
	 *
	 * @param value output state
	 */
	public void setTrackOutput(boolean value) {
		setHandleValue("c", value);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.SET_COMMAND_MINECART_IN;
	}
}
