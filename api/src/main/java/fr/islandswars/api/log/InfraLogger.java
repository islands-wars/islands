package fr.islandswars.api.log;

import fr.islandswars.api.log.internal.DefaultLog;
import fr.islandswars.api.log.internal.ErrorLog;
import fr.islandswars.api.utils.NMSReflectionUtil;
import java.util.logging.Level;

/**
 * File <b>InfraLogger</b> located on fr.islandswars.api.log
 * InfraLogger is a part of islands.
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
 * along with this program. If not, see <a href="http://www.gnu.org/licenses/">GNU license</a>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 04/02/2021 at 17:27
 * @since 0.1
 */
public interface InfraLogger {

	/**
	 * Instanciate and retrieve a custom log, or else throw Exception if reflection failed
	 *
	 * @param clazz   a custom class to instanciate
	 * @param level   a log level
	 * @param message a log message
	 * @param <T>     log type
	 * @return a custom {@link Log} implementation
	 * @see fr.islandswars.api.utils.NMSReflectionUtil#getConstructorAccessor(String, Class[])
	 */
	default <T extends Log> T createCustomLog(Class<T> clazz, Level level, String message) {
		return NMSReflectionUtil.getConstructorAccessor(clazz, Level.class, String.class).newInstance(level, message);
	}

	/**
	 * Create a default log with INFO level and a custom message
	 *
	 * @param message a message to log
	 * @see #createDefaultLog(Level, String)
	 */
	default void createDefaultLog(String message) {
		createDefaultLog(Level.INFO, message);
	}

	/**
	 * Helper method to create and directly log a simple log
	 *
	 * @param level a log level
	 * @param msg   a log message
	 */
	default void createDefaultLog(Level level, String msg) {
		new DefaultLog(level, msg).log();
	}

	/**
	 * Send this message to default docker sysout
	 *
	 * @param object an object to log, it will be serialize to json and sent to graylog through GELF
	 */
	void log(Log object);

	/**
	 * Log and format an error
	 *
	 * @param e an error to log
	 */
	default void logError(Exception e) {
		logError(e, e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage());
	}

	/**
	 * Log an error with a custom message
	 *
	 * @param e       an error to log
	 * @param message a specific message
	 */
	default void logError(Exception e, String message) {
		createCustomLog(ErrorLog.class, Level.WARNING, message).supplyStacktrace(e).log();
	}

}

