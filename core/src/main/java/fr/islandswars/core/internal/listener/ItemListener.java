package fr.islandswars.core.internal.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.core.bukkit.item.InternalIslandsItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * File <b>ItemListener</b> located on fr.islandswars.core.internal.listener
 * ItemListener is a part of islands.
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
 * Created the 07/02/2021 at 22:17
 * @since 0.1
 */
public class ItemListener extends LazyListener {

	public ItemListener(IslandsApi api) {
		super(api);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		event.getWhoClicked();
		if (!(event.getWhoClicked() instanceof Player) || event.getCurrentItem() == null)
			return;

		var islandsPlayer = getFromPlayer((Player) event.getWhoClicked());
		IslandsApi.getInstance().getStorageManager().getItem(event.getCurrentItem()).ifPresent(item -> ((InternalIslandsItem) item).callEvent(islandsPlayer, event));
	}
}
