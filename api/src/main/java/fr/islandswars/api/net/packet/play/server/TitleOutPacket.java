package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.utils.Preconditions;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;

/**
 * File <b>TitleOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * TitleOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 19:12
 * @since 0.1
 */
public class TitleOutPacket extends GamePacket<PacketPlayOutTitle> {

	protected TitleOutPacket(PacketPlayOutTitle handle) {
		super(handle);
	}

	public TitleOutPacket(String message, PacketPlayOutTitle.EnumTitleAction action, int fadeIn, int stay, int fadeOut) {
		super(new PacketPlayOutTitle(action, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), fadeIn, stay, fadeOut));
	}

	/**
	 * @return which title to send
	 */
	public PacketPlayOutTitle.EnumTitleAction getAction() {
		return (PacketPlayOutTitle.EnumTitleAction) getHandleValue("a");
	}

	/**
	 * @param action a title to send
	 */
	public void setAction(PacketPlayOutTitle.EnumTitleAction action) {
		setHandleValue("a", action);
	}

	/**
	 * @return message
	 */
	public IChatBaseComponent getMessage() {
		return (IChatBaseComponent) getHandleValue("b");
	}

	/**
	 * @param message the message to send
	 */
	public void setMessage(String message) {
		IChatBaseComponent chat = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		setHandleValue("b", chat);
	}

	/**
	 * @return positive time for fade in effect (in ticks)
	 */
	public int getFadeIn() {
		return (int) getHandleValue("c");
	}

	/**
	 * @param fadeIn time effect (in ticks)
	 */
	public void setFadeIn(int fadeIn) {
		Preconditions.checkState(fadeIn, ref -> ref > 0);

		setHandleValue("c", fadeIn);
	}

	/**
	 * @return for how many time the message will be displayed (in ticks)
	 */
	public int getStay() {
		return (int) getHandleValue("d");
	}

	/**
	 * @param stay how many time the message will be displayed (in ticks)
	 */
	public void setStay(int stay) {
		Preconditions.checkState(stay, ref -> ref > 0);

		setHandleValue("d", stay);
	}

	/**
	 * @return time effect (in ticks)
	 */
	public int getFadeOut() {
		return (int) getHandleValue("e");
	}

	/**
	 * @param fadeOut time effect (in ticks)
	 */
	public void setFadeOut(int fadeOut) {
		Preconditions.checkState(fadeOut, ref -> ref > 0);

		setHandleValue("e", fadeOut);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.TITLE_OUT;
	}
}
