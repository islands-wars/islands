package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.utils.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;

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
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Chassignol (Vinetos), {@literal <vinetos@islandswars.fr>}
 * Created the 06/02/2021 at 18:19
 * @since 0.1
 * <p>
 * Debug with https://wiki.vg/Protocol#Teams
 */
public class ScoreboardTeamOutPacket extends GamePacket<PacketPlayOutScoreboardTeam> {

	private int option = 0;

	protected ScoreboardTeamOutPacket(PacketPlayOutScoreboardTeam handle) {
		super(handle);
	}

	public ScoreboardTeamOutPacket() {
		this(new PacketPlayOutScoreboardTeam());
	}

	public String getTeamName() {
		return (String) getHandleValue("a");
	}

	public void setTeamName(String name) {
		Preconditions.checkState(name, ref -> ref.length() <= 16);

		setHandleValue("a", name);
	}

	public IChatBaseComponent getDisplayName() {
		return (IChatBaseComponent) getHandleValue("b");
	}

	public void setDisplayName(String displayName) {
		Preconditions.checkState(displayName, ref -> ChatColor.stripColor(ref).length() <= 128);

		var result = displayName.isEmpty() ? ChatComponentText.d : CraftChatMessage.fromStringOrNull(displayName);
		setHandleValue("b", result);
	}

	public IChatBaseComponent getPrefix() {
		return (IChatBaseComponent) getHandleValue("c");
	}

	public void setPrefix(String prefix) {
		Preconditions.checkState(prefix, ref -> ChatColor.stripColor(ref).length() <= 64);

		var result = prefix.isEmpty() || prefix.equals(" ") ? ChatComponentText.d : CraftChatMessage.fromStringOrNull(prefix);
		setHandleValue("c", result);
	}

	public IChatBaseComponent getSuffix() {
		return (IChatBaseComponent) getHandleValue("d");
	}

	public void setSuffix(String suffix) {
		Preconditions.checkState(suffix, ref -> ChatColor.stripColor(ref).length() <= 64);

		var result = suffix.isEmpty() || suffix.equals(" ") ? ChatComponentText.d : CraftChatMessage.fromStringOrNull(suffix);
		setHandleValue("d", result);
	}

	public ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility() {
		var nametag = (String) getHandleValue("e");
		return ScoreboardTeamBase.EnumNameTagVisibility.a(nametag);
	}

	public void setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility nameTagVisibility) {
		setHandleValue("e", nameTagVisibility.e);
	}

	public ScoreboardTeamBase.EnumTeamPush getCollisionRule() {
		var collision = (String) getHandleValue("f");
		return ScoreboardTeamBase.EnumTeamPush.a(collision);
	}

	public void setCollisionRule(ScoreboardTeamBase.EnumTeamPush collisionRule) {
		setHandleValue("f", collisionRule.e);
	}

	public EnumChatFormat getTeamColor() {
		return (EnumChatFormat) getHandleValue("g");
	}

	public void setTeamColor(EnumChatFormat color) {
		setHandleValue("g", color);
	}

	@SuppressWarnings("unchecked")
	public Collection<String> getPlayers() {
		return (Collection<String>) getHandleValue("h");
	}

	public void setPlayers(Collection<String> players) {
		setHandleValue("h", players);
	}

	public EnumTeamPush getMode() {
		var mode = (int) getHandleValue("i");
		return EnumTeamPush.fromInt(mode);
	}

	public void setMode(EnumTeamPush mode) {
		setHandleValue("i", mode.getMode());
	}

	public boolean hasAllowedFriendlyFire() {
		return ((int) getHandleValue("j") & 0b01) != 0;
	}

	public void allowFriendlyFire() {
		option |= 0b01;
		setHandleValue("j", (int) getHandleValue("j") | 0b01);
	}

	public boolean canSeeFriendlyInvisible() {
		return ((int) getHandleValue("j") & 0b10) != 0;
	}

	public void setCanSeeFriendlyInvisible() {
		option |= 0b10;
		setHandleValue("j", option);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.SCOREBOARD_TEAM_OUT;
	}

	public enum EnumTeamPush {
		CREATE(0),
		REMOVE(1),
		UPDATE(2),
		ADD_PLAYERS(3),
		REMOVE_PLAYERS(4);

		private final int mode;

		EnumTeamPush(int mode) {
			this.mode = mode;
		}

		@Nullable
		public static EnumTeamPush fromInt(int mode) {
			return Arrays.stream(values()).filter(team -> team.mode == mode).findFirst().orElse(null);
		}

		public int getMode() {
			return mode;
		}
	}
}
