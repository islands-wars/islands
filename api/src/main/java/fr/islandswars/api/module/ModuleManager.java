package fr.islandswars.api.module;

import java.util.Optional;

/**
 * File <b>ModuleManager</b> located on fr.islandswars.api.module
 * ModuleManager is a part of islands.
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
 * Created the 08/02/2021 at 16:14
 * @since 0.1
 */
public interface ModuleManager {

	/**
	 * Get this module, if it has been registered
	 *
	 * @param module a module to get
	 * @param <T>    extends Module
	 * @return a wrapepd module instance, if found
	 */
	<T extends Module> Optional<T> getModule(Class<T> module);

	/**
	 * Get this api module, if it has been registered
	 *
	 * @param module a module to get
	 * @param <T>    extends Module
	 * @return a wrapped module instance, if found
	 */
	@SuppressWarnings("unchecked")
	default <T extends Module> Optional<T> getModule(ApiModule module) {
		return (Optional<T>) getModule(module.getModuleClass());
	}

	/**
	 * Register an existing module and load it if the plugin is already enabled
	 *
	 * @param module a module type to enabled
	 * @param <T>    extends Mocule
	 * @return this module
	 */
	@SuppressWarnings("unchecked")
	default <T extends Module> T registerApiModule(ApiModule module) {
		return (T) registerModule(module.getModuleClass());
	}

	/**
	 * Register the given module and load it if the plugin is already enabled
	 *
	 * @param module a module class to register
	 * @param <T>    extends Mocule
	 * @return a new instance of this module
	 */
	<T extends Module> T registerModule(Class<T> module);

	/**
	 * Unregister an existing module, if it has been registered and enabled
	 *
	 * @param module a module to unregister
	 * @param <T>    extends Module
	 */
	default <T extends Module> void unregisterApiModule(ApiModule module) {
		unregisterModule(module.getModuleClass());
	}

	/**
	 * Unregister the given module, if it has been registered and enabled
	 *
	 * @param module a module to unregister
	 * @param <T>    extends Module
	 */
	<T extends Module> void unregisterModule(Class<T> module);
}
