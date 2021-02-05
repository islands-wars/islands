package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayInAutoRecipe;

/**
 * File <b>AutoRecipeInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * AutoRecipeInPacket is a part of islands.
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
 * Created the 05/02/2021 at 14:39
 * @since 0.1
 * <p>
 * This packet is sent when a player clicks a recipe in the crafting book that is craftable (white border).
 */
public class AutoRecipeInPacket extends GamePacket<PacketPlayInAutoRecipe> {

	protected AutoRecipeInPacket(PacketPlayInAutoRecipe handle) {
		super(handle);
	}

	/**
	 * @return the recipe ID
	 */
	public MinecraftKey getKey() {
		return handle.c();
	}

	/**
	 * @param key the recipe id
	 */
	public void setkey(MinecraftKey key) {
		setHandleValue("b", key);
	}

	/**
	 * @return the windows ID
	 */
	public int getWindowsID() {
		return handle.b();
	}

	/**
	 * @param id the windows ID
	 */
	public void setWindowsID(int id) {
		setHandleValue("a", id);
	}

	/**
	 * Affects the amount of items processed; true if shift is down when clicked.
	 *
	 * @return is shift down pressed
	 */
	public boolean getMakeAll() {
		return handle.d();
	}

	/**
	 * Affects the amount of items processed; true if shift is down when clicked.
	 *
	 * @param makeAll is shift down pressed
	 */
	public void setMakeAll(boolean makeAll) {
		setHandleValue("c", makeAll);
	}

	@Override
	public PacketType getType() {
		return null;
	}
}
