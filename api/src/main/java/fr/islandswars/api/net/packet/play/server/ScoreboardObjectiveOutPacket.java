package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.utils.Preconditions;
import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IScoreboardCriteria;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardObjective;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;

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

	public ScoreboardObjectiveOutPacket() {
		super(new PacketPlayOutScoreboardObjective());
	}

	public String getObjectiveName() {
		return (String) getHandleValue("a");
	}

	public void setObjectiveName(String objectiveName) {
		Preconditions.checkState(objectiveName, ref -> ref.length() <= 16);

		setHandleValue("a", objectiveName);
	}

	public IChatBaseComponent getDisplayName() {
		return (IChatBaseComponent) getHandleValue("b");
	}

	public void setDisplayName(String displayName) {
		Preconditions.checkState(displayName, ref -> ref.length() <= 128);

		setHandleValue("b", CraftChatMessage.fromStringOrNull(displayName));
	}

	@Nullable
	public ObjectiveMode getMode() {
		return ObjectiveMode.getFromInt((int) getHandleValue("d"));
	}

	public void setMode(ObjectiveMode mode) {
		setHandleValue("d", mode.getMode());
	}

	public IScoreboardCriteria.EnumScoreboardHealthDisplay getDisplayType() {
		return (IScoreboardCriteria.EnumScoreboardHealthDisplay) getHandleValue("c");
	}

	public void setDisplayType(IScoreboardCriteria.EnumScoreboardHealthDisplay objectiveDisplayType) {
		setHandleValue("c", objectiveDisplayType);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.SCOREBOARD_OBJECTIVE_OUT;
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
