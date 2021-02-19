package fr.islandswars.core.internal.listener;

import fr.islandswars.api.inventory.item.CustomIslandsItem;
import fr.islandswars.api.inventory.item.IslandsItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

/**
 * File <b>ItemRegistry</b> located on fr.islandswars.core.internal.listener
 * ItemRegistry is a part of islands.
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
 * Created the 19/02/2021 at 17:36
 * @since 0.1
 */
public class ItemRegistry {

	static final CustomIslandsItem stick = new CustomIslandsItem(IslandsItem.builder(Material.STICK).withName("stick_name")
																		 .addLoreLine(ChatColor.DARK_GRAY + "Baton de sorcier")
																		 .addLoreLine(ChatColor.DARK_GRAY + "clic " + ChatColor.GREEN + "droit " + ChatColor.DARK_GRAY + "pour utiliser")
																		 .onInteract((p, c) -> {
																			 c.setCancelled(true);
																			 Location location  = p.getCraftPlayer().getEyeLocation();
																			 Vector   direction = location.getDirection();
																			 p.getCraftPlayer().getWorld().spawnParticle(Particle.FLAME, location.getX(), location.getY(), location.getZ(), 0,
																														 (float) direction.getX(),
																														 (float) direction.getY(), (float) direction.getZ(), 2.5, null);
																		 }), -1);
}
