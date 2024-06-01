package fr.islandswars.api.log;

import fr.islandswars.api.lang.IslandsApiError;
import fr.islandswars.api.log.internal.DefaultLog;
import fr.islandswars.api.log.internal.ErrorLog;
import fr.islandswars.api.utils.ReflectionUtil;
import org.apache.logging.log4j.Level;


/**
 * File <b>InfraLogger</b> located on fr.islandswars.api.log
 * InfraLogger is a part of islands.
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
 * Created the 26/03/2024 at 19:28
 * @since 0.1
 */
public interface InfraLogger {

    default void logInfo(String message) {
        log(Level.INFO, message);
    }

    default void log(Level level, String msg) {
        if (level.equals(Level.ERROR))
            logError(new IslandsApiError(msg));
        else
            new DefaultLog(level, msg).log();
    }

    default void logDebug(String message) {
        log(Level.DEBUG, message);
    }

    default <T extends Log> T createCustomLog(Class<T> clazz, Level level, String message) {
        return ReflectionUtil.getConstructorAccessor(clazz, Level.class, String.class).newInstance(level, message);
    }

    void sysout(Log object);

    default void logError(Exception e) {
        new ErrorLog(Level.ERROR, e.getMessage() == null ? "Error" : e.getMessage()).supplyStacktrace(e.fillInStackTrace()).log();
    }

}
