package fr.islandswars.api.listener;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.player.IslandsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * File <b>LazyListener</b> located on fr.islandswars.api.listener
 * LazyListener is a part of islands.
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
 * Created the 23/03/2024 at 23:11
 * @since 0.1
 */
public abstract class LazyListener implements Listener {

    protected final IslandsApi api;

    public LazyListener(IslandsApi api) {
        this.api = api;
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (isValidEventHandler(method)) {
                registerEventHandler(method);
            }
        }
    }

    private boolean isValidEventHandler(Method method) {
        return method.isAnnotationPresent(EventHandler.class) &&
                method.getParameterCount() == 1 &&
                Event.class.isAssignableFrom(method.getParameterTypes()[0]) &&
                method.getReturnType().equals(void.class) &&
                Modifier.isPublic(method.getModifiers());
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> void registerEventHandler(Method method) {
        EventHandler  annotation = method.getAnnotation(EventHandler.class);
        EventPriority priority   = annotation.priority();

        Class<T> eventType = (Class<T>) method.getParameterTypes()[0];
        Consumer<T> consumer = event -> {
            try {
                method.invoke(this, event);
            } catch (Exception e) {
                api.getInfraLogger().logError(e);
            }
        };
        IslandsApi.getInstance().registerEvent(eventType, consumer);
    }

    protected Optional<IslandsPlayer> getOptionalPlayer(Player player) {
        return api.getPlayer(player.getUniqueId());
    }
}
