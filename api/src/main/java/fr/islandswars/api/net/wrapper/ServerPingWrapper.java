package fr.islandswars.api.net.wrapper;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.ServerPing;

/**
 * File <b>ServerPingWrapper</b> located on fr.islandswars.api.net.wrapper
 * ServerPingWrapper is a part of islands.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:14
 * @since 0.1
 */
public class ServerPingWrapper extends Wrapper<ServerPing> {

	public ServerPingWrapper() {
		super(new ServerPing());
	}

	public ServerPingWrapper(ServerPing ping) {
		super(ping);
	}

	public String getDescription() {
		return getHandleValue("a").toString();
	}

	public String getFaviconPath() {
		return (String) getHandleValue("d");
	}

	public int getMaxPlayer() {
		return handle.b().a();
	}

	public ServerPing getNMS() {
		return handle;
	}

	public int getOnlinePlayer() {
		return handle.b().b();
	}

	public int getProtocolversion() {
		return handle.getServerData().getProtocolVersion();
	}

	public String getProtocolversionName() {
		return handle.getServerData().a();
	}

	public GameProfile[] getSamples() {
		return handle.b().c();
	}

	public void setDescription(IChatBaseComponent component) {
		handle.setMOTD(component);
	}

	public void setFaviconPath(String faviconPath) {
		handle.setFavicon(faviconPath);
	}

	public void setMaxPlayer(int maxPlayer) {
		setHandleValue("a", handle.b(), maxPlayer);
	}

	public void setOnlinePlayer(int onlinePlayer) {
		setHandleValue("b", handle.b(), onlinePlayer);
	}

	public void setProtocolVersion(int protocolVersion) {
		setHandleValue("b", handle.getServerData(), protocolVersion);
	}

	public void setProtocolVersionName(String protocolVersionName) {
		setHandleValue("a", handle.getServerData(), protocolVersionName);
	}
}

