package fr.islandswars.core.internal.i18n;

import com.google.common.base.Preconditions;
import fr.islandswars.api.player.i18n.I18nLoader;
import fr.islandswars.api.player.i18n.Locale;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarFile;

/**
 * File <b>TranslatableLoader</b> located on fr.islandswars.core.internal.i18n
 * TranslatableLoader is a part of islands.
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
 * Created the 23/03/2024 at 22:39
 * @since 0.1
 */
public class TranslatableLoader implements I18nLoader {

    final Map<Locale, Map<String, String>> values;

    public TranslatableLoader() {
        this.values = new HashMap<>();
        for (var locale : Locale.values())
            values.put(locale, new HashMap<String, String>());
    }

    @Override
    public void registerCustomProperties(Plugin plugin) {
        Preconditions.checkNotNull(plugin);
        var i18nFolder = new File(plugin.getDataFolder(), "i18n/");

        if (!i18nFolder.exists())
            i18nFolder.mkdirs();

        try {
            URI pluginURI = plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
            try (JarFile jarPlugin = new JarFile(pluginURI.getPath())) {
                for (var jarEntry : Collections.list(jarPlugin.entries())) {
                    if (!jarEntry.isDirectory() && jarEntry.getName().startsWith("i18n/"))
                        plugin.saveResource(jarEntry.getName(), true);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Arrays.stream(i18nFolder.listFiles()).filter(file -> file.getName().endsWith(".lang")).forEach(this::loadFile);
    }

    @Override
    public void registerDynamicProperty(Locale locale, String key, String value) {
        values.computeIfPresent(locale, (k, v) -> {
            v.putIfAbsent(key, value);
            return v;
        });
    }

    private Locale getLocale(File lang) {
        var name = lang.getName();
        for (var locale : Locale.values()) {
            if (name.contains(locale.getI18nName()))
                return locale;
        }
        return null;
    }

    private void loadFile(File langFile) {
        var loc = getLocale(langFile);
        if (loc != null) {
            var               properties = new Properties();
            FileInputStream   stream = null;
            InputStreamReader reader = null;
            try {
                stream = new FileInputStream(langFile);
                reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                properties.load(reader);
                properties.forEach((langKey, langValue) -> values.computeIfPresent(loc, (locale, map) -> {
                    map.putIfAbsent(langKey.toString(), langValue.toString());
                    return map;
                }));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (stream != null)
                        stream.close();
                    if (reader != null)
                        reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
