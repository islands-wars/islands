package fr.islandswars.core.internal.listener.packet;

import fr.islandswars.api.net.PacketEvent;
import fr.islandswars.api.net.PacketHandler;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.net.packet.play.server.WindowItemsOutPacket;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.bukkit.storage.StorageFactory;

/**
 * File <b>WindowItemHandler</b> located on fr.islandswars.core.internal.listener.packet
 * WindowItemHandler is a part of islands.
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
 * Created the 07/02/2021 at 22:15
 * @since 0.1
 */
public class WindowItemHandler extends PacketHandler<WindowItemsOutPacket> {

	private final StorageFactory storageManager;

	public WindowItemHandler(IslandsCore core) {
		super(PacketType.Play.Server.WINDOW_ITEMS_OUT);
		this.storageManager = (StorageFactory) core.getStorageManager();
	}

	@Override
	public void handlePacket(PacketEvent<WindowItemsOutPacket> event) {
		var items = event.getPacket().getItemStacks();
		items.forEach(item -> {
			if (item == null || !item.hasTag() || !item.getTag().hasKeyOfType("id", 3))
				return;
			storageManager.translateItem(event.getPlayer(), item);
		});
	}
}
