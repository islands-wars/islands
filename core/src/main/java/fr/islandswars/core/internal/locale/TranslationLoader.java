package fr.islandswars.core.internal.locale;

import fr.islandswars.api.locale.Translatable;
import fr.islandswars.api.utils.Preconditions;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.intellij.lang.annotations.Subst;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * File <b>TranslationLoader</b> located on fr.islandswars.core.internal.locale
 * TranslationLoader is a part of islands.
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
 * Created the 24/03/2024 at 00:17
 * @since 0.1
 */
public class TranslationLoader implements Translatable {

    @Override
    public void load(@Subst("locale.core") String bundleName) {
        Preconditions.checkNotNull(bundleName);

        TranslationRegistry registry = TranslationRegistry.create(Key.key(bundleName));
        ResourceBundle      bundleUS = ResourceBundle.getBundle(bundleName, Locale.US, UTF8ResourceBundleControl.get());
        ResourceBundle      bundleFR = ResourceBundle.getBundle(bundleName, Locale.FRANCE, UTF8ResourceBundleControl.get());
        registry.registerAll(Locale.US, bundleUS, true);
        registry.registerAll(Locale.FRANCE, bundleFR, true);
        GlobalTranslator.translator().addSource(registry);
    }

    @Override
    public Component render(String key, ComponentLike... parameters) {
        return Component.translatable(key, parameters);
    }

    @Override
    public Component render(Locale locale, String key, ComponentLike... parameters) {
        return GlobalTranslator.render(render(key, parameters), locale);
    }

    @Override
    public Component renderDefault(String key, ComponentLike... parameters) {
        return GlobalTranslator.render(Component.translatable(key, parameters), Locale.US);
    }

}
