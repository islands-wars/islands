package fr.islandswars.core.bukkit.command;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.command.IslandsCommand;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.IslandsRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;

import java.util.List;

/**
 * File <b>PingCommand</b> located on fr.islandswars.core.bukkit.command
 * PingCommand is a part of islands.
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
 * Created the 02/04/2024 at 19:44
 * @since 0.1
 */
public class PingCommand extends IslandsCommand {

    public PingCommand(String cmd, IslandsApi api, IslandsRank rank) {
        super(cmd, api, rank);
    }

    @Override
    public List<String> getCompleter(Command command, String label, String[] args) {
        return null;
    }

    @Override
    public boolean execute(IslandsPlayer sender, Command command, String label, String[] args) {
        sender.getBukkitPlayer().ifPresent(p -> {
            p.sendMessage(Component.translatable("core.cmd.ping.execute", Component.text(p.getPing()).color(NamedTextColor.RED)).color(NamedTextColor.GRAY));
        });
        return true;
    }
}
