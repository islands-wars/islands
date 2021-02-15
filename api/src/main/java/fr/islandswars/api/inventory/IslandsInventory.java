package fr.islandswars.api.inventory;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.inventory.item.IslandsItem;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.utils.Preconditions;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * File <b>IslandsInventory</b> located on fr.islandswars.api.inventory
 * IslandsInventory is a part of islands.
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
 * Created the 14/02/2021 at 18:40
 * @since 0.1
 */
public abstract class IslandsInventory extends LazyListener {

	private final String        name;
	private final int           size;
	private final IslandsItem[] items;

	public IslandsInventory(IslandsApi api, String name, int size) {
		super(api);
		this.name = name;
		this.size = size;
		this.items = new IslandsItem[size];
	}

	public void addItem(IslandsItem item) {
		for (int i = 0; i < size; i++) {
			if (items[i] == null) {
				items[i] = item;
				return;
			}
		}
	}

	public void setItem(int slot, IslandsItem item) {
		items[slot] = item;
	}

	public Inventory build(IslandsPlayer player, TranslationParameters parameters) {
		Inventory inv = Bukkit.createInventory(null, size, player.getPlayerLocale().format(name, parameters.getTranslation(player).get()));
		for (int i = 0; i < size; i++) {
			if (items[i] != null)
				inv.setItem(i, items[i].build(player));
		}
		return inv;
	}

	public void handleClick(IslandsPlayer player, InventoryClickEvent event, int slot) {
		if (slot > size || slot < 0)
			return;
		if (items[slot] != null) {
			items[slot].handleClick(player, event);
		}
	}

	public void supplyPattern(String pattern, IslandsItem item) {
		Preconditions.checkState(pattern, ref -> ref.length() == size);
		Preconditions.checkNotNull(item);

		item.onClick((p, e) -> e.setCancelled(true));

		for (var i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c != ' ' && c != 'X')
				System.out.println(i);
		}
	}

	public void supplyDelimiter(String pattern, IslandsItem item) {
		Preconditions.checkState(pattern, ref -> ref.length() == size);
		Preconditions.checkNotNull(item);

		item.onClick((p, e) -> e.setCancelled(true)).withName("");
		supplyPattern(pattern, item);
	}

	public void open(IslandsPlayer player) {
		open(player, TranslationParameters.EMPTY);
	}

	public abstract void open(IslandsPlayer player, TranslationParameters parameters);

}
