package fr.islandswars.api.net.packet.play.client;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;

/**
 * File <b>AdvancementsInPacket</b> located on fr.islandswars.api.net.packet.play.client
 * AdvancementsInPacket is a part of islands.
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
 * Created the 05/02/2021 at 14:34
 * @since 0.1
 */
public class AdvancementsInPacket extends GamePacket<PacketPlayInAdvancements> {

	protected AdvancementsInPacket(PacketPlayInAdvancements handle) {
		super(handle);
	}

	/**
	 * @return the advancements identifier
	 */
	public MinecraftKey getKey() {
		return handle.d();
	}

	/**
	 * @return the corresponding status
	 */
	public PacketPlayInAdvancements.Status getStatus() {
		return handle.c();
	}

	/**
	 * @param key the advancements identifier
	 */
	public void setKey(MinecraftKey key) {
		setHandleValue("b", key);
	}

	/**
	 * @param status status to use
	 */
	public void setStatus(PacketPlayInAdvancements.Status status) {
		setHandleValue("a", status);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Client.ADVANCEMENTS_IN;
	}
}
