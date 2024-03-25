package fr.islandswars.core.bukkit.bossbar;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.bossbar.BarManager;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.task.TaskType;
import fr.islandswars.api.task.TimeType;
import fr.islandswars.api.task.Updater;
import fr.islandswars.api.utils.Preconditions;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * File <b>BukkitBarManager</b> located on fr.islandswars.core.bukkit.bossbar
 * BukkitBarManager is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2024 Islands Wars.
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
 * @author Jangliu, {@literal <jangliu@islandswars.fr>}
 * Created the 25/03/2024 at 16:43
 * @since 0.1
 */
public class BukkitBarManager extends BarManager {

    private final List<InternalBar> updatableBar;

    public BukkitBarManager(IslandsApi api) {
        super(api);
        this.updatableBar = new ArrayList<>();
    }

    @Override
    public void unregisterPlayer(IslandsPlayer player) {
        for (InternalBar bar : updatableBar) {
            if (bar.viewer != null && bar.viewer.equals(player)) {
                bar.timer = 0;
            }
        }
        updatableBar.removeIf(bar -> bar.viewer != null && bar.viewer.equals(player));
    }

    @Override
    public Bar createBar(Component text, BossBar.Color color, float progress, BossBar.Overlay overlay) {
        Preconditions.checkNotNull(text);
        Preconditions.checkNotNull(color);
        Preconditions.checkNotNull(overlay);
        Preconditions.checkState(progress, (p) -> p >= BossBar.MIN_PROGRESS && p <= BossBar.MAX_PROGRESS);

        return new InternalBar(text, color, progress, overlay);
    }

    @Override
    public void subscribeBar(Bar bar) {
        updatableBar.add((InternalBar) bar);
    }

    @Override
    public void unsubscribeBar(Bar bar) {
        ((InternalBar) bar).timer = 0L;
        updatableBar.remove(bar);
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

    @Updater(type = TaskType.SYNC, time = TimeType.TICK, delta = 1)
    public void updateBar() {
        for (InternalBar bar : updatableBar) {
            if (bar.timeInTickOnScreen >= 1) {
                if (bar.timer > bar.timeInTickOnScreen) {
                    bar.removeTo(bar.viewer);
                } else {
                    if (bar.autoUpdate) {
                        if (bar.timer % bar.delta == 0)
                            bar.setProgress(bar.initialProgress - bar.initialProgress / bar.timeInTickOnScreen * bar.timer);
                    }
                    bar.timer += 1;
                }
            }
        }
        updatableBar.removeIf(bar -> bar.timeInTickOnScreen >= 1 && bar.timer > bar.timeInTickOnScreen + 1);
    }
}
