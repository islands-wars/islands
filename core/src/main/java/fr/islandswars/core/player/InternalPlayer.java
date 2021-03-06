package fr.islandswars.core.player;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.bossbar.BarSequence;
import fr.islandswars.api.i18n.Locale;
import fr.islandswars.api.inventory.item.IslandsItem;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.IslandsRank;
import fr.islandswars.api.utils.Preconditions;
import fr.islandswars.core.bukkit.bossbar.InternalBar;
import fr.islandswars.core.bukkit.bossbar.InternalBarSequence;
import fr.islandswars.core.bukkit.scoreboard.InternalScoreboardManager;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

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
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:42
 * @since 0.1
 */
public class InternalPlayer implements IslandsPlayer {

	private final transient List<Bar>         bars;
	private final transient List<BarSequence> sequences;
	private final transient CraftPlayer       player;
	private                 Locale            locale;

	public InternalPlayer(Player player, Locale locale) {
		this.player = (CraftPlayer) player;
		this.bars = new ArrayList<>();
		this.sequences = new ArrayList<>();
		this.locale = locale;
	}

	@Override
	public List<Bar> getDisplayedBars() {
		return bars;
	}

	@Override
	public void displaySequence(BarSequence sequence) {
		Preconditions.checkNotNull(sequence);

		var internalSequence = (InternalBarSequence) sequence;
		if (internalSequence.getViewers().noneMatch(p -> p.equals(this))) {
			sequences.add(sequence);
			internalSequence.addPlayer(this);
		}
	}

	@Override
	public void removeFromBar(Bar bar) {
		Preconditions.checkNotNull(bar);

		var internalBar = (InternalBar) bar;
		if (bar.getViewers().anyMatch(p -> p.equals(this))) {
			bars.remove(bar);
			internalBar.removePlayer(this);
		}
	}

	@Override
	public void disconnect() {
		bars.forEach(bar -> ((InternalBar) bar).removePlayer(this));
		sequences.forEach(seq -> ((InternalBarSequence) seq).removePlayer(this));
		((InternalScoreboardManager) IslandsApi.getInstance().getScoreboardManager()).remove(this);
	}

	@Override
	public Locale getPlayerLocale() {
		return locale;
	}

	@Override
	public void setLocale(Locale locale) {
		if (this.locale != locale) {
			this.locale = locale;
			IslandsApi.getInstance().getScoreboardManager().updateLocale(this);
			IslandsApi.getInstance().getBarManager().updateLocale(this);
			updateInventory();
		}
	}

	@Override
	public CraftPlayer getCraftPlayer() {
		return player;
	}

	@Override
	public void displayBar(Bar bar) {
		Preconditions.checkNotNull(bar);

		var internalBar = (InternalBar) bar;
		if (bar.getViewers().noneMatch(p -> p.equals(this))) {
			bars.add(bar);
			internalBar.addPlayer(this);
		}
	}

	@Override
	public IslandsRank getDisplayedRank() {
		return null;
	}

	@Override
	public void addItem(IslandsItem item, int amount) {
		var slot = -1;
		var inv  = getCraftPlayer().getInventory();
		for (var i = 0; i < 35; i++) {
			if (inv.getItem(i) == null) {
				slot = i;
				break;
			}
		}
		if (slot != -1)
			setItem(item, slot, amount);
	}

	@Override
	public void setItem(IslandsItem item, int slot, int amount) {
		var id = IslandsApi.getInstance().getItemManager().register(item);
		updateInventory(item, slot, id, amount);
	}

	private void updateInventory() {
		var inv = getCraftPlayer().getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			var item = inv.getItem(i);
			if (item != null && item.hasItemMeta()) {
				var meta      = item.getItemMeta();
				var container = meta.getPersistentDataContainer();
				var key       = IslandsApi.getInstance().getKey();
				if (container.has(key, PersistentDataType.INTEGER)) {
					int id     = container.get(key, PersistentDataType.INTEGER);
					int finalI = i;
					IslandsApi.getInstance().getItemManager().exist(id).ifPresent(isItem -> updateInventory(isItem, finalI, id, item.getAmount()));
				}
			}
		}
		player.getInventory().getContents();
	}

	private void updateInventory(IslandsItem item, int slot, int id, int amount) {
		var it = item.build(this);
		it.setAmount(amount);
		var key      = IslandsApi.getInstance().getKey();
		var itemMeta = it.getItemMeta();
		itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, id);
		it.setItemMeta(itemMeta);
		getCraftPlayer().getInventory().setItem(slot, it);
	}
}
