package fr.islandswars.core.bukkit.scoreboard.team;

import com.google.common.collect.Lists;
import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.net.packet.play.server.ScoreboardTeamOutPacket;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.scoreboard.team.Team;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.v1_16_R3.EnumChatFormat;
import net.minecraft.server.v1_16_R3.ScoreboardTeamBase;
import org.bukkit.entity.Player;

/**
 * File <b>InternalTeam</b> located on fr.islandswars.core.bukkit.scoreboard.team
 * InternalTeam is a part of islands.
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
 * Created the 13/02/2021 at 18:02
 * @since 0.1
 */
public class InternalTeam implements Team {

	private final String                                   name;
	private       String                                   displayName;
	private       String                                   suffix;
	private       String                                   prefix;
	private       EnumChatFormat                           color;
	private       ScoreboardTeamBase.EnumTeamPush          collisionRule;
	private       ScoreboardTeamBase.EnumNameTagVisibility nameTagVisibility;
	private final List<IslandsPlayer>                      viewers;
	private       TranslationParameters                    prefixParam, suffixParam;

	public InternalTeam(String name) {
		this.name = name;
		this.displayName = name;
		this.prefix = "";
		this.suffix = "";
		this.color = EnumChatFormat.RESET;
		this.collisionRule = ScoreboardTeamBase.EnumTeamPush.ALWAYS;
		this.nameTagVisibility = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
		this.viewers = Lists.newArrayList();
		this.prefixParam = TranslationParameters.EMPTY;
		this.suffixParam = TranslationParameters.EMPTY;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getSuffix() {
		return suffix;
	}

	@Override
	public EnumChatFormat getColor() {
		return color;
	}

	@Override
	public ScoreboardTeamBase.EnumTeamPush getCollisionRule() {
		return collisionRule;
	}

	@Override
	public ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility() {
		return nameTagVisibility;
	}

	@Override
	public Stream<IslandsPlayer> getViewers() {
		return viewers.stream();
	}

	@Override
	public void setColor(EnumChatFormat color) {
		this.color = color;
	}

	@Override
	public void setPrefix(String prefix, TranslationParameters parameters) {
		this.prefix = prefix;
		this.prefixParam = parameters;
	}

	@Override
	public void setSuffix(String suffix, TranslationParameters parameters) {
		this.suffix = suffix;
		this.suffixParam = parameters;
	}

	@Override
	public boolean contains(IslandsPlayer player) {
		return viewers.stream().anyMatch(pl -> pl.getCraftPlayer().getName().equals(player.getCraftPlayer().getName()));
	}

	@Override
	public void add(IslandsPlayer player) {
		viewers.add(player);
		update(player, ScoreboardTeamOutPacket.EnumTeamPush.ADD_PLAYERS);
	}

	@Override
	public void remove(IslandsPlayer player) {
		viewers.remove(player);
		update(player, ScoreboardTeamOutPacket.EnumTeamPush.REMOVE);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void setCollisionRule(ScoreboardTeamBase.EnumTeamPush collisionRule) {
		this.collisionRule = collisionRule;
	}

	@Override
	public void setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility visibility) {
		this.nameTagVisibility = visibility;
	}

	@Override
	public TranslationParameters getPrefixParameters() {
		return prefixParam;
	}

	@Override
	public TranslationParameters getSuffixParameters() {
		return suffixParam;
	}

	private void update(IslandsPlayer target, ScoreboardTeamOutPacket.EnumTeamPush mode) {
		viewers.forEach(player -> {
			if (player.getCraftPlayer().getUniqueId().equals(target.getCraftPlayer().getUniqueId()))
				sendUpdate(player, mode, viewers.stream().map(p -> p.getCraftPlayer().getName()).collect(Collectors.toList()));
			else
				sendUpdate(player, mode, Collections.singletonList(target.getCraftPlayer().getName()));
		});
	}

	public void sendUpdate(IslandsPlayer target, ScoreboardTeamOutPacket.EnumTeamPush mode, Collection<String> players) {
		var packetTeam = new ScoreboardTeamOutPacket();
		packetTeam.setTeamName(name);
		packetTeam.setMode(mode);
		packetTeam.setPlayers(players);
		packetTeam.sendToPlayer(target.getCraftPlayer());
	}
}
