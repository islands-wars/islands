package fr.islandswars.core.bukkit.command;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.command.CommandManager;
import fr.islandswars.api.command.IslandsCommand;
import fr.islandswars.api.utils.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;

import java.util.Map;

/**
 * File <b>InternalCommandManager</b> located on fr.islandswars.core.bukkit.command
 * InternalCommandManager is a part of islands.
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
 * Created the 01/04/2024 at 21:00
 * @since 0.1
 */
public class InternalCommandManager implements CommandManager {

    public InternalCommandManager() {
        unregisterDefaultCommand();
    }


    private void unregisterDefaultCommand() {
        PluginManager        s             = Bukkit.getServer().getPluginManager();
        SimpleCommandMap     commandMap    = ReflectionUtil.getValue(s, "commandMap");
        Map<String, Command> knownCommands = ReflectionUtil.getValue(commandMap, "knownCommands");
        for (Map.Entry<String, Command> entry : knownCommands.entrySet()) {
            entry.getValue().unregister(commandMap);
        }
        knownCommands.clear();
        Bukkit.getCommandAliases().clear();
        Bukkit.getCommandMap().getKnownCommands().clear();
    }

    @Override
    public void registerCommand(IslandsCommand command) {
        command.getCommand().ifPresent(cmd -> {
            Bukkit.getCommandMap().getKnownCommands().forEach((s1, s2) -> {
                if (s1.startsWith(IslandsApi.getInstance().getName().toLowerCase() + ":")) s2.unregister(IslandsApi.getInstance().getServer().getCommandMap());
            });
            Bukkit.getCommandMap().getKnownCommands().remove(IslandsApi.getInstance().getName().toLowerCase() + ":" + cmd.getLabel());
            var logger = IslandsApi.getInstance().getInfraLogger();
            logger.logDebug("Register /" + cmd.getLabel() + " with rank " + command.getRank() + " on method " + command.getClass().getName());
        });
    }
}
