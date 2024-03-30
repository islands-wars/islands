package fr.islandswars.api.log.internal;

import com.google.gson.annotations.SerializedName;
import fr.islandswars.api.server.ServerType;

import java.util.UUID;

/**
 * File <b>Server</b> located on fr.islandswars.api.log.internal
 * Server is a part of islands.
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
 * Created the 30/03/2024 at 20:28
 * @since 0.1
 */
public class Server {

    @SerializedName("server-type")
    private final ServerType serverType;

    private final UUID   id;
    private       String status;

    public Server(Status status, ServerType type, UUID id) {
        this.status = status.toString();
        this.serverType = type;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }
}
