package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayInChat;

/**
 * File <b>ChatInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * ChatInPacket is a part of islands.
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
 * Created the 05/02/2021 at 15:18
 * @since 0.1
 */
public class ChatInPacket extends GamePacket<PacketPlayInChat> {

	protected ChatInPacket(PacketPlayInChat handle) {
		super(handle);
	}

	/**
	 * @return the message (weither it's a command or not)
	 */
	public String getMessage() {
		return handle.b();
	}

	/**
	 * @param message a 256 length message
	 */
	public void setMessage(String message) {
		if (message.length() > 256)
			message = message.substring(0, 256);

		setHandleValue("a", message);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.CHAT_IN;
	}
}
