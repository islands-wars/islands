package fr.islandswars.api.module.bukkit;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.module.Module;
import fr.islandswars.api.utils.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * File <b>ArrowModule</b> located on fr.islandswars.api.module.bukkit
 * ArrowModule is a part of islands.
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
 * Created the 24/03/2024 at 19:43
 * @since 0.1
 */
public class ArrowModule extends Module {

    private long removeTime = 20; //in tick, 1 seconds

    public ArrowModule(IslandsApi api) {
        super(api);
    }

    @Override
    public void onDisable() {
        enabled = false;
    }

    @Override
    public void onEnable() {
        enabled = true;
        api.registerEvent(this);
    }

    @Override
    public void onLoad() {

    }

    @EventHandler
    public void onShoot(ProjectileHitEvent event) {
        if (enabled) {
            if (event.getEntityType() == EntityType.ARROW) {
                Arrow arrow = (Arrow) event.getEntity();
                Bukkit.getScheduler().runTaskLater(api, () -> {
                    if (arrow.isValid() && !arrow.isDead())
                        arrow.remove();
                }, removeTime);
            }
        } else
            event.getHandlers().unregister(this);
    }

    public void setRemoveTime(long removeTime) {
        Preconditions.checkState(removeTime, ref -> ref >= 0);
        this.removeTime = removeTime;
    }
}
