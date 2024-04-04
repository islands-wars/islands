package fr.islandswars.api.command;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.lang.IslandsApiError;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.player.rank.IslandsRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * File <b>IslandsCommand</b> located on fr.islandswars.api.command
 * IslandsCommand is a part of islands.
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
 * Created the 02/04/2024 at 17:59
 * @since 0.1
 */
public abstract class IslandsCommand implements CommandExecutor {

    protected final IslandsApi  api;
    private final   IslandsRank rank;
    private         Command     command;

    public IslandsCommand(String cmd, IslandsApi api, IslandsRank rank) {
        this.rank = rank;
        this.api = api;
        var command = api.getCommand(cmd);
        if (command != null) {
            this.command = command;
            command.setExecutor(this);
            var completer = new TabCompleter() {
                @Override
                public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                    if (sender instanceof Player player) {
                        var islandsPlayer = api.getPlayer(player.getUniqueId());
                        if (islandsPlayer.isPresent()) return getCompleter(command, label, args);
                    }
                    return null;
                }
            };
            command.setTabCompleter(completer);
            api.getCommandManager().registerCommand(this);
        } else api.getInfraLogger().logError(new IslandsApiError("The command provided " + cmd + " is null."));
    }

    public abstract List<String> getCompleter(Command command, String label, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            var islandsPlayer = getPlayer(player);
            if (islandsPlayer.isPresent()) {
                var p = islandsPlayer.get();
                if (p.getMainRank().getRankLevel() <= rank.getRankLevel()) return execute(p, command, label, args);
                else p.getBukkitPlayer().sendMessage(Component.translatable("core.cmd.permission").color(NamedTextColor.RED));
            }
        }
        return true;
    }

    public Optional<IslandsPlayer> getPlayer(Player sender) {
        return api.getPlayer(sender.getUniqueId());
    }

    public abstract boolean execute(IslandsPlayer sender, Command command, String label, String[] args);

    public Optional<Command> getCommand() {
        return Optional.ofNullable(command);
    }

    public IslandsRank getRank() {
        return rank;
    }
}
