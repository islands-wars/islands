package fr.islandswars.core.bukkit.task;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.task.TaskType;
import fr.islandswars.api.task.Updater;
import fr.islandswars.api.task.UpdaterManager;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * File <b>TaskManager</b> located on fr.islandswars.core.bukkit.task
 * TaskManager is a part of islands.
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
 * Created the 25/03/2024 at 16:49
 * @since 0.1
 */
public class TaskManager implements UpdaterManager {

    private final IslandsApi                     plugin;
    private final ConcurrentMap<Method, Integer> runningTasks;

    public TaskManager() {
        this.plugin = IslandsApi.getInstance();
        this.runningTasks = new ConcurrentHashMap<>();
    }

    @Override
    public void register(Object updatable) {
        Class<?> updatableClass = updatable.getClass();
        for (var method : updatableClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Updater.class) & method.getParameterCount() == 0) {
                Updater updater = method.getAnnotation(Updater.class);
                method.setAccessible(true);
                TaskType type = updater.type();
                switch (type) {
                    case ASYNC:
                        var asyncTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
                            try {
                                method.invoke(updatable);
                            } catch (Exception e) {
                                //TODO better logging
                                e.printStackTrace();
                            }
                        }, getDelay(updater), getDelta(updater));
                        runningTasks.put(method, asyncTask.getTaskId());
                        break;
                    case SYNC:
                        var syncTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                            try {
                                method.invoke(updatable);
                            } catch (Exception e) {
                                //TODO better logging
                                e.printStackTrace();
                            }
                        }, getDelay(updater), getDelta(updater));
                        runningTasks.put(method, syncTask.getTaskId());
                        break;
                }
            }
        }
    }

    @Override
    public void stop(Object updatable) {
        Class<?> updatableClass = updatable.getClass();
        for (Method method : updatableClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Updater.class) & method.getParameterCount() == 0 & runningTasks.containsKey(method)) {
                Bukkit.getScheduler().cancelTask(runningTasks.remove(method));
                method.setAccessible(false);
            }
        }
    }

    private long getDelay(Updater task) {
        return (long) task.delayed() * task.time().getTimeInTick();
    }

    private long getDelta(Updater task) {
        return (long) task.delta() * task.time().getTimeInTick();
    }
}
