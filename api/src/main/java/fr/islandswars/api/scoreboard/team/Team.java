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
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 13/02/2021 at 11:10
 * @since 0.1
 */
public interface Team {

	/**
	 * @param color this team color
	 */
	void setColor(EnumChatFormat color);

	/**
	 * @param prefix a prefix to use for this team
	 */
	default void setPrefix(String prefix) {
		setPrefix(prefix, TranslationParameters.EMPTY);
	}

	/**
	 * @param prefix     a prefix with some formats to handle
	 * @param parameters prefix translation formatters
	 */
	void setPrefix(String prefix, TranslationParameters parameters);

	/**
	 * @param suffix a suffix to use for this team
	 */
	default void setSuffix(String suffix) {
		setSuffix(suffix, TranslationParameters.EMPTY);
	}

	/**
	 * @param suffix     a suffix with some formats to handle
	 * @param parameters suffix translation formatters
	 */
	void setSuffix(String suffix, TranslationParameters parameters);

	/**
	 * @param displayName team display name, not needed
	 */
	void setDisplayName(String displayName);

	/**
	 * @param collisionRule hitbox collision
	 */
	void setCollisionRule(ScoreboardTeamBase.EnumTeamPush collisionRule);

	/**
	 * @param visibility for this name tag
	 */
	void setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility visibility);

	/**
	 * @param player a player to test appartenance with
	 * @return if this player is in this team
	 */
	boolean contains(IslandsPlayer player);

	/**
	 * @param player a player to add to this team
	 */
	void add(IslandsPlayer player);

	/**
	 * @param player a player to remove from this team
	 */
	void remove(IslandsPlayer player);

	/**
	 * @return team name, automatically compute
	 */
	String getName();

	/**
	 * @return team displayed name
	 */
	String getDisplayName();

	/**
	 * @return team prefix
	 */
	String getPrefix();

	/**
	 * @return team suffix
	 */
	String getSuffix();

	/**
	 * @return team color
	 */
	EnumChatFormat getColor();

	/**
	 * @return team collision rule
	 */
	ScoreboardTeamBase.EnumTeamPush getCollisionRule();

	/**
	 * @return team name tag visibility
	 */
	ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility();

	/**
	 * @return players in this team
	 */
	Stream<IslandsPlayer> getViewers();

	/**
	 * @return wrapper for prefix translation
	 */
	TranslationParameters getPrefixParameters();

	/**
	 * @return wrapper for suffix translation
	 */
	TranslationParameters getSuffixParameters();
}
