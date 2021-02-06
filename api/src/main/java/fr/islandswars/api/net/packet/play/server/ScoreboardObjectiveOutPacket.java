package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import java.util.Arrays;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;

/**
 * File <b>ScoreboardObjectiveOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * ScoreboardObjectiveOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 18:06
 * @since 0.1
 */
public class ScoreboardObjectiveOutPacket extends GamePacket<PacketPlayOutScoreboardObjective> {

	protected ScoreboardObjectiveOutPacket(PacketPlayOutScoreboardObjective handle) {
		super(handle);
	}

	public ObjectiveMode getMode() {
		return ObjectiveMode.getFromInt((int) getHandleValue("d"));
	}

	public void setMode(ObjectiveMode mode) {
		setHandleValue("d", mode.getMode());
	}


	/* TODO debug with scoreboard api util
	// TODO field change, we have one string and one IChatBaseCOmponent now

	public String getDisplayName() {
		return (String) getHandleValue("b");
	}

	public ObjectiveDisplayType getDisplayType() {
		return ObjectiveDisplayType.getHealthDisplay(((EnumScoreboardHealthDisplay) getHandleValue("c")).a());
	}

	@Nullable
	public ObjectiveMode getMode() {
		return ObjectiveMode.getFromInt((int) getHandleValue("d"));
	}

	public String getObjectiveName() {
		return (String) getHandleValue("a");
	}

	@Override
	public PacketType getType() {
		return OBJECTIVE;
	}

	public void setDisplayName(String displayName) {
		setHandleValue("b", displayName);
	}

	public void setDisplayType(ObjectiveDisplayType objectiveDisplayType) {
		setHandleValue("c", EnumScoreboardHealthDisplay.a(objectiveDisplayType.getDisplay()));
	}

	public void setMode(ObjectiveMode mode) {
		setHandleValue("d", mode.getMode());
	}

	public void setObjectiveName(String objectiveName) {
		setHandleValue("a", objectiveName);
	}

	 */

	@Override
	public PacketType getType() {
		return null;
	}

	/**
	 * The mode of the scoreboard (when the client receive the packet, what action it have to do)
	 */
	public enum ObjectiveMode {

		CREATE(0),
		REMOVE(1),
		UPDATE(2);

		private final int mode;

		ObjectiveMode(final int mode) {
			this.mode = mode;
		}

		public static ObjectiveMode getFromInt(int mode) {
			return Arrays.stream(values()).filter(objectiveMode -> objectiveMode.mode == mode).findFirst().orElse(null);
		}

		public final int getMode() {
			return this.mode;
		}
	}
}
