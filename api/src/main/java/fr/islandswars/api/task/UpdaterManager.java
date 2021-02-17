package fr.islandswars.api.task;

import javax.annotation.concurrent.ThreadSafe;
import org.bukkit.scheduler.BukkitTask;

/**
 * File <b>UpdaterManager</b> located on fr.islandswars.api.task
 * UpdaterManager is a part of islands.
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
 * Created the 08/02/2021 at 16:26
 * @since 0.1
 */
@ThreadSafe
public interface UpdaterManager {

	/**
	 * Will look inside the class method to register every method which has
	 * a {@link Updater} annotation (and no method's parameters)
	 * The bukkit task {@link BukkitTask#getTaskId() id} will be sync from the hash of class + method name
	 *
	 * @param updatable a class to register
	 */
	void register(Object updatable);

	/**
	 * Will stop all task associated to this object, according to hash
	 *
	 * @param updatable a class to stop
	 */
	void stop(Object updatable);

}
