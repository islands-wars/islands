package fr.islandswars.core.internal.mq.event;

import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.commons.service.rabbitmq.packet.PacketManager;
import fr.islandswars.commons.service.rabbitmq.packet.PacketType;
import fr.islandswars.commons.service.rabbitmq.packet.manager.PingResponsePacket;
import fr.islandswars.commons.service.rabbitmq.packet.manager.StatusResponsePacket;
import fr.islandswars.commons.service.rabbitmq.packet.server.StatusRequestPacket;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.internal.InternalServer;
import fr.islandswars.core.internal.mq.IslandsExchange;

/**
 * File <b>ExchangePacket</b> located on fr.islandswars.core.internal.mq.event
 * ExchangePacket is a part of islands.
 * <p>
 * Copyright (c) 2017 - 2024 Islands Wars.
 * <p>
 * islands is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Jangliu, {@literal <jangliu@islandswars.fr>}
 * Created the 17/06/2024 at 23:11
 * @since 0.3
 */
public class ExchangePacket {

    private final PacketManager   PACKET_MANAGER;
    private final InternalServer  server;
    private final InfraLogger     logger;
    private final IslandsExchange mq;

    public ExchangePacket(PacketManager PACKET_MANAGER, InternalServer server, IslandsExchange mq) {
        this.PACKET_MANAGER = PACKET_MANAGER;
        this.server = server;
        this.mq = mq;
        this.logger = IslandsApi.getInstance().getInfraLogger();
        addListener();
    }

    private void addListener() {
        PACKET_MANAGER.addListener(PacketType.Status.PING_REQUEST, packet -> {
            var response = new PingResponsePacket();
            response.setCode(packet.getCode());
            response.setServerId(server.getId());
            try {
                var delivery = PACKET_MANAGER.encode(response);
                mq.sendPacketToManager(delivery);
            } catch (Exception e) {
                logger.logError(e);
            }
        });
        PACKET_MANAGER.addListener(PacketType.Status.STATUS_REQUEST, packet -> {
            var response = new StatusResponsePacket();
            response.setServerId(server.getId());
            response.setOnlinePlayers(IslandsApi.getInstance().getPlayers().size());
            switch (packet.getStatus()) {
                case ENABLE -> {
                    //ready to accept new players
                    if (server.getStatus() == StatusRequestPacket.ServerStatus.READY) {
                        ((IslandsCore) IslandsApi.getInstance()).setServerStatus(StatusRequestPacket.ServerStatus.ENABLE);
                    }
                }
                case DISABLE -> {
                    if (server.getStatus() == StatusRequestPacket.ServerStatus.ENABLE) {
                        ((IslandsCore) IslandsApi.getInstance()).setServerStatus(StatusRequestPacket.ServerStatus.DISABLE);
                    }
                }
                case SHUTDOWN -> IslandsApi.getInstance().stop();
            }
            response.setStatus(packet.getStatus());

            try {
                var delivery = PACKET_MANAGER.encode(response);
                mq.sendPacketToManager(delivery);
            } catch (Exception e) {
                logger.logError(e);
            }
        });
    }
}
