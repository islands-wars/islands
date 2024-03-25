package fr.islandswars.core.bukkit.bossbar;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.bossbar.BarManager;
import net.kyori.adventure.text.Component;

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

    public BukkitBarManager(IslandsApi api) {
        super(api);
    }

    @Override
    public Bar createBar(Component text) {
        return null;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onLoad() {

    }
}
