package fr.islandswars.core.bukkit.bossbar;

import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.utils.Preconditions;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

/**
 * File <b>InternalBar</b> located on fr.islandswars.core.bukkit.bossbar
 * InternalBar is a part of islands.
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
 * Created the 25/03/2024 at 17:17
 * @since 0.1
 */
public class InternalBar implements Bar {

    protected final float              initialProgress;
    private final   BossBar            bukkitBar;
    private final   InternalBarManager manager;
    protected       IslandsPlayer      viewer;
    protected       long               timer;
    protected       long               timeInTickOnScreen;
    protected       long               delta;
    protected       boolean            autoUpdate;

    protected InternalBar(InternalBarManager manager, Component text, BossBar.Color color, float progress, BossBar.Overlay overlay) {
        this.bukkitBar = BossBar.bossBar(text, progress, color, overlay);
        this.timer = 0L;
        this.manager = manager;
        this.initialProgress = progress;
        this.timeInTickOnScreen = -1;
        this.autoUpdate = false;
    }

    public InternalBar withFlag(BossBar.Flag... flags) {
        bukkitBar.addFlags(flags);
        return this;
    }

    @Override
    public Bar withTimeOnScreen(long timeInTickOnScreen, long delta) {
        Preconditions.checkState(timeInTickOnScreen, (ref) -> ref >= 1);
        Preconditions.checkState(delta, (ref) -> ref <= timeInTickOnScreen);
        this.timeInTickOnScreen = timeInTickOnScreen;
        this.delta = delta;
        return this;
    }

    @Override
    public Bar withAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        return this;
    }

    @Override
    public void displayTo(IslandsPlayer player) {
        player.getBukkitPlayer().ifPresent(p -> {
            p.showBossBar(bukkitBar);
            if (timeInTickOnScreen >= 1) {
                if (viewer != null) {
                    removeTo(viewer);
                    manager.unsubscribeBar(this);
                }
                manager.subscribeBar(this);
                this.viewer = player;
            }
        });

    }

    @Override
    public void removeTo(IslandsPlayer player) {
        player.getBukkitPlayer().ifPresent(p -> {
            p.hideBossBar(bukkitBar);
            if (viewer != null) {
                this.viewer = null;
                this.timer = 0L;
                manager.unsubscribeBar(this);
            }
        });

    }

    protected void setProgress(float newProgress) {
        Preconditions.checkState(newProgress, (ref) -> ref >= BossBar.MIN_PROGRESS && ref <= BossBar.MAX_PROGRESS);

        bukkitBar.progress(newProgress);
    }
}
