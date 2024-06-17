package fr.islandswars.core.internal;

import com.google.gson.annotations.SerializedName;
import fr.islandswars.api.server.ServerType;
import fr.islandswars.commons.service.rabbitmq.packet.server.StatusRequestPacket;

import java.util.UUID;

/**
 * File <b>InternalServer</b> located on fr.islandswars.core.internal
 * InternalServer is a part of islands.
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
 * Created the 16/06/2024 at 23:03
 * @since 0.1
 */
public class InternalServer {

    @SerializedName("server-type")
    private final ServerType serverType;

    private final UUID                             id;
    private       StatusRequestPacket.ServerStatus status;

    public InternalServer(StatusRequestPacket.ServerStatus status, ServerType type, UUID id) {
        this.status = status;
        this.serverType = type;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public StatusRequestPacket.ServerStatus getStatus() {
        return status;
    }

    public void setStatus(StatusRequestPacket.ServerStatus status) {
        this.status = status;
    }

    public ServerType getServerType() {
        return serverType;
    }
}
