package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.EnumChatVisibility;
import net.minecraft.server.v1_16_R3.EnumMainHand;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;

/**
 * File <b>SettingsInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * SettingsInPacket is a part of islands.
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
 * Created the 05/02/2021 at 17:08
 * @since 0.1
 * <p>
 * Sent when the player connects, or when settings are changed.
 */
public class SettingsInPacket extends GamePacket<PacketPlayInSettings> {

	protected SettingsInPacket(PacketPlayInSettings handle) {
		super(handle);
	}

	/**
	 * @return player's locale eg: 'en_GB'
	 */
	public String getLocale() {
		return handle.locale;
	}

	/**
	 * @param locale player's locale eg: 'en_GB'
	 */
	public void setLocale(String locale) {
		handle.locale = locale;
	}

	/**
	 * @return cClient-side render distance, in chunks.
	 */
	public int getViewDistance() {
		return handle.viewDistance;
	}

	/**
	 * @param distance client-side render distance, in chunks.
	 */
	public void setViewDistance(int distance) {
		handle.viewDistance = distance;
	}

	/**
	 * @return chat mdoe
	 */
	public EnumChatVisibility getChatVisibility() {
		return handle.d();
	}

	/**
	 * @param visibility chat mode
	 */
	public void setChatVisibility(EnumChatVisibility visibility) {
		setHandleValue("c", visibility);
	}

	/**
	 * @return chat colors active
	 */
	public boolean isChatColorsActive() {
		return handle.e();
	}

	/**
	 * @param value colors active
	 */
	public void setChatColorActive(boolean value) {
		setHandleValue("d", value);
	}

	/**
	 * Bit 0 (0x01): Cape enabled
	 * Bit 1 (0x02): Jacket enabled
	 * Bit 2 (0x04): Left Sleeve enabled
	 * Bit 3 (0x08): Right Sleeve enabled
	 * Bit 4 (0x10): Left Pants Leg enabled
	 * Bit 5 (0x20): Right Pants Leg enabled
	 * Bit 6 (0x40): Hat enabled
	 *
	 * @return a bit mask
	 */
	public int getDisplayedSkinParts() {
		return handle.f();
	}

	/**
	 * Bit 0 (0x01): Cape enabled
	 * Bit 1 (0x02): Jacket enabled
	 * Bit 2 (0x04): Left Sleeve enabled
	 * Bit 3 (0x08): Right Sleeve enabled
	 * Bit 4 (0x10): Left Pants Leg enabled
	 * Bit 5 (0x20): Right Pants Leg enabled
	 * Bit 6 (0x40): Hat enabled
	 *
	 * @param skinParts a bit mask
	 */
	public void setDisplayedSkinParts(int skinParts) {
		setHandleValue("e", skinParts);
	}

	/**
	 * @return main hand, 0 left
	 */
	public EnumMainHand getMainHand() {
		return handle.getMainHand();
	}

	/**
	 * @param hand main hand, 0 left
	 */
	public void setMainHand(EnumMainHand hand) {
		setHandleValue("f", hand);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.SETTINGS_IN;
	}
}
