package fr.islandswars.core.player;

import fr.islandswars.api.player.rank.IslandsRank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * File <b>PlayerRank</b> located on fr.islandswars.core.player
 * PlayerRank is a part of islands.
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
 * Created the 23/03/2024 at 23:34
 * @since 0.1
 */
public enum PlayerRank implements IslandsRank {

    ADMIN(1, "core.player.ranks.admin.name", 0xe50101, "core.player.ranks.admin.name.short"),
    PLAYER(5, "core.player.ranks.player.name", 0x999999, "core.player.ranks.player.name.short");

    private final String displayNameKey;
    private final String displayShortNameKey;
    private final int    colorCode;
    private final int    rankLevel;

    PlayerRank(int rankLevel, String displayNameKey, int colorCode, String displayShortNameKey) {
        this.displayNameKey = displayNameKey;
        this.displayShortNameKey = displayShortNameKey;
        this.colorCode = colorCode;
        this.rankLevel = rankLevel;
    }

    public static PlayerRank getHighest(List<String> ranks) {
        return Arrays.stream(PlayerRank.values()).filter(r -> ranks.contains(r.name())).min(Comparator.comparingInt(PlayerRank::getRankLevel)).orElse(PLAYER);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(displayNameKey).color(getRankColor());
    }

    @Override
    public TextColor getRankColor() {
        return TextColor.color(colorCode);
    }

    @Override
    public int getRankLevel() {
        return rankLevel;
    }

    @Override
    public String getShortName() {
        return displayShortNameKey;
    }
}
