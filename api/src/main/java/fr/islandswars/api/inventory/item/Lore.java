package fr.islandswars.api.inventory.item;

import fr.islandswars.api.i18n.TranslationParameters;

/**
 * File <b>Lore</b> located on fr.islandswars.api.inventory.item
 * Lore is a part of islands.
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
 * Created the 15/02/2021 at 17:22
 * @since 0.1
 */
public class Lore {

	protected String                key;
	protected TranslationParameters parameters;

	public Lore(String key, TranslationParameters parameters) {
		this.key = key;
		this.parameters = parameters;
	}
}
