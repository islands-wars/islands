package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import java.util.Collection;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;

/**
 * File <b>ScoreboardTeamOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * ScoreboardTeamOutPacket is a part of islands.
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
 * @author Valentin Chassignol (Vinetos), {@literal <vinetos@islandswars.fr>}
 * Created the 06/02/2021 at 18:19
 * @since 0.1
 */
public class ScoreboardTeamOutPacket extends GamePacket<PacketPlayOutScoreboardTeam> {

	private int option = 0;

	protected ScoreboardTeamOutPacket(PacketPlayOutScoreboardTeam handle) {
		super(handle);
	}

	public ScoreboardTeamOutPacket() {
		this(new PacketPlayOutScoreboardTeam());
	}

	public void allowFriendlyFire() {
		option |= 0b01;
		setHandleValue("j", (int) getHandleValue("j") | 0b01);
	}

	public boolean canSeeFriendlyInvisible() {
		return ((int) getHandleValue("j") & 0b10) != 0;
	}

	public int getChatColor(int chatColor) {
		return (int) getHandleValue("g");
	}

	/*public CollisionRule getCollisionRule() {
		return CollisionRule.getCollisionRule((String) getHandleValue("f"));
	}*/

	public String getDisplayName() {
		return (String) getHandleValue("b");
	}

	/*public Action getMode() {
		return Action.getAction((int) getHandleValue("i"));
	}*/

	//TODO deprecated
	/*public NameTagVisibility getNameTagVisibility() {
		return NameTagVisibility.getNameTagVisibility((String) getHandleValue("e"));
	}*/

	@SuppressWarnings("unchecked")
	public Collection<String> getPlayers() {
		return (Collection<String>) getHandleValue("h");
	}

	public String getPrefix() {
		return (String) getHandleValue("c");
	}

	public String getSuffix() {
		return (String) getHandleValue("d");
	}

	public String getTeamName() {
		return (String) getHandleValue("a");
	}

	public boolean hasAllowedFriendlyFire() {
		return ((int) getHandleValue("j") & 0b01) != 0;
	}

	public void setCanSeeFriendlyInvisible() {
		option |= 0b10;
		setHandleValue("j", option);
	}

	/**
	 * -1 by default
	 *
	 * @param chatColor the chatColor
	 */
	public void setChatColor(int chatColor) {
		setHandleValue("g", chatColor);
	}

	/*public void setCollisionRule(CollisionRule collisionRule) {
		setHandleValue("f", collisionRule.getCollisionRule());
	}*/

	public void setDisplayName(String displayName) {
		setHandleValue("b", displayName);
	}

	/*public void setMode(Action mode) {
		setHandleValue("i", mode.getMode());
	}*/

	/*public void setNameTagVisibility(NameTagVisibility nameTagVisibility) {
		setHandleValue("e", nameTagVisibility.getNameTagVisibility());
	}*/

	public void setPlayers(Collection<String> players) {
		setHandleValue("h", players);
	}

	public void setPrefix(String prefix) {
		setHandleValue("c", prefix);
	}

	public void setSuffix(String suffix) {
		setHandleValue("d", suffix);
	}

	public void setTeamName(String name) {
		setHandleValue("a", name);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.SCOREBOARD_TEAM_OUT;
	}
}
