package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindow;

/**
 * File <b>OpenWindowOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * OpenWindowOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 17:54
 * @since 0.1
 */
public class OpenWindowOutPacket extends GamePacket<PacketPlayOutOpenWindow> {

	protected OpenWindowOutPacket(PacketPlayOutOpenWindow handle) {
		super(handle);
	}

	/**
	 * @return the window type to use for display. Contained in the minecraft:menu registry
	 */
	public int getWindowType() {
		return (int) getHandleValue("b");
	}

	/**
	 * @param type the window type to use for display. Contained in the minecraft:menu registry
	 */
	public void setWindowType(int type) {
		setHandleValue("b", type);
	}

	/**
	 * @return a unique id number for the window to be displayed
	 */
	public int getWindowID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param windowId a unique id number for the window to be displayed
	 */
	public void setWindowID(int windowId) {
		setHandleValue("a", windowId);
	}

	/**
	 * @return the title of the window
	 */
	public String getWindowTitle() {
		return ((IChatBaseComponent) getHandleValue("c")).getText();
	}

	/**
	 * @param title the title of the window
	 */
	public void setWindowTitle(IChatBaseComponent title) {
		setHandleValue("c", title);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.OPEN_WINDOW_OUT;
	}
}
