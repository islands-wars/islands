package fr.islandswars.api.bossbar;

import fr.islandswars.api.player.IslandsPlayer;

/**
 * File <b>BarSequence</b> located on fr.islandswars.api.bossbar
 * BarSequence is a part of islands.
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
 * Created the 26/03/2024 at 15:02
 * @since 0.1
 */
public interface BarSequence {

    void addBar(Bar bar);

    void removeBar(Bar bar);

    void setBar(Bar bar, int index);

    void subscribe(IslandsPlayer player);

    void unsubscribe(IslandsPlayer player);

    void display();

    void hide();

}
