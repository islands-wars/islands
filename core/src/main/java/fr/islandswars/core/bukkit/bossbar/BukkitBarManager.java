package fr.islandswars.core.bukkit.bossbar;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.bossbar.BarManager;
import fr.islandswars.api.bossbar.BarProperties;
import fr.islandswars.api.bossbar.BarSequence;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.task.TaskType;
import fr.islandswars.api.task.TimeType;
import fr.islandswars.api.task.Updater;
import fr.islandswars.api.utils.Preconditions;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.v1_16_R3.BossBattle;
import net.minecraft.server.v1_16_R3.PacketPlayOutBoss;
import org.bukkit.boss.BarFlag;

/**
 * File <b>BukkitBarManager</b> located on fr.islandswars.core.bukkit.bossbar
 * BukkitBarManager is a part of islands.
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
 * Created the 08/02/2021 at 18:21
 * @since 0.1
 */
public class BukkitBarManager extends BarManager {

	private static final Random                                    RANDOM = new Random();
	private final        CopyOnWriteArrayList<InternalBar>         updatableBar;
	private final        CopyOnWriteArrayList<InternalBarSequence> updatableSequences;
	private final        List<BossBattle.BarColor>                 colors;

	public BukkitBarManager(IslandsApi api) {
		super(api);
		this.colors = List.of(BossBattle.BarColor.values());
		this.updatableBar = new CopyOnWriteArrayList<>();
		this.updatableSequences = new CopyOnWriteArrayList<>();
	}

	@Override
	public BarSequence createSequence(BarProperties properties, Bar... bars) {
		Preconditions.checkNotNull(properties);
		Preconditions.checkNotNull(bars);
		Preconditions.checkState(bars, ref -> ref.length > 1);

		var internalBars = Stream.of(bars).map(InternalBar.class::cast).collect(Collectors.toList());
		internalBars.forEach(bar -> bar.provideProperties(properties, false));
		return new InternalBarSequence(internalBars);
	}

	@Override
	public Bar createBar(String title, BossBattle.BarColor color, BossBattle.BarStyle style, BarFlag... flags) {
		Preconditions.checkNotNull(title);
		Preconditions.checkNotNull(color);
		Preconditions.checkNotNull(style);

		InternalBar bar = new InternalBar(title, color, style);
		for (var flag : flags) {
			switch (flag) {
				case CREATE_FOG:
					bar.setCreateFog(true);
					break;
				case DARKEN_SKY:
					bar.setDarkenSky(true);
					break;
				case PLAY_BOSS_MUSIC:
					bar.setPlayMusic(true);
					break;
			}
		}
		return bar;
	}

	@Override
	public void onDisable() {
		api.getUpdaterManager().stop(this);
	}

	@Override
	public void onEnable() {
		api.getUpdaterManager().register(this);
	}

	@Override
	public void onLoad() {

	}

	@Updater(type = TaskType.SYNC, time = TimeType.TICK)
	public void updateBar() {
		updatableBar.forEach(bar -> {
			if (bar.properties != null) {
				updateBar(bar, bar.properties);
			} else
				updatableBar.remove(bar);
		});
		updatableSequences.forEach(sequence -> {
			var bar        = (InternalBar) sequence.getCurrentBar();
			var properties = bar.properties;
			if (bar.getProgress() + bar.properties.getPercent() / 100f + (properties.useReverse() ? 1 : 0) > 1) {
				InternalBar newBar = sequence.getNextBar();
				sequence.getViewers().forEach(newBar::addPlayer);
				sequence.getViewers().forEach(bar::removePlayer);
				bar.setProgress(properties.useReverse() ? 1 : 0);
				//updateBar(newBar, newBar.properties);
			} //else updateBar(bar, properties);
		});
	}

	@Override
	public void updateLocale(IslandsPlayer object) {
		updatableSequences.forEach(seq -> {
			seq.removePlayer(object);
			seq.addPlayer(object);
		});
		updatableBar.forEach(bar -> {
			bar.removePlayer(object);
			bar.addPlayer(object);
		});
	}

	private void updateBar(InternalBar bar, BarProperties properties) {
		if (properties.getTick() >= 1) {
			if (bar.repeatCount < properties.getRepeat()) {
				if (++bar.lastTick >= properties.getTick()) {
					if (properties.useMagicColor()) {
						bar.color = colors.get(RANDOM.nextInt(colors.size()));
						bar.update(PacketPlayOutBoss.Action.UPDATE_STYLE);
					}
					if (bar.getProgress() <= 0 || bar.getProgress() == 1) {
						bar.setProgress(properties.useReverse() ? 1 : 0);
						if (properties.getRepeat() >= 1)
							bar.repeatCount++;
					} else if (bar.getProgress() >= 1)
						bar.setProgress(0);
					float percent = properties.getPercent() / 100f;
					if (properties.useReverse())
						bar.setProgress((bar.getProgress() - percent) < 0 ? 0 : (bar.getProgress() - percent));
					else
						bar.setProgress((bar.getProgress() + percent) > 1 ? 1 : (bar.getProgress() + percent));
					bar.lastTick = 0;
				}
			} else updatableBar.remove(bar);
		}
		if (properties.getTitleDelta() >= 1)
			if (++bar.lastUpdateTitle >= properties.getTitleDelta()) {
				bar.update(PacketPlayOutBoss.Action.UPDATE_NAME);
				bar.lastUpdateTitle = 0;
			}
	}

	void removeBar(InternalBar bar) {
		updatableBar.remove(bar);
	}

	void removeSequence(InternalBarSequence sequence) {
		updatableSequences.remove(sequence);
	}

	void scheduleBar(InternalBar bar) {
		if (!updatableBar.contains(bar))
			updatableBar.add(bar);
	}

	void scheduleSequence(InternalBarSequence sequence) {
		if (!updatableSequences.contains(sequence))
			updatableSequences.add(sequence);
	}
}