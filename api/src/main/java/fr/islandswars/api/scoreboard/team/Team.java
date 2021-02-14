package fr.islandswars.api.scoreboard.team;

import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.player.IslandsPlayer;
import java.util.stream.Stream;
import net.minecraft.server.v1_16_R3.EnumChatFormat;
import net.minecraft.server.v1_16_R3.ScoreboardTeamBase;

/**
 * File <b>Team</b> located on fr.islandswars.api.scoreboard.team
 * Team is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2021 Islands Wars.
 * <p>
 * islands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 13/02/2021 at 11:10
 * @since 0.1
 */
public interface Team {

	void setColor(EnumChatFormat color);

	default void setPrefix(String prefix) {
		setPrefix(prefix, TranslationParameters.EMPTY);
	}

	void setPrefix(String prefix, TranslationParameters parameters);

	default void setSuffix(String suffix) {
		setSuffix(suffix, TranslationParameters.EMPTY);
	}

	void setSuffix(String suffix, TranslationParameters parameters);

	void setDisplayName(String displayName);

	void setCollisionRule(ScoreboardTeamBase.EnumTeamPush collisionRule);

	void setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility visibility);

	boolean contains(IslandsPlayer player);

	void add(IslandsPlayer player);

	void remove(IslandsPlayer player);

	String getName();

	String getDisplayName();

	String getPrefix();

	String getSuffix();

	EnumChatFormat getColor();

	ScoreboardTeamBase.EnumTeamPush getCollisionRule();

	ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility();

	Stream<IslandsPlayer> getViewers();

	TranslationParameters getPrefixParameters();

	TranslationParameters getSuffixParameters();
}
