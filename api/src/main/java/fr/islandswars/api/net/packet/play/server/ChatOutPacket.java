package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import java.util.UUID;
import net.minecraft.server.v1_16_R3.ChatMessageType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutChat;

/**
 * File <b>ChatOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * ChatOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 17:46
 * @since TODO edit
 */
public class ChatOutPacket extends GamePacket<PacketPlayOutChat> {

	protected ChatOutPacket(PacketPlayOutChat handle) {
		super(handle);
	}

	/**
	 * @return the text wrapped
	 */
	public IChatBaseComponent getMessage() {
		return (IChatBaseComponent) getHandleValue("a");
	}

	/**
	 * @param message chat message
	 */
	public void setMessage(String message) {
		setHandleValue("a", IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"));
	}

	/**
	 * @return message type
	 */
	public ChatMessageType getMessageType() {
		return handle.d();
	}

	/**
	 * @param type message type
	 */
	public void setMessageType(ChatMessageType type) {
		setHandleValue("b", type);
	}

	/**
	 * @return message id
	 */
	public UUID getMessageID() {
		return (UUID) getHandleValue("c");
	}

	/**
	 * @param id message id
	 */
	public void setMessageID(UUID id) {
		setHandleValue("c", id);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.CHAT_OUT;
	}
}
