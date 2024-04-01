package fr.islandswars.api.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

/**
 * File <b>GradientComponent</b> located on fr.islandswars.api.utils
 * GradientComponent is a part of islands.
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
 * Created the 01/04/2024 at 12:51
 * @since 0.1
 */
public class GradientComponent {

    public static List<Component> highlightCharacters(String text, TextColor startColor, TextColor endColor) {
        List<Component> components = new ArrayList<>();
        int             length     = text.length();
        components.add(gradient(text, startColor, endColor, false, -1)); // Add initial gradient
        for (int j = 0; j < length; j++) {
            components.add(gradient(text, startColor, endColor, true, j)); // Add gradient with character highlighted
        }
        return components;
    }

    public static Component gradient(String text, TextColor startColor, TextColor endColor, boolean lighten, int highlightIndex) {
        int length  = text.length();
        var builder = Component.text();
        for (int i = 0; i < length; i++) {
            float     ratio = (float) i / (float) (length - 1);
            TextColor color = interpolateColor(startColor, endColor, ratio, lighten && i == highlightIndex);
            builder.append(Component.text(text.charAt(i)).color(color));
        }
        return builder.build();
    }

    private static TextColor interpolateColor(TextColor startColor, TextColor endColor, float ratio, boolean lighten) {
        int startRed   = startColor.red();
        int startGreen = startColor.green();
        int startBlue  = startColor.blue();

        int endRed   = endColor.red();
        int endGreen = endColor.green();
        int endBlue  = endColor.blue();

        int interpolatedRed   = (int) (startRed + ratio * (endRed - startRed));
        int interpolatedGreen = (int) (startGreen + ratio * (endGreen - startGreen));
        int interpolatedBlue  = (int) (startBlue + ratio * (endBlue - startBlue));

        if (lighten) {
            interpolatedRed = Math.min((int) (interpolatedRed * 1.7), 255);
            interpolatedGreen = Math.min((int) (interpolatedGreen * 1.7), 255);
            interpolatedBlue = Math.min((int) (interpolatedBlue * 1.7), 255);
        }

        return TextColor.color(interpolatedRed, interpolatedGreen, interpolatedBlue);
    }
}
