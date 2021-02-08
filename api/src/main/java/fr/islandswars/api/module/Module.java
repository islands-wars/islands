package fr.islandswars.api.module;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.listener.LazyListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * File <b>Module</b> located on fr.islandswars.api.module
 * Module is a part of islands.
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
 * Created the 08/02/2021 at 16:13
 * @since 0.1
 */
public abstract class Module extends LazyListener {

	protected final IslandsApi api;
	protected       boolean    enabled;

	public Module(IslandsApi api) {
		super(api);
		this.api = api;
		enabled = false;
	}

	/**
	 * Disable this module, call in {@link JavaPlugin#onDisable()} or when using {@link ModuleManager#unregisterModule(Class)}
	 */
	public abstract void onDisable();

	/**
	 * Enable this module, call in {@link JavaPlugin#onEnable()} ()} or when using {@link ModuleManager#registerModule(Class)}
	 */
	public abstract void onEnable();

	/**
	 * Load this module, call in {@link JavaPlugin#onLoad()}
	 */
	public abstract void onLoad();

}
