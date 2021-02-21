package fr.islandswars.core.internal.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.event.PlayerDataSynchronizeEvent;
import fr.islandswars.api.i18n.Locale;
import fr.islandswars.api.listener.LazyListener;
import fr.islandswars.api.log.internal.Action;
import fr.islandswars.api.log.internal.PlayerLog;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.bukkit.scoreboard.InternalScoreboardManager;
import fr.islandswars.core.player.InternalPlayer;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
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
		if (api.debug()) {
			var p = new InternalPlayer(event.getPlayer(), Locale.FRENCH);
			((IslandsCore) api).addPlayer(p);
			Bukkit.getPluginManager().callEvent(new PlayerDataSynchronizeEvent(p));
		} else
			api.getServer().getScheduler().runTaskLater(api, () -> {
				var player = event.getPlayer();
				if (!((CraftPlayer) player).getHandle().playerConnection.isDisconnected())
					player.kickPlayer("unable to retrieve data");
			}, 20 * 5);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onIslandsPlayerConnect(PlayerDataSynchronizeEvent event) {
		api.getInfraLogger().createCustomLog(PlayerLog.class, Level.INFO, "Player " + event.getPlayer().getCraftPlayer().getName() + " joined the game.").setPlayer(event.getPlayer(), Action.CONNECT).log();
		((InternalScoreboardManager) api.getScoreboardManager()).injectTeams(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onLeave(PlayerQuitEvent event) {
		var islandsPlayer = api.getPlayer(event.getPlayer().getUniqueId());
		islandsPlayer.ifPresent(((IslandsCore) api)::removePlayer);
		api.getInfraLogger().createCustomLog(PlayerLog.class, Level.INFO, "Player " + event.getPlayer().getName() + " leaved the game.").setPlayer(event.getPlayer(), Action.LEAVE).log();
	}
}
