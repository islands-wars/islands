package fr.islandswars.api.inventory;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.player.IslandsPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * File <b>PersonalInventory</b> located on fr.islandswars.api.inventory
 * PersonalInventory is a part of islands.
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
 * Created the 14/02/2021 at 18:54
 * @since 0.1
 * <p>
 * One inventory per player, rebuilt every time we need to open it, memory friendly
 */
public abstract class PersonalInventory extends IslandsInventory {

	private final List<UUID> inventories;

	public PersonalInventory(String name, int size) {
		super(IslandsApi.getInstance(), name, size);
		this.inventories = new ArrayList<>();
	}

	@Override
	public void open(IslandsPlayer player, TranslationParameters parameters) {
		var inv = build(player, parameters);
		inventories.add(player.getCraftPlayer().getUniqueId());
		player.getCraftPlayer().openInventory(inv);
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			var player = getFromPlayer((Player) event.getWhoClicked());
			if (event.getClickedInventory() != player.getCraftPlayer().getInventory()) {
				if (inventories.contains(player.getCraftPlayer().getUniqueId())) {
					var slot = event.getSlot();
					handleClick(player, event, slot);
				}
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		inventories.remove(event.getPlayer().getUniqueId());
	}
}
