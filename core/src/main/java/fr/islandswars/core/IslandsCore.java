package fr.islandswars.core;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.i18n.I18nLoader;
import fr.islandswars.api.i18n.Locale;
import fr.islandswars.api.i18n.Translatable;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.api.log.internal.Server;
import fr.islandswars.api.log.internal.ServerLog;
import fr.islandswars.api.log.internal.Status;
import fr.islandswars.api.net.ProtocolManager;
import fr.islandswars.api.player.IslandsPlayer;
import fr.islandswars.api.server.ServerType;
import fr.islandswars.core.bukkit.net.PacketHandlerManager;
import fr.islandswars.core.bukkit.net.PacketInterceptor;
import fr.islandswars.core.internal.i18n.LocaleTranslatable;
import fr.islandswars.core.internal.listener.PlayerListener;
import fr.islandswars.core.internal.log.InternalLogger;
import fr.islandswars.core.player.InternalPlayer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.entity.Player;

/**
 * File <b>IslandsCore</b> located on fr.islandswars.core
 * IslandsCore is a part of islands.
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
 * Created the 03/02/2021 at 19:10
 * @since 0.1
 */
public class IslandsCore extends IslandsApi {

	private final PacketHandlerManager                packetManager;
	private final InternalLogger                      logger;
	private final CopyOnWriteArrayList<IslandsPlayer> players;
	private final LocaleTranslatable                  translatable;

	public IslandsCore() {
		this.logger = new InternalLogger();
		this.players = new CopyOnWriteArrayList<>();
		this.packetManager = new PacketHandlerManager();
		this.translatable = new LocaleTranslatable();
	}

	@Override
	public Optional<IslandsPlayer> getPlayer(UUID playerId) {
		return players.stream().filter(p -> p.getCraftPlayer().getUniqueId().equals(playerId)).findFirst();
	}

	@Override
	public List<? extends IslandsPlayer> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	@Override
	public List<IslandsPlayer> getPlayers(Predicate<IslandsPlayer> predicate) {
		return Collections.unmodifiableList(players.stream().filter(predicate).collect(Collectors.toList()));
	}

	@Override
	public ProtocolManager getProtocolManager() {
		return packetManager;
	}

	@Override
	public InfraLogger getInfraLogger() {
		return logger;
	}

	@Override
	public I18nLoader getI18nLoader() {
		return translatable.getLoader();
	}

	@Override
	public Translatable getTranslatable() {
		return translatable;
	}

	@Override
	public void onLoad() {
		translatable.getLoader().registerCustomProperties(this);
		getInfraLogger().createCustomLog(ServerLog.class, Level.INFO, "Loading server...").setServer(new Server(Status.LOAD, ServerType.HUB)).log();
	}

	@Override
	public void onDisable() {
		getInfraLogger().createCustomLog(ServerLog.class, Level.INFO, "Disabling server...").setServer(new Server(Status.DISABLE, ServerType.HUB)).log();
	}

	@Override
	public void onEnable() {
		getInfraLogger().createCustomLog(ServerLog.class, Level.INFO, "Enable server in %s ms.").setServer(new Server(Status.ENABLE, ServerType.HUB)).log();
		PacketInterceptor.inject();
		try {
			new PlayerListener(this);
		} catch (Exception e) {
			getInfraLogger().logError(e);
		}
	}

	public void addPlayer(Player p) {
		players.add(new InternalPlayer(p));
	}

	public void removePlayer(IslandsPlayer player) {
		player.disconnect();
		players.remove(player);
	}
}
