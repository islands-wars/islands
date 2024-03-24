package fr.islandswars.api.utils;

import java.util.function.Function;

/**
 * File <b>Preconditions</b> located on fr.islandswars.api.utils
 * Preconditions is a part of islands.
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
 * Created the 23/03/2024 at 23:14
 * @since 0.1
 */
public class Preconditions {

    public static <T> void checkState(T reference, Function<T, Boolean> check) {
        checkState(reference, check, "The given reference does not match with the function");
    }

    public static <T> void checkState(T reference, Function<T, Boolean> check, String errorMessage) {
        checkNotNull(reference);
        if (!check.apply(reference))
            throw new UnsupportedOperationException(errorMessage);
    }

    public static <T> void checkNotNull(T reference) {
        if (reference == null)
            throw new NullPointerException("Given reference is null");
    }

    public static <T> void checkValue(T reference, T to) {
        checkValue(reference, to, "The reference is not equals to the given label, " + to);
    }

    public static <T> void checkValue(T reference, T to, String errorMessage) {
        checkNotNull(reference);
        checkNotNull(to);
        if (!reference.equals(to))
            throw new UnsupportedOperationException(errorMessage);
    }
}
