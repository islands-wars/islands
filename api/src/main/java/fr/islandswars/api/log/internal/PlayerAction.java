package fr.islandswars.api.log.internal;

import java.util.UUID;

/**
 * File <b>PlayerAction</b> located on fr.islandswars.api.log.internal
 * PlayerAction is a part of islands.
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
 * Created the 04/02/2021 at 17:30
 * @since TODO edit
 */
class PlayerAction {

	private final String name, action;
	private final UUID uuid;

	PlayerAction(String name, Action action, UUID id) {
		this.name = name;
		this.action = action.toString();
		this.uuid = id;
	}
}
