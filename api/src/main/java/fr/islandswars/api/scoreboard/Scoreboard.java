package fr.islandswars.api.scoreboard;

import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.player.IslandsPlayer;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.ScoreboardServer;

/**
 * File <b>Scoreboard</b> located on fr.islandswars.api.scoreboard
 * Scoreboard is a part of islands.
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
public interface Scoreboard {

	/**
	 * Append a new line to this scoreboard
	 *
	 * @param text the line content
	 * @return the current scoreboard
	 */
	Scoreboard addLine(String text);

	Scoreboard addLineWithParameters(String text, TranslationParameters parameters);

	/**
	 * Create this scoreboard for this player, it will use the given map to get {@link Function#apply(Object)}
	 * parameters, according to each line generic type
	 *
	 * @param player a player to open this scoreboard
	 */
	void addPlayer(IslandsPlayer player);

	void removePlayer(IslandsPlayer player);

	void paramTitleUpdate(int tick);

	void paramScoreUpdate(int tick);

	Stream<IslandsPlayer> getViewers();

}
