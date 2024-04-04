package fr.islandswars.api.module;

import fr.islandswars.api.module.bukkit.ArrowModule;

/**
 * File <b>ApiModule</b> located on fr.islandswars.api.module
 * ApiModule is a part of islands.
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
 * Created the 24/03/2024 at 19:42
 * @since 0.1
 */
public enum ApiModule {

    ARROW(ArrowModule.class);

    private final Class<? extends Module> moduleClass;

    ApiModule(Class<? extends Module> moduleClass) {
        this.moduleClass = moduleClass;
    }

    /**
     * @return this module class
     */
    public Class<? extends Module> getModuleClass() {
        return moduleClass;
    }
}

