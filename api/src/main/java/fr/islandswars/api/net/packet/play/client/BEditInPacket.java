package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayInBEdit;

/**
 * File <b>BEditInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * BEditInPacket is a part of islands.
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
 * Created the 05/02/2021 at 15:01
 * @since 0.1
 */
public class BEditInPacket extends GamePacket<PacketPlayInBEdit> {

	protected BEditInPacket(PacketPlayInBEdit handle) {
		super(handle);
	}

	/**
	 * @return the concerned book
	 */
	public ItemStack getItem() {
		return handle.b();
	}

	/**
	 * @param item the book
	 */
	public void setItem(ItemStack item) {
		setHandleValue("a", item);
	}

	/**
	 * True if the player is signing the book; false if the player is saving a draft.
	 *
	 * @return is sign
	 */
	public boolean isSigning() {
		return handle.c();
	}

	/**
	 * True if the player is signing the book; false if the player is saving a draft.
	 *
	 * @param signing is sign
	 */
	public void setSigning(boolean signing) {
		setHandleValue("b", signing);
	}

	/**
	 * 0: Main hand, 1: Off hand.
	 *
	 * @return the concerned hand
	 */
	public int getHand() {
		return handle.d();
	}

	/**
	 * 0: Main hand, 1: Off hand.
	 *
	 * @param hand the concerned hand
	 */
	public void setHand(int hand) {
		setHandleValue("c", hand);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.BOOK_EDIT_IN;
	}
}
