package fr.islandswars.core.player;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.event.PlayerDataSynchronizeEvent;
import fr.islandswars.api.i18n.Locale;
import fr.islandswars.core.IslandsCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

/**
 * File <b>PlayerChannelListener</b> located on fr.islandswars.core.player
 * PlayerChannelListener is a part of islands.
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 20/02/2021 at 11:12
 * @since 0.1
 * <p>
 * Receive shortly after the player join.
 */
public class PlayerChannelListener implements PluginMessageListener {

	private static final String     CHANNEL = ((IslandsCore) IslandsApi.getInstance()).getChannel();
	private final        IslandsApi api     = IslandsApi.getInstance();

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
		if (!channel.equals(CHANNEL)) {
			return;
		}
		var p = new InternalPlayer(player, Locale.FRENCH);
		((IslandsCore) api).addPlayer(p);
		Bukkit.getPluginManager().callEvent(new PlayerDataSynchronizeEvent(p));
	}
}
