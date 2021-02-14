package fr.islandswars.api.scoreboard;

import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.net.packet.play.server.ScoreboardTeamOutPacket;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.team.Team;
import java.util.Optional;
import net.md_5.bungee.api.chat.BaseComponent;

/**
 * File <b>ScoreboardManager</b> located on fr.islandswars.api.scoreboard
 * ScoreboardManager is a part of islands.
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
 * Created the 10/02/2021 at 18:17
 * @since 0.1
 */
public interface ScoreboardManager {

	default Scoreboard createTranslatedScoreboard(String key) {
		return createTranslatedScoreboard(key, TranslationParameters.EMPTY);
	}

	Scoreboard createTranslatedScoreboard(String key, TranslationParameters parameters);

	void updateLocale(IslandsPlayer player);

	Optional<Scoreboard> getScoreBoard(IslandsPlayer player);

	Team registerTeam(String name);

	void updateTeam(Team team, ScoreboardTeamOutPacket.EnumTeamPush mode);

}
