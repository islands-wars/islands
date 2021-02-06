package fr.islandswars.api.i18n;

/**
 * File <b>Translatable</b> located on fr.islandswars.api.i18n
 * Translatable is a part of islands.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Valentin Burgaud (Xharos), {@literal <xharos@islandswars.fr>}
 * Created the 06/02/2021 at 20:52
 * @since 0.1
 */
public interface Translatable {

	/**
	 * Get a String according to the default language (French) and format with the given parameters
	 *
	 * @param key        the property key
	 * @param parameters the properties label to format with
	 * @return a {@link String#format(String, Object...)}, or else the key
	 * @see #format(Locale, String, Object...)
	 */
	String format(String key, Object... parameters);

	/**
	 * Get a String according to the given language and format with the given parameters
	 *
	 * @param locale     a language to use
	 * @param key        the property key
	 * @param parameters the properties label to format with
	 * @return a {@link String#format(String, Object...)}, or else the key
	 */
	String format(Locale locale, String key, Object... parameters);
}
