package fr.islandswars.core.internal.listener.packet;

import fr.islandswars.api.net.PacketEvent;
import fr.islandswars.api.net.PacketHandler;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.net.packet.play.server.ScoreboardTeamOutPacket;

/**
 * File <b>ScoreboardTeamOutHandler</b> located on fr.islandswars.core.internal.listener.packet
 * ScoreboardTeamOutHandler is a part of islands.
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
 * Created the 11/02/2021 at 11:37
 * @since 0.1
 */
public class ScoreboardTeamOutHandler extends PacketHandler<ScoreboardTeamOutPacket> {

	public ScoreboardTeamOutHandler() {
		super(PacketType.Play.Server.SCOREBOARD_TEAM_OUT);
	}

	@Override
	public void handlePacket(PacketEvent<ScoreboardTeamOutPacket> event) {
		var packet = event.getPacket();
		System.out.println("team name : " + packet.getTeamName());
		System.out.println("team display name : " + packet.getDisplayName());
		System.out.println("team prefix : " + packet.getPrefix());
		System.out.println("team suffix : " + packet.getSuffix().toString());
		System.out.println("name tag visibility : " + packet.getNameTagVisibility());
		System.out.println("collision rule : " + packet.getCollisionRule());
		System.out.println("team mode : " + packet.getMode());
		System.out.println("team color : " + packet.getTeamColor());
		System.out.println("players : " + packet.getPlayers());
		System.out.println(" ");
	}
}

