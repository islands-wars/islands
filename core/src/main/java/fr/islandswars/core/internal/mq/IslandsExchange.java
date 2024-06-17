package fr.islandswars.core.internal.mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import fr.islandswars.api.IslandsApi;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.commons.service.rabbitmq.RabbitMQConnection;
import fr.islandswars.commons.service.rabbitmq.packet.PacketManager;
import fr.islandswars.commons.service.rabbitmq.packet.PacketType;
import fr.islandswars.core.IslandsCore;
import fr.islandswars.core.internal.InternalServer;
import fr.islandswars.core.internal.mq.event.ExchangePacket;

import java.io.IOException;

/**
 * File <b>IslandsExchange</b> located on fr.islandswars.core.internal.mq
 * IslandsExchange is a part of islands.
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
 * Created the 02/06/2024 at 14:24
 * @since 0.1
 */
public class IslandsExchange {

    private final String              EXCHANGE_NAME = "islands";
    private final String              MANAGER_NAME  = "server";
    private final BuiltinExchangeType EXCHANGE_TYPE = BuiltinExchangeType.TOPIC;
    private final PacketManager       PACKET_MANAGER;
    private final RabbitMQConnection  connection;
    private final InternalServer      server;
    private final Channel             channel;
    private final InfraLogger         logger;

    public IslandsExchange(RabbitMQConnection connection, IslandsCore core) {
        this.PACKET_MANAGER = new PacketManager(PacketType.Bound.SERVER, 1024, true);
        this.logger = IslandsApi.getInstance().getInfraLogger();
        this.connection = connection;
        this.server = core.getInternalServer();
        this.channel = connection.getConnection();
        try {
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
        } catch (Exception e) {
            logger.logError(e);
        }
        new ExchangePacket(PACKET_MANAGER, server, this);
        initConnection();
    }

    public void sendPacketToManager(byte[] packet) {
        sendPacket(MANAGER_NAME, packet);
    }

    public void sendPacket(String routingKey, byte[] packet) {
        try {
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, packet);
        } catch (Exception e) {
            logger.logError(e);
        }
    }

    private void initConnection() {
        var channel = connection.getConnection();
        try {
            var queue = "server." + server.getServerType() + "." + server.getId();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.queueDeclare(queue, false, true, false, null);
            channel.queueBind(queue, EXCHANGE_NAME, queue); //listen to specific message
            channel.queueBind(queue, EXCHANGE_NAME, MANAGER_NAME + "." + server.getServerType() + ".all"); //listen to all same server's type message
            channel.queueBind(queue, EXCHANGE_NAME, MANAGER_NAME + ".all"); //listen to all servers message

            channel.basicConsume(queue, true, (tag, delivery) -> {
                try {
                    PACKET_MANAGER.decode(delivery.getBody());
                } catch (Exception e) {
                    logger.logError(e);
                }
                //TODO remove
                IslandsApi.getInstance().getInfraLogger().logInfo(delivery.getEnvelope().toString());
            }, consumerTag -> {
            });
        } catch (IOException e) {
            logger.logError(e);
        }
    }
}
