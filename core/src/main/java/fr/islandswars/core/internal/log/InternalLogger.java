package fr.islandswars.core.internal.log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.islandswars.api.log.InfraLogger;
import fr.islandswars.api.log.Log;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.net.URISyntaxException;

/**
 * File <b>InternalLogger</b> located on fr.islandswars.core.internal.log
 * InternalLogger is a part of islands.
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
 * Created the 26/03/2024 at 19:34
 * @since 0.1
 */
public class InternalLogger implements InfraLogger {

    private final Logger  rootLogger;
    private final Gson    gson;
    private final boolean debug;

    public InternalLogger() {
        this.gson = new GsonBuilder().registerTypeAdapter(Level.class, new Log4jLevelSerializer()).registerTypeAdapter(StackTraceElement.class, new StackTraceElementTypeAdapter()).create();
        try {
            Configurator.reconfigure(InternalLogger.class.getClassLoader().getResource("log4j2-islands.xml").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.debug = Boolean.parseBoolean(System.getenv("DEBUG"));
        this.rootLogger = (Logger) LogManager.getRootLogger();
    }

    @Override
    public void sysout(Log object) {
        if (object.getLevel() == Level.DEBUG) {
            if (debug)
                rootLogger.log(object.getLevel(), gson.toJson(object));
        } else
            rootLogger.log(object.getLevel(), gson.toJson(object));
    }
}
