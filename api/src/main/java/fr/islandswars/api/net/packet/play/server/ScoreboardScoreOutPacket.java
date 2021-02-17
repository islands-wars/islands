package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_16_R3.ScoreboardServer;

/**
 * File <b>ScoreboardScoreOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * ScoreboardScoreOutPacket is a part of islands.
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
 * @author Valentin Chassignol (Vinetos), {@literal <vinetos@islandswars.fr>}
 * Created the 06/02/2021 at 18:11
 * @since 0.1
 */
public class ScoreboardScoreOutPacket extends GamePacket<PacketPlayOutScoreboardScore> {

	protected ScoreboardScoreOutPacket(PacketPlayOutScoreboardScore handle) {
		super(handle);
	}

	public ScoreboardScoreOutPacket() {
		super(new PacketPlayOutScoreboardScore());
	}

	/**
	 * @return score name
	 */
	public String getLine() {
		return (String) getHandleValue("a");
	}

	/**
	 * @param name this score name
	 */
	public void setLine(String name) {
		setHandleValue("a", name);
	}

	/**
	 * @return objective name
	 */
	public String getObjectiveName() {
		return (String) getHandleValue("b");
	}

	/**
	 * @param name objective name
	 */
	public void setObjectiveName(String name) {
		setHandleValue("b", name);
	}

	/**
	 * @return scoreboard action
	 */
	public ScoreboardServer.Action getAction() {
		return (ScoreboardServer.Action) getHandleValue("d");
	}

	/**
	 * @param action an action to do
	 */
	public void setAction(ScoreboardServer.Action action) {
		setHandleValue("d", action);
	}

	/**
	 * @return line count
	 */
	public int getLineNumber() {
		return (int) getHandleValue("c");
	}

	/**
	 * @param lineNumber total of line
	 */
	public void setLineNumber(int lineNumber) {
		setHandleValue("c", lineNumber);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.SCOREBOARD_SCORE_OUT;
	}
}
