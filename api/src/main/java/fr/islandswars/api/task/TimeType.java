package fr.islandswars.api.task;

/**
 * File <b>TimeType</b> located on fr.islandswars.api.task
 * TimeType is a part of islands.
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
 * Created the 25/03/2024 at 16:44
 * @since 0.1
 */
public enum TimeType {

    TICK(1),
    SECOND(20),
    TWO_SECONDS(SECOND.timeInTick * 2),
    THIRTY_SECONDS(SECOND.timeInTick * 30),
    MINUTE(SECOND.timeInTick * 60),
    HOUR(MINUTE.timeInTick * 60);

    final int timeInTick;

    TimeType(int timeInTick) {
        this.timeInTick = timeInTick;
    }

    public int getTimeInTick() {
        return timeInTick;
    }
}
