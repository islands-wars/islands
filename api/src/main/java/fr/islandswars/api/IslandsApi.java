package fr.islandswars.api;

import fr.islandswars.api.bossbar.BarManager;
import fr.islandswars.api.locale.Translatable;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.api.module.ModuleManager;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.task.UpdaterManager;
import fr.islandswars.api.utils.Preconditions;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * File <b>IslandsApi</b> located on fr.islandswars.api
 * IslandsApi is a part of islands.
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
 * Created the 23/03/2024 at 19:59
 * @since 0.1
 */
public abstract class IslandsApi extends JavaPlugin implements ModuleManager {

    private static IslandsApi instance;

    protected IslandsApi() {
        if (instance == null) instance = this;
    }

    public static IslandsApi getInstance() {
        return instance;
    }

    @Override
    public abstract void onLoad();

    @Override
    public abstract void onDisable();

    @Override
    public abstract void onEnable();

    public abstract List<? extends IslandsPlayer> getPlayers();

    public abstract Optional<IslandsPlayer> getPlayer(UUID playerId);

    public abstract UpdaterManager getUpdaterManager();

    public abstract BarManager getBarManager();

    public abstract Translatable getTranslatable();

    public void registerEvent(Listener listener) {
        Preconditions.checkNotNull(listener);

        getServer().getPluginManager().registerEvents(listener, instance);
    }

    public <T extends Event> void registerEvent(Class<T> eventType, Consumer<T> consumer) {
        getServer().getPluginManager().registerEvent(eventType, new Listener() {
        }, EventPriority.LOW, (listener, event) -> {
            if (eventType.isInstance(event)) {
                try {
                    consumer.accept(eventType.cast(event));
                } catch (Exception e) {
                    getInfraLogger().logError(e);
                }
            }
        }, this, false);
    }

    public abstract InfraLogger getInfraLogger();
}
