package fr.islandswars.api.log.internal;

import fr.islandswars.api.log.Log;
import fr.islandswars.api.utils.Preconditions;
import org.apache.logging.log4j.Level;

/**
 * File <b>ServerLog</b> located on fr.islandswars.api.log.internal
 * ServerLog is a part of islands.
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
 * Created the 30/03/2024 at 20:16
 * @since 0.1
 */
public class ServerLog extends Log {

    private Server server;

    public ServerLog(Level level, String msg) {
        super(level, msg);
    }

    @Override
    public void checkValue() {
        Preconditions.checkNotNull(server);
    }

    public ServerLog setServer(Server server) {
        this.server = server;
        return this;
    }
}
