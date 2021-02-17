package fr.islandswars.core.internal.listener;

import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.inventory.PersonalInventory;
import fr.islandswars.api.inventory.item.IslandsItem;
import fr.islandswars.api.player.IslandsPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

/**
 * File <b>TestInventory</b> located on fr.islandswars.core.internal.listener
 * TestInventory is a part of islands.
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
 * Created the 15/02/2021 at 17:35
 * @since 0.1
 */
public class TestInventory extends PersonalInventory {

	private final TranslationParameters parameters;

	public TestInventory() {
		super("test_inv", 9 * 3);
		this.parameters = new TranslationParameters((p) -> () -> new Object[]{p.getCraftPlayer().getTotalExperience()});
		var delimiter = IslandsItem.builder(Material.GRAY_STAINED_GLASS_PANE);
		var pattern   = "OOOOOOOOOOXXXXXXXOOOOOOOOOO";
		super.supplyDelimiter(pattern, delimiter);
		addItem();
	}

	public void openTo(IslandsPlayer player) {
		super.open(player, parameters);
	}

	private void addItem() {
		super.addItem(IslandsItem.builder(Material.DIAMOND_HOE).withName("item_name").addLoreLine("item_lore").onClick((p, e) -> e.setCancelled(true)).addFlags(ItemFlag.HIDE_ATTRIBUTES));
	}

}
