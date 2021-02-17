package fr.islandswars.api.task;

/**
 * File <b>TimeType</b> located on fr.islandswars.api.task
 * TimeType is a part of islands.
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
 * Created the 08/02/2021 at 16:25
 * @since 0.1
 */
public enum TimeType {

	TICK(1),
	SECOND(20),
	TWO_SECONDS(SECOND.timeInTick * 2),
	THIRTY_SECONDS(SECOND.timeInTick * 30),
	MINUTE(SECOND.timeInTick * 60),
	HOUR(MINUTE.timeInTick * 60),
	NONE(-1);

	int timeInTick;

	TimeType(int timeInTick) {
		this.timeInTick = timeInTick;
	}

	/**
	 * @return the time (in tick)
	 */
	public int getTimeInTick() {
		return timeInTick;
	}
}
