package fr.islandswars.core.bukkit.scoreboard;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.module.Module;
import fr.islandswars.api.net.packet.play.server.ScoreboardObjectiveOutPacket;
import fr.islandswars.api.net.packet.play.server.ScoreboardTeamOutPacket;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.Scoreboard;
import fr.islandswars.api.scoreboard.ScoreboardManager;
import fr.islandswars.api.scoreboard.team.Team;
import fr.islandswars.api.task.TaskType;
import fr.islandswars.api.task.TimeType;
import fr.islandswars.api.task.Updater;
import fr.islandswars.core.bukkit.scoreboard.team.InternalTeam;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import net.minecraft.server.v1_16_R3.ScoreboardServer;

/**
 * File <b>InternalScoreboardManager</b> located on fr.islandswars.core.bukkit.scoreboard
 * InternalScoreboardManager is a part of islands.
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
 * Created the 11/02/2021 at 09:03
 * @since 0.1
 */
public class InternalScoreboardManager extends Module implements ScoreboardManager {

	private static final String                                   OBJECTIVE_ID = "is-";
	private static final AtomicInteger                            ID           = new AtomicInteger();
	private final        CopyOnWriteArrayList<InternalScoreboard> boards;
	private final        CopyOnWriteArrayList<InternalTeam>       teams;

	public InternalScoreboardManager(IslandsApi api) {
		super(api);
		this.boards = new CopyOnWriteArrayList<>();
		this.teams = new CopyOnWriteArrayList<>();
	}

	@Override
	public Team registerTeam(String name) {
		if (teams.stream().anyMatch(t -> t.getName().equals(name)))
			return null;
		var team = new InternalTeam(name);
		teams.add(team);
		return team;
	}

	@Override
	public void updateTeam(Team team, ScoreboardTeamOutPacket.EnumTeamPush mode) {
		var players = team.getViewers().map(pl -> pl.getCraftPlayer().getName()).collect(Collectors.toList());
		IslandsApi.getInstance().getPlayers().forEach(p -> {
			if (mode == ScoreboardTeamOutPacket.EnumTeamPush.CREATE || mode == ScoreboardTeamOutPacket.EnumTeamPush.UPDATE)
				createUpdateTeam(p, team, mode);
			else {
				var packetTeam = new ScoreboardTeamOutPacket();
				packetTeam.setMode(mode);
				packetTeam.setTeamName(team.getName());
				if (mode == ScoreboardTeamOutPacket.EnumTeamPush.ADD_PLAYERS || mode == ScoreboardTeamOutPacket.EnumTeamPush.REMOVE_PLAYERS) {
					packetTeam.setPlayers(players);
					packetTeam.sendToPlayer(p.getCraftPlayer());
				}
			}
		});
	}

	@Override
	public void onDisable() {
		api.getUpdaterManager().stop(this);
	}

	@Override
	public void onEnable() {
		api.getUpdaterManager().register(this);
	}

	@Override
	public void onLoad() {

	}

	@Updater(type = TaskType.SYNC, time = TimeType.TICK)
	public void updateBoards() {
		boards.stream().filter(b -> b.update).forEach(this::updateBoard);
	}

	@Override
	public Optional<Scoreboard> getScoreBoard(IslandsPlayer player) {
		return boards.stream().filter(b -> b.getViewers().anyMatch(p -> p.getCraftPlayer().getUniqueId() == player.getCraftPlayer().getUniqueId())).findFirst().map(Scoreboard.class::cast);
	}

	@Override
	public void updateLocale(IslandsPlayer player) {
		Optional<Scoreboard> board = getScoreBoard(player);
		board.ifPresent(b -> {
			b.removePlayer(player);
			b.addPlayer(player);
		});
		teams.forEach(t -> createUpdateTeam(player, t, ScoreboardTeamOutPacket.EnumTeamPush.UPDATE));
	}

	@Override
	public Scoreboard createTranslatedScoreboard(String key, TranslationParameters parameters) {
		var board = new InternalScoreboard(key, parameters, OBJECTIVE_ID + ID.getAndIncrement());
		boards.add(board);
		return board;
	}

	public void remove(IslandsPlayer player) {
		boards.forEach(board -> board.removePlayer(player));
		teams.forEach(t -> t.remove(player));
	}

	public void injectTeams(IslandsPlayer player) {
		//new player is joining, send him all registered teams
		teams.forEach(t -> createUpdateTeam(player, t, ScoreboardTeamOutPacket.EnumTeamPush.CREATE));
		//send him teams member
		teams.forEach(t -> {
			if (t.getViewers().iterator().hasNext())
				t.sendUpdate(player, ScoreboardTeamOutPacket.EnumTeamPush.ADD_PLAYERS, t.getViewers().map(p -> p.getCraftPlayer().getName()).collect(Collectors.toList()));
		});
	}

	private void createUpdateTeam(IslandsPlayer player, Team team, ScoreboardTeamOutPacket.EnumTeamPush mode) {
		var packetTeam = new ScoreboardTeamOutPacket();
		packetTeam.setMode(mode);
		packetTeam.setTeamName(team.getName());
		packetTeam.setPrefix(player.getPlayerLocale().format(team.getPrefix(), team.getPrefixParameters().getTranslation(player).get()) + " ");
		packetTeam.setSuffix(" " + player.getPlayerLocale().format(team.getSuffix(), team.getSuffixParameters().getTranslation(player).get()));
		packetTeam.setTeamColor(team.getColor());
		packetTeam.setCollisionRule(team.getCollisionRule());
		packetTeam.setDisplayName(team.getDisplayName());
		packetTeam.setNameTagVisibility(team.getNameTagVisibility());

		packetTeam.sendToPlayer(player.getCraftPlayer());
	}

	private void updateBoard(InternalScoreboard board) {
		if (board.titleTick >= 1)
			if (board.lastTitleTick++ >= board.titleTick) {
				board.getViewers().forEach(p -> board.updateObjective(p, ScoreboardObjectiveOutPacket.ObjectiveMode.UPDATE));
				board.lastTitleTick = 0;
			}

		if (board.scoreTick >= 1)
			if (board.lastScoreTick++ >= board.scoreTick) {
				board.getViewers().forEach(p -> board.updateScore(p, ScoreboardServer.Action.CHANGE, false));
				board.lastScoreTick = 0;
			}
	}
}