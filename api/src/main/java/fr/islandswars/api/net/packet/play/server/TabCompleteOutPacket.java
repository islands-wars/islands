package fr.islandswars.api.net.packet.play.server;

import com.mojang.brigadier.suggestion.Suggestions;
import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import fr.islandswars.api.net.wrapper.ServerPingWrapper;
import fr.islandswars.api.net.wrapper.SuggestionsWrapper;
import net.minecraft.server.v1_16_R3.PacketPlayOutTabComplete;
import net.minecraft.server.v1_16_R3.ServerPing;

/**
 * File <b>TabCompleteOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * TabCompleteOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 18:28
 * @since 0.1
 * <p>
 * The server responds with a list of auto-completions of the last word sent to it. In the case of regular chat, this is a player username. Command names and parameters are also supported. The client sorts these alphabetically before listing them.
 */
public class TabCompleteOutPacket extends GamePacket<PacketPlayOutTabComplete> {

	private SuggestionsWrapper wrapper;

	protected TabCompleteOutPacket(PacketPlayOutTabComplete handle) {
		super(handle);
	}

	/**
	 * @return suggestion id
	 */
	public int getID() {
		return (int) getHandleValue("a");
	}

	/**
	 * @param id new suggestion ID
	 */
	public void setID(int id) {
		setHandleValue("a", id);
	}

	/**
	 * @return a wrapper for Suggestions brigadier class
	 */
	public SuggestionsWrapper getWrapper() {
		if (wrapper == null)
			this.wrapper = new SuggestionsWrapper((Suggestions) getHandleValue("b"));
		return wrapper;
	}

	/**
	 * @param wrapper a wrapper for this response
	 */
	public void setSuggestions(SuggestionsWrapper wrapper) {
		setHandleValue("b", wrapper.getNMS());
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.TAB_COMPLETE_OUT;
	}
}
