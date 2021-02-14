package fr.islandswars.core.bukkit.bossbar;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.bossbar.Bar;
import fr.islandswars.api.bossbar.BarProperties;
import fr.islandswars.api.i18n.TranslationParameters;
import fr.islandswars.api.net.packet.play.server.BossOutPacket;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.utils.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.server.v1_16_R3.BossBattle;
import net.minecraft.server.v1_16_R3.MathHelper;
import net.minecraft.server.v1_16_R3.PacketPlayOutBoss;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftChatMessage;

/**
 * File <b>InternalBar</b> located on fr.islandswars.core.bukkit.bossbar
 * InternalBar is a part of islands.
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
 * Created the 08/02/2021 at 18:14
 * @since 0.1
 */
public class InternalBar extends BossBattle implements Bar {

	private List<IslandsPlayer> viewers;
	private boolean             active;
	TranslationParameters parameters;
	BarProperties         properties;
	int                   lastTick, lastUpdateTitle, repeatCount;

	InternalBar(String titleKey, BarColor barColor, BarStyle barStyle) {
		super(MathHelper.a(), CraftChatMessage.fromString(titleKey, true)[0], barColor, barStyle);
		this.viewers = new ArrayList<>();
		this.active = true;
		this.lastTick = 0;
		this.repeatCount = -1;
		this.lastUpdateTitle = 0;
		this.parameters = TranslationParameters.EMPTY;
	}

	public void addPlayer(IslandsPlayer player) {
		Preconditions.checkNotNull(player);

		viewers.add(player);
		if (active) {
			var packet = getPacket(PacketPlayOutBoss.Action.ADD);
			var text   = title.getSiblings().get(0).getText();
			var tit    = player.getPlayerLocale().format(text, parameters.getTranslation(player).get());
			packet.setTitle(tit);
			packet.setDarkenSky(isDarkenSky());
			packet.setPlayMusic(isPlayMusic());
			packet.setFogActive(isCreateFog());
			packet.sendToPlayer(player.getCraftPlayer());
			if (viewers.size() == 1)
				lazyRegister();
		}
	}

	@Override
	public Stream<IslandsPlayer> getViewers() {
		return viewers.stream();
	}

	@Override
	public void provideProperties(BarProperties properties, boolean erase) {
		Preconditions.checkNotNull(properties);

		if (erase)
			this.properties = properties;
		if (this.properties == null)
			this.properties = properties;
		if (properties.useReverse())
			setProgress(1);
	}

	@Override
	public void setActive(boolean active) {
		if (this.active != active) {
			this.active = active;
			update(active ? PacketPlayOutBoss.Action.ADD : PacketPlayOutBoss.Action.REMOVE);
		}
	}

	@Override
	public void setCreateFog(boolean createFog) {
		super.c(createFog);
		update(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
	}

	@Override
	public void setDarkenSky(boolean darkenSky) {
		super.a(darkenSky);
		update(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
	}

	@Override
	public void setPlayMusic(boolean playMusic) {
		super.b(playMusic);
		update(PacketPlayOutBoss.Action.UPDATE_PROPERTIES);
	}

	public void forceUpdate(IslandsPlayer player) {
		var packet = getPacket(PacketPlayOutBoss.Action.UPDATE_NAME);
		packet.setTitle(player.getPlayerLocale().format(title.getSiblings().get(0).getText(), parameters.getTranslation(player).get()));
		packet.sendToPlayer(player.getCraftPlayer());
	}

	@Override
	public void setProgress(float progress) {
		Preconditions.checkState(progress, ref -> ref >= 0 && ref <= 1);

		if (progress != super.b) {
			super.a(progress);
			update(PacketPlayOutBoss.Action.UPDATE_PCT);
		}
	}

	@Override
	public void setTranslationParameters(TranslationParameters parameters) {
		this.parameters = parameters;
	}

	public void removePlayer(IslandsPlayer player) {
		Preconditions.checkNotNull(player);

		if (viewers.remove(player) && active)
			getPacket(PacketPlayOutBoss.Action.REMOVE).sendToPlayer(player.getCraftPlayer());
		if (viewers.size() == 0)
			lazyUnregister();
	}

	public void update(PacketPlayOutBoss.Action action) {
		Preconditions.checkNotNull(action);

		if (active) {
			if (action == PacketPlayOutBoss.Action.UPDATE_NAME) {
				getViewers().forEach(player -> {
					var packet = getPacket(action);
					packet.setTitle(player.getPlayerLocale().format(title.getSiblings().get(0).getText(), parameters.getTranslation(player).get()));
					packet.sendToPlayer(player.getCraftPlayer());
				});
			} else {
				sendUpdate(action);
			}
		}
	}

	private BossOutPacket getPacket(PacketPlayOutBoss.Action action) {
		return new BossOutPacket(action, this);
	}

	private void lazyRegister() {
		((BukkitBarManager) IslandsApi.getInstance().getBarManager()).scheduleBar(this);
	}

	private void lazyUnregister() {
		((BukkitBarManager) IslandsApi.getInstance().getBarManager()).removeBar(this);
	}

	private void sendUpdate(PacketPlayOutBoss.Action action) {
		var packet = getPacket(action);
		getViewers().forEach(player -> packet.sendToPlayer(player.getCraftPlayer()));
	}
}


