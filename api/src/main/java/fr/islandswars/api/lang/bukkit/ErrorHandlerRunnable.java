package fr.islandswars.api.lang.bukkit;

import fr.islandswars.api.utils.ErrorHandler;

/**
 * File <b>ErrorHandlerRunnable</b> located on fr.islandswars.api.lang.bukkit
 * ErrorHandlerRunnable is a part of islands.
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
 * @author DeltaEvo
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:23
 * @since 0.1
 */
public class ErrorHandlerRunnable implements Runnable {

	private final Runnable     owner;
	private final ErrorHandler handler;

	public ErrorHandlerRunnable(Runnable owner, ErrorHandler handler) {
		this.owner = owner;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			owner.run();
		} catch (Throwable t) {
			handler.handle(t);
		}
	}
}
