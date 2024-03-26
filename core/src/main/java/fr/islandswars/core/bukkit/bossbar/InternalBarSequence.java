package fr.islandswars.core.bukkit.bossbar;

import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.bossbar.BarSequence;
import fr.islandswars.api.player.IslandsPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File <b>InternalBarSequence</b> located on fr.islandswars.core.bukkit.bossbar
 * InternalBarSequence is a part of islands.
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
 * Created the 26/03/2024 at 15:05
 * @since 0.1
 */
public class InternalBarSequence implements BarSequence {

    protected final List<InternalBar>   bars;
    private final   List<IslandsPlayer> viewers;
    private final   BukkitBarManager    manager;
    protected       boolean             start;
    protected       int                 index;
    protected       long                timer;

    protected InternalBarSequence(BukkitBarManager manager, InternalBar... bars) {
        this.viewers = new ArrayList<>();
        this.manager = manager;
        this.bars = Arrays.asList(bars);
        this.index = 0;
        this.start = false;
        this.timer = 0;
    }

    @Override
    public void addBar(Bar bar) {
        bars.add((InternalBar) bar);
    }

    @Override
    public void removeBar(Bar bar) {
        bars.remove((InternalBar) bar);
    }

    @Override
    public void setBar(Bar bar, int index) {
        bars.set(index, (InternalBar) bar);
    }

    @Override
    public void subscribe(IslandsPlayer player) {
        viewers.add(player);
        if (start) bars.get(index).displayTo(player);
    }

    @Override
    public void unsubscribe(IslandsPlayer player) {
        viewers.remove(player);
        bars.forEach(bar -> bar.removeTo(player));
    }

    @Override
    public void display() {
        if (!start) {
            this.index = 0;
            this.timer = 0;
            this.start = true;
            setCurrent();
            manager.subscribeBarSequence(this);
        }
    }

    @Override
    public void hide() {
        if (start) {
            this.index = 0;
            this.timer = 0;
            manager.unsubscribeBarSequence(this);
            viewers.forEach(p -> bars.forEach(b -> b.removeTo(p)));
            this.start = false;
        }
    }

    protected void setCurrent() {
        if (start) viewers.forEach(p -> bars.get(index).displayTo(p));
    }

    protected void bump() {
        if (start) {
            viewers.forEach(p -> bars.get(index).removeTo(p));
            index = (index < bars.size() - 1) ? index + 1 : 0;
            setCurrent();
        }
    }
}
