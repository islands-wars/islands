package fr.islandswars.core.player;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.IslandsRank;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * File <b>InternalPlayer</b> located on fr.islandswars.core.player
 * InternalPlayer is a part of islands.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:42
 * @since TODO edit
 */
public class InternalPlayer implements IslandsPlayer {

	private final CraftPlayer player;

	public InternalPlayer(Player player) {
		this.player = (CraftPlayer) player;
	}

	@Override
	public void disconnect() {

	}

	@Override
	public CraftPlayer getCraftPlayer() {
		return player;
	}

	@Override
	public IslandsRank getDisplayedRank() {
		return null;
	}
}
