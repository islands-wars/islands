package fr.islandswars.api.inventory.item;

import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.player.IslandsPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * File <b>IslandsItem</b> located on fr.islandswars.api.inventory.item
 * IslandsItem is a part of islands.
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
 * Created the 14/02/2021 at 18:57
 * @since 0.1
 */
public class IslandsItem {

	private final Material                               material;
	private final int                                    amount;
	private       String                                 nameKey;
	private final List<Lore>                             lores;
	private       TranslationParameters                  nameParam;
	private       BiConsumer<IslandsPlayer, Cancellable> click, interact;
	private ItemFlag[] flags;

	private IslandsItem(Material material, int amount) {
		this.material = material;
		this.amount = amount;
		this.lores = new ArrayList<>();
	}

	/**
	 * Create an island item
	 *
	 * @param material a material type
	 * @return an item wrapper
	 */
	public static IslandsItem builder(Material material) {
		return builder(material, 1);
	}

	/**
	 * Create an island item
	 *
	 * @param material a material type
	 * @param amount   the quantity
	 * @return an item wrapper
	 */
	public static IslandsItem builder(Material material, int amount) {
		return new IslandsItem(material, amount);
	}

	/**
	 * Provide a translated name without parameters
	 *
	 * @param name translation key
	 * @return this wrapper
	 */
	public IslandsItem withName(String name) {
		return withName(name, TranslationParameters.EMPTY);
	}

	/**
	 * Provide a translated name with parameters
	 *
	 * @param name       translation key
	 * @param parameters translation arguments
	 * @return this wrapper
	 */
	public IslandsItem withName(String name, TranslationParameters parameters) {
		this.nameKey = name;
		this.nameParam = parameters;
		return this;
	}

	/**
	 * Provide a translated lore line without parameters
	 *
	 * @param lore translation key
	 * @return this wrapper
	 */
	public IslandsItem addLoreLine(String lore) {
		return addLoreLine(lore, null);
	}

	/**
	 * Provide a translated lore line with parameters
	 *
	 * @param lore       translation key
	 * @param parameters translation arguments
	 * @return this wrapper
	 */
	public IslandsItem addLoreLine(String lore, TranslationParameters parameters) {
		var line = new Lore(lore, parameters);
		lores.add(line);
		return this;
	}

	/**
	 * Add item flags
	 *
	 * @param flags flag to add
	 * @return this wrapper
	 */
	public IslandsItem addFlags(ItemFlag... flags) {
		this.flags = flags;
		return this;
	}

	/**
	 * Get at this exact time a translated item corresponding to the given player's locale.
	 * Warning, amount is set to 1
	 *
	 * @param player a player to translate from
	 * @return a bukkit item
	 */
	public ItemStack build(IslandsPlayer player) {
		var item = new ItemStack(material, amount);
		var meta = item.getItemMeta();
		if (nameKey != null)
			meta.setDisplayName(ChatColor.RESET + player.getPlayerLocale().format(nameKey, nameParam.getTranslation(player).get()));
		if (lores.size() > 0)
			meta.setLore(buildLore(player));
		if (flags != null)
			meta.addItemFlags(flags);
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Handle click event in inventory
	 *
	 * @param click a click function
	 * @return this wrapper
	 */
	public IslandsItem onClick(BiConsumer<IslandsPlayer, Cancellable> click) {
		this.click = click;
		return this;
	}

	/**
	 * Handle interact event in hotbar, call when right click in air
	 *
	 * @param interact an interact function
	 * @return this wrapper
	 */
	public IslandsItem onInteract(BiConsumer<IslandsPlayer, Cancellable> interact) {
		this.interact = interact;
		return this;
	}

	/**
	 * Call click event if set
	 *
	 * @param player who click
	 * @param event  the wrapper
	 */
	public void handleClick(IslandsPlayer player, InventoryClickEvent event) {
		if (click != null)
			click.accept(player, event);
	}

	/**
	 * Call interact if set
	 *
	 * @param player who right click
	 * @param event  the wrapper
	 */
	public void handleInteract(IslandsPlayer player, PlayerInteractEvent event) {
		if (interact != null)
			interact.accept(player, event);
	}

	private List<String> buildLore(IslandsPlayer player) {
		var result = new ArrayList<String>();
		for (Lore lore : lores) {
			if (lore.parameters != null)
				result.add(ChatColor.RESET + player.getPlayerLocale().format(lore.key, lore.parameters.getTranslation(player).get()));
			else
				result.add(ChatColor.RESET + player.getPlayerLocale().format(lore.key));
		}
		return result;
	}
}
