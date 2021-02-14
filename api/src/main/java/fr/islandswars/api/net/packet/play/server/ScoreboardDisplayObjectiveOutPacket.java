package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.utils.Preconditions;
import java.util.Arrays;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardDisplayObjective;

/**
 * File <b>ScoreboardDisplayObjectiveOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * ScoreboardDisplayObjectiveOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 18:01
 * @since 0.1
 */
public class ScoreboardDisplayObjectiveOutPacket extends GamePacket<PacketPlayOutScoreboardDisplayObjective> {

	protected ScoreboardDisplayObjectiveOutPacket(PacketPlayOutScoreboardDisplayObjective handle) {
		super(handle);
	}

	public ScoreboardDisplayObjectiveOutPacket() {
		super(new PacketPlayOutScoreboardDisplayObjective());
	}

	/**
	 * @return concerned slot
	 */
	public SlotMode getSlot() {
		return SlotMode.getFromInt((int) getHandleValue("a"));
	}

	/**
	 * @param slot a new objective slot
	 */
	public void setSlot(SlotMode slot) {
		setHandleValue("a", slot.getMode());
	}

	/**
	 * @return this objective name
	 */
	public String getObjectiveName() {
		return (String) getHandleValue("b");
	}

	/**
	 * @param objectiveName a new name
	 */
	public void setObjectiveName(String objectiveName) {
		Preconditions.checkState(objectiveName, ref -> ref.length() <= 16);
		setHandleValue("b", objectiveName);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE_OUT;
	}

	public enum SlotMode {

		LIST(0),
		SIDEBAR(1),
		BELOW_NAME(2);

		private final int mode;

		SlotMode(final int mode) {
			this.mode = mode;
		}

		public static ScoreboardDisplayObjectiveOutPacket.SlotMode getFromInt(int mode) {
			return Arrays.stream(values()).filter(slotMode -> slotMode.mode == mode).findFirst().orElse(null);
		}

		public final int getMode() {
			return this.mode;
		}
	}
}
