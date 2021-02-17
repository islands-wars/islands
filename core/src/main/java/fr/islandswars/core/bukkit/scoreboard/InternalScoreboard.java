package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.packet.play.server.ScoreboardDisplayObjectiveOutPacket;
import fr.islandswars.api.net.packet.play.server.ScoreboardObjectiveOutPacket;
import fr.islandswars.api.net.packet.play.server.ScoreboardScoreOutPacket;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.Scoreboard;
import fr.islandswars.api.utils.Preconditions;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.ScoreboardServer;

/**
 * File <b>InternalScoreboard</b> located on fr.islandswars.core.bukkit.scoreboard
 * InternalScoreboard is a part of islands.
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
 * Created the 11/02/2021 at 09:05
 * @since 0.1
 */
public class InternalScoreboard implements Scoreboard {

	private final TranslationParameters titleParameters;
	private final String                titleKey;
	private final String                objectiveId;
	private final Set<IslandsPlayer>    viewers;
	private final List<ScoreboardLine>  lines;
	protected     boolean               update;
	protected     int                   titleTick, scoreTick, lastTitleTick, lastScoreTick;

	public InternalScoreboard(String titleKey, TranslationParameters titleParameters, String objectiveId) {
		this.titleParameters = titleParameters;
		this.viewers = new HashSet<>();
		this.lines = new ArrayList<>();
		this.objectiveId = objectiveId;
		this.titleKey = titleKey;
		this.update = false;
		this.titleTick = 0;
		this.scoreTick = 0;
		this.lastScoreTick = 0;
		this.lastTitleTick = 0;
	}

	@Override
	public Scoreboard addLine(String text) {
		var line = new ScoreboardLine(text);
		lines.add(line);
		return this;
	}

	@Override
	public Scoreboard addLineWithParameters(String text, TranslationParameters parameters) {
		var line = new ScoreboardLine(text, parameters);
		lines.add(line);
		return this;
	}

	@Override
	public void addPlayer(IslandsPlayer player) {
		if (IslandsApi.getInstance().getScoreboardManager().getScoreBoard(player).isPresent())
			return;

		viewers.add(player);

		updateObjective(player, ScoreboardObjectiveOutPacket.ObjectiveMode.CREATE);
		updateScore(player, ScoreboardServer.Action.CHANGE, true);
	}

	@Override
	public void removePlayer(IslandsPlayer player) {
		if (viewers.remove(player)) {
			updateObjective(player, ScoreboardObjectiveOutPacket.ObjectiveMode.REMOVE);
			lines.forEach(line -> line.free(player));
		}
	}

	@Override
	public Stream<IslandsPlayer> getViewers() {
		return viewers.stream();
	}

	@Override
	public void paramTitleUpdate(int tick) {
		Preconditions.checkState(tick, ref -> ref > 0);
		update = true;

		this.titleTick = tick;
	}

	@Override
	public void paramScoreUpdate(int tick) {
		Preconditions.checkState(tick, ref -> ref > 0);
		update = true;

		this.scoreTick = tick;
	}

	public void updateScore(IslandsPlayer player, ScoreboardServer.Action action, boolean force) {
		for (int i = 0; i < lines.size(); i++) {
			var line = lines.get(i);
			if (force) {
				resetScore(player, line);

				var scorePacket = new ScoreboardScoreOutPacket();
				scorePacket.setObjectiveName(objectiveId);
				scorePacket.setAction(action);
				scorePacket.setLine(line.get(player));
				if (action == ScoreboardServer.Action.CHANGE)
					scorePacket.setLineNumber(i);
				sendTo(player, scorePacket);
			} else if (line.needUpdate()) {
				resetScore(player, line);

				var scorePacket = new ScoreboardScoreOutPacket();
				scorePacket.setObjectiveName(objectiveId);
				scorePacket.setAction(action);
				scorePacket.setLine(line.get(player));
				if (action == ScoreboardServer.Action.CHANGE)
					scorePacket.setLineNumber(i);
				sendTo(player, scorePacket);
			}
		}
	}

	public void updateObjective(IslandsPlayer player, ScoreboardObjectiveOutPacket.ObjectiveMode mode) {
		var objectivePacket = new ScoreboardObjectiveOutPacket();
		objectivePacket.setDisplayType(IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
		objectivePacket.setMode(mode);
		objectivePacket.setObjectiveName(objectiveId);
		if (mode == ScoreboardObjectiveOutPacket.ObjectiveMode.UPDATE || mode == ScoreboardObjectiveOutPacket.ObjectiveMode.CREATE) {
			objectivePacket.setDisplayType(IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
			objectivePacket.setDisplayName(player.getPlayerLocale().format(titleKey, titleParameters.getTranslation(player).get()));
		}
		sendTo(player, objectivePacket);

		if (mode == ScoreboardObjectiveOutPacket.ObjectiveMode.CREATE) {
			var displayObjectivePacket = new ScoreboardDisplayObjectiveOutPacket();
			displayObjectivePacket.setSlot(ScoreboardDisplayObjectiveOutPacket.SlotMode.SIDEBAR);
			displayObjectivePacket.setObjectiveName(objectiveId);
			sendTo(player, displayObjectivePacket);
		}
	}

	private void resetScore(IslandsPlayer player, ScoreboardLine line) { //thx minecraft...
		var cache = line.getCache(player);
		if (cache != null) {
			var resetScorePacket = new ScoreboardScoreOutPacket();
			resetScorePacket.setObjectiveName(objectiveId);
			resetScorePacket.setAction(ScoreboardServer.Action.REMOVE);
			resetScorePacket.setLine(cache);
			sendTo(player, resetScorePacket);
		}
	}

	private void sendTo(IslandsPlayer player, GamePacket<?> packet) {
		packet.sendToPlayer(player.getCraftPlayer());
	}

}
