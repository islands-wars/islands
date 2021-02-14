package fr.islandswars.api.i18n;

import fr.islandswars.api.player.IslandsPlayer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * File <b>TranslationParameters</b> located on fr.islandswars.api.i18n
 * TranslationParameters is a part of islands.
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
 * Created the 09/02/2021 at 11:19
 * @since 0.1
 */
public class TranslationParameters {

	private static final Object[]                                    EMPTY_OBJECT = new Object[0];
	public static final  TranslationParameters                       EMPTY        = new TranslationParameters((p) -> () -> EMPTY_OBJECT);
	private final        Function<IslandsPlayer, Supplier<Object[]>> translate;

	public TranslationParameters(Function<IslandsPlayer, Supplier<Object[]>> translation) {
		this.translate = translation;
	}

	public Supplier<Object[]> getTranslation(IslandsPlayer player) {
		return translate.apply(player);
	}
}
