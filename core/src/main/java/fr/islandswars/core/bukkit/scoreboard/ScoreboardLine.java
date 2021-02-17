package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.player.IslandsPlayer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * File <b>ScoreboardLine</b> located on fr.islandswars.core.bukkit.scoreboard
 * ScoreboardLine is a part of islands.
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
 * Created the 13/02/2021 at 16:04
 * @since 0.1
 */
public class ScoreboardLine {

	protected final String                queryKey;
	protected final TranslationParameters parameters;
	private final Map<UUID, String> values;

	public ScoreboardLine(String queryKey) {
		this(queryKey, TranslationParameters.EMPTY);
	}

	public ScoreboardLine(String queryKey, TranslationParameters parameters) {
		this.queryKey = queryKey;
		this.parameters = parameters;
		this.values = new HashMap<>();
	}

	protected String get(IslandsPlayer player) {
		var newScore = player.getPlayerLocale().format(queryKey, parameters.getTranslation(player).get());
		values.put(player.getCraftPlayer().getUniqueId(), newScore);
		return newScore;
	}

	protected boolean needUpdate() {
		return parameters != TranslationParameters.EMPTY;
	}

	protected String getCache(IslandsPlayer player) {
		return values.remove(player.getCraftPlayer().getUniqueId());
	}

	protected void free(IslandsPlayer player) {
		values.remove(player.getCraftPlayer().getUniqueId());
	}

}
