package fr.islandswars.core.internal.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.item.ItemType;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.api.log.internal.Action;
import fr.islandswars.api.log.internal.PlayerLog;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.bukkit.item.InternalIslandsItem;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * File <b>PlayerListener</b> located on fr.islandswars.core.internal.listener
 * PlayerListener is a part of islands.
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
 * Created the 04/02/2021 at 17:46
 * @since 0.1
 */
public class PlayerListener extends LazyListener {

	public PlayerListener(IslandsApi api) {
		super(api);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onConnect(PlayerJoinEvent event) {
		var player = event.getPlayer();
		((IslandsCore) api).addPlayer(player);
		api.getInfraLogger().createCustomLog(PlayerLog.class, Level.INFO, "Player " + player.getName() + " joined the game.").setPlayer(player, Action.CONNECT).log();

		IslandsItem item = api.getStorageManager().newItem(new ItemType(Material.DIAMOND_SWORD)).onClick((p, c) -> {
			p.getCraftPlayer().sendMessage("tudu");
			c.setCancelled(true);
		}).withProperties(prop -> {
			prop.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			prop.addEnchantment(Enchantment.FIRE_ASPECT, 1);
		});
		ItemStack it = item.toBukkitItem();
		player.getInventory().setItem(0, it);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onLeave(PlayerQuitEvent event) {
		var islandsPlayer = getFromPlayer(event.getPlayer());
		((IslandsCore) api).removePlayer(islandsPlayer);
		api.getInfraLogger().createCustomLog(PlayerLog.class, Level.INFO, "Player " + islandsPlayer.getCraftPlayer().getName() + " leaved the game.").setPlayer(islandsPlayer, Action.LEAVE).log();
	}

}
