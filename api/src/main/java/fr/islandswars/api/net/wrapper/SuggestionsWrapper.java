package fr.islandswars.api.net.wrapper;

import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.List;

/**
 * File <b>SuggestionsWrapper</b> located on fr.islandswars.api.net.wrapper
 * SuggestionsWrapper is a part of islands.
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
 * Created the 06/02/2021 at 18:59
 * @since 1.0
 */
public class SuggestionsWrapper extends Wrapper<Suggestions> {

	public SuggestionsWrapper(Suggestions handle) {
		super(handle);
	}

	public int getStart() {
		return handle.getRange().getStart();
	}

	public int getEnd() {
		return handle.getRange().getEnd();
	}

	public int getLength() {
		return handle.getRange().getLength();
	}

	public List<Suggestion> getSuggestion() {
		return handle.getList();
	}

	public Suggestions getNMS() {
		return handle;
	}
}
