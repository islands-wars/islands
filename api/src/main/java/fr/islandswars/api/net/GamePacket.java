package fr.islandswars.api.net;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.net.wrapper.Wrapper;
import java.util.stream.Stream;
import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.entity.Player;

/**
 * File <b>GamePacket</b> located on fr.islandswars.api.net
 * GamePacket is a part of islands.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:11
 * @since TODO edit
 */
public abstract class GamePacket<T extends Packet> extends Wrapper<T> {

	protected T handle;

	protected GamePacket(T handle) {
		super(handle);
		this.handle = handle;
	}

	/**
	 * Represent the default NMS packet
	 *
	 * @return a NMS wrapped packet
	 */
	public final T getNMSPacket() {
		return this.handle;
	}

	/**
	 * @return the associed packet type to this packet
	 */
	public abstract PacketType getType();

	/**
	 * Queued this packet to the given player
	 *
	 * @param receiver a connected bukkit player
	 */
	public final void sendToPlayer(Player receiver) {
		IslandsApi.getInstance().getProtocolManager().sendPacket(receiver, this);
	}

	/**
	 * Queued this packet to the given player
	 *
	 * @param receivers a connected stream of bukkit player
	 */
	public final void sendToPlayer(Stream<Player> receivers) {
		receivers.forEach(this::sendToPlayer);
	}
}
