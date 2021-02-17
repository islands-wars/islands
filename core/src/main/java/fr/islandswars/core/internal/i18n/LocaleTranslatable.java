package fr.islandswars.core.internal.i18n;

import fr.islandswars.api.i18n.Locale;
import fr.islandswars.api.i18n.Translatable;

/**
 * File <b>LocaleTranslatable</b> located on fr.islandswars.core.internal.i18n
 * LocaleTranslatable is a part of islands.
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
 * Created the 06/02/2021 at 20:55
 * @since 0.1
 */
public class LocaleTranslatable implements Translatable {

	private static final Locale             DEFAULT = Locale.FRENCH;
	private final        TranslatableLoader loader;

	public LocaleTranslatable() {
		this.loader = new TranslatableLoader();
	}

	@Override
	public String format(String key, Object... parameters) {
		return String.format(loader.values.get(DEFAULT).getOrDefault(key, key), parameters);
	}

	@Override
	public String format(Locale locale, String key, Object... parameters) {
		return String.format(loader.values.get(locale).getOrDefault(key, key), parameters);
	}

	public TranslatableLoader getLoader() {
		return loader;
	}
}
