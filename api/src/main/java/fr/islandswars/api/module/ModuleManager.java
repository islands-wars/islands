package fr.islandswars.api.module;

import java.util.Optional;

/**
 * File <b>ModuleManager</b> located on fr.islandswars.api.module
 * ModuleManager is a part of islands.
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
 * Created the 24/03/2024 at 19:41
 * @since 0.1
 */
public interface ModuleManager {

    @SuppressWarnings("unchecked")
    default <T extends Module> Optional<T> getModule(ApiModule module) {
        return (Optional<T>) getModule(module.getModuleClass());
    }

    <T extends Module> Optional<T> getModule(Class<T> module);

    @SuppressWarnings("unchecked")
    default <T extends Module> T registerApiModule(ApiModule module) {
        return (T) registerModule(module.getModuleClass());
    }

    <T extends Module> T registerModule(Class<T> module);

    default <T extends Module> void unregisterApiModule(ApiModule module) {
        unregisterModule(module.getModuleClass());
    }

    <T extends Module> void unregisterModule(Class<T> module);
}
