package fr.islandswars.api.bossbar;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.module.Module;
import fr.islandswars.api.player.IslandsPlayer;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

/**
 * File <b>BarManager</b> located on fr.islandswars.api.bossbar
 * BarManager is a part of islands.
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
 * Created the 25/03/2024 at 16:36
 * @since 0.1
 */
public abstract class BarManager extends Module {

    private final BossBar.Color DEFAULT_COLOR = BossBar.Color.RED;

    public BarManager(IslandsApi api) {
        super(api);
    }

    public abstract void unregisterPlayer(IslandsPlayer player);

    public Bar createBar(Component text) {
        return createBar(text, DEFAULT_COLOR, BossBar.MAX_PROGRESS, BossBar.Overlay.PROGRESS);
    }

    public abstract Bar createBar(Component text, BossBar.Color color, float progress, BossBar.Overlay overlay);

    public Bar createBar(Component text, float progress) {


        return createBar(text, DEFAULT_COLOR, progress, BossBar.Overlay.PROGRESS);
    }

    public abstract void subscribeBar(Bar bar);

    public abstract void unsubscribeBar(Bar bar);

    public Bar createBar(Component text, BossBar.Color color) {
        return createBar(text, color, BossBar.MAX_PROGRESS, BossBar.Overlay.PROGRESS);
    }
}
