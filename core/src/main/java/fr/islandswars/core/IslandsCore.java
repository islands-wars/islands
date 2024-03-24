package fr.islandswars.core;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.locale.Translatable;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.core.internal.listener.PlayerListener;
import fr.islandswars.core.internal.locale.TranslationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * File <b>IslandsCore</b> located on fr.islandswars.core
 * IslandsCore is a part of islands.
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
 * Created the 23/03/2024 at 20:19
 * @since 0.1
 */
public class IslandsCore extends IslandsApi {

    private final List<IslandsPlayer> players;
    private final TranslationLoader   translatable;

    public IslandsCore() {
        this.players = new ArrayList<>();
        this.translatable = new TranslationLoader();
    }

    @Override
    public void onLoad() {
        translatable.load("locale.core");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        new PlayerListener(this);
    }

    @Override
    public List<? extends IslandsPlayer> getPlayers() {
        return players;
    }

    @Override
    public Optional<IslandsPlayer> getPlayer(UUID playerId) {
        return players.stream().filter(p -> p.getBukkitPlayer().getUniqueId().equals(playerId)).findFirst();
    }

    @Override
    public Translatable getTranslatable() {
        return translatable;
    }

    public void addPlayer(IslandsPlayer player) {
        players.add(player);
    }

    public void removePlayer(IslandsPlayer player) {
        players.remove(player);
    }
}
