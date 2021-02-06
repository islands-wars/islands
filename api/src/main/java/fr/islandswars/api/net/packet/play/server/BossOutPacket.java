package fr.islandswars.api.net.packet.play.server;

import fr.islandswars.api.net.GamePacket;
import fr.islandswars.api.net.PacketType;
import java.util.UUID;
import net.minecraft.server.v1_16_R3.BossBattle;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutBoss;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;

/**
 * File <b>BossOutPacket</b> located on fr.islandswars.api.net.packet.play.server
 * BossOutPacket is a part of islands.
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
 * Created the 06/02/2021 at 17:41
 * @since 0.1
 */
public class BossOutPacket extends GamePacket<PacketPlayOutBoss> {

	protected BossOutPacket(PacketPlayOutBoss handle) {
		super(handle);
	}

	/**
	 * @return boss bar action
	 */
	public PacketPlayOutBoss.Action getAction() {
		return (PacketPlayOutBoss.Action) getHandleValue("b");
	}

	/**
	 * @param action boss bar action
	 */
	public void setAction(PacketPlayOutBoss.Action action) {
		setHandleValue("b", action);
	}

	/**
	 * @return bar color
	 */
	public BossBattle.BarColor getBarColor() {
		return (BossBattle.BarColor) getHandleValue("e");
	}

	/**
	 * @param color of the bar
	 */
	public void setBarColor(BossBattle.BarColor color) {
		setHandleValue("e", color);
	}

	/**
	 * @return bar health
	 */
	public float getBarHealth() {
		return (float) getHandleValue("d");
	}

	/**
	 * @param health of the bar
	 */
	public void setHealth(float health) {
		setHandleValue("d", health);
	}

	/**
	 * @return bar id
	 */
	public UUID getBarId() {
		return (UUID) getHandleValue("a");
	}

	/**
	 * @param id bar id
	 */
	public void setBarId(UUID id) {
		setHandleValue("a", id);
	}

	/**
	 * @return bar style
	 */
	public BossBattle.BarStyle getBarStyle() {
		return (BossBattle.BarStyle) getHandleValue("f");
	}

	/**
	 * @param style of the bar
	 */
	public void setBarStyle(BossBattle.BarStyle style) {
		setHandleValue("f", style);
	}

	/**
	 * @return bar title
	 */
	public IChatBaseComponent getTitle() {
		return (IChatBaseComponent) getHandleValue("c");
	}

	/**
	 * @param title of the bar
	 */
	public void setTitle(String title) {
		setHandleValue("c", CraftChatMessage.fromString(title, true)[0]);
	}

	/**
	 * @return if the client will darken the sky
	 */
	public boolean isDarkenSky() {
		return (boolean) getHandleValue("g");
	}

	/**
	 * @param darkenSky effect
	 */
	public void setDarkenSky(boolean darkenSky) {
		setHandleValue("g", darkenSky);
	}

	/**
	 * @return if the client will active frog effect
	 */
	public boolean isFogActive() {
		return (boolean) getHandleValue("i");
	}

	/**
	 * @param fog effect
	 */
	public void setFogActive(boolean fog) {
		setHandleValue("i", fog);
	}

	/**
	 * @return playing music effect
	 */
	public boolean isPlayingMusic() {
		return (boolean) getHandleValue("h");
	}

	/**
	 * @param playMusic music activation
	 */
	public void setPlayMusic(boolean playMusic) {
		setHandleValue("h", playMusic);
	}

	@Override
	public PacketType getType() {
		return PacketType.Play.Server.BOSS_OUT;
	}
}