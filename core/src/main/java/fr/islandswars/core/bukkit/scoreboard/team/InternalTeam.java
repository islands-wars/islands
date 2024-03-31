package fr.islandswars.core.bukkit.scoreboard.team;

import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.team.IslandsTeam;
import fr.islandswars.core.bukkit.scoreboard.InternalScoreboardManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

/**
 * File <b>InternalTeam</b> located on fr.islandswars.core.bukkit.scoreboard.team
 * InternalTeam is a part of islands.
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
 * Created the 31/03/2024 at 18:29
 * @since 0.1
 */
public class InternalTeam implements IslandsTeam {

    private final String                    name;
    private final List<IslandsPlayer>       members;
    private final InternalScoreboardManager manager;
    private       Component                 suffix, prefix, displayName;
    private NamedTextColor color;

    public InternalTeam(String name, InternalScoreboardManager instance) {
        this.members = new ArrayList<>();
        this.manager = instance;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Component getDisplayName() {
        return displayName;
    }

    @Override
    public void setDisplayName(Component displayName) {
        this.displayName = displayName;
        manager.update(this);
    }

    @Override
    public void addPlayer(IslandsPlayer player) {
        members.add(player);
        manager.addPlayer(this, player);
    }

    @Override
    public void removePlayer(IslandsPlayer player) {
        members.remove(player);
        manager.removePlayer(this, player);
    }

    public Component getPrefix() {
        return prefix;
    }

    @Override
    public void setPrefix(Component prefix) {
        this.prefix = prefix;
        manager.update(this);
    }

    public Component getSuffix() {
        return suffix;
    }

    @Override
    public void setSuffix(Component suffix) {
        this.suffix = suffix;
        manager.update(this);
    }

    public NamedTextColor getColor() {
        return color;
    }

    @Override
    public void setColor(NamedTextColor color) {
        this.color = color;
        manager.update(this);
    }

    public List<IslandsPlayer> getMembers() {
        return members;
    }
}
