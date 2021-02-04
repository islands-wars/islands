package fr.islandswars.api.net;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.player.IslandsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * File <b>PacketEvent</b> located on fr.islandswars.api.net
 * PacketEvent is a part of islands.
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
public class PacketEvent<T extends GamePacket> implements Cancellable {

	private final IslandsPlayer player;
	private final T             packet;
	private       boolean       cancel;

	public PacketEvent(Player player, T packet) {
		this.player = IslandsApi.getInstance().getPlayer(player.getUniqueId()).orElseThrow(() -> new NullPointerException("Try to send a packet to a non-registered player!"));
		this.packet = packet;
		cancel = false;
	}

	/**
	 * Return the packet wrapped in this event
	 *
	 * @return a wrapped packet
	 */
	public T getPacket() {
		return packet;
	}

	/**
	 * Return the player who receive or send the packet
	 *
	 * @return this packet's target
	 */
	public IslandsPlayer getPlayer() {
		return player;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}

