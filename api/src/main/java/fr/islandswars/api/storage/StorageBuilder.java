package fr.islandswars.api.storage;

import fr.islandswars.api.item.IslandsItem;
import fr.islandswars.api.utils.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * File <b>StorageBuilder</b> located on fr.islandswars.api.storage
 * StorageBuilder is a part of islands.
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
 * Created the 07/02/2021 at 21:48
 * @since 0.1
 */
public class StorageBuilder {

	private final String                      name;
	private final int                         size;
	private final StorageType                 type;
	private final Map<Character, IslandsItem> patternKey;
	private       int                         maxSlot;
	private       Supplier<Object[]>          nameParameters;
	private       String                      pattern;

	private StorageBuilder(String name, int size, StorageType type) {
		this.nameParameters = () -> new Object[0];
		this.patternKey = new HashMap<>();
		this.maxSlot = 64;
		this.type = type;
		this.name = name;
		this.size = size;
	}

	/**
	 * Create a new Storage builder to create a custom Inventory
	 *
	 * @param name this storage display name, or translation key
	 * @param size this storage max size, must be a multiple of 9 between [9;54]
	 * @param type the storage strategy to use
	 * @return a builder
	 */
	public static StorageBuilder build(String name, int size, StorageType type) {
		Preconditions.checkNotNull(name);
		Preconditions.checkNotNull(type);
		Preconditions.checkState(size, ref -> ref % 9 == 0 && ref >= 9 && ref <= 6 * 9, "Size must be a multiple of 9 from [9; 18; 27; 36; 45; 54]");

		return new StorageBuilder(name, size, type);
	}

	/**
	 * @return this storage name
	 */
	public int getMaxSlot() {
		return maxSlot;
	}

	/**
	 * @return this storage name/translation key
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return if exist the nameParameters use for formatting
	 */
	public Supplier<Object[]> getNameParameters() {
		return nameParameters;
	}

	/**
	 * @return if exist the wrapped supplied pattern
	 */
	public Optional<String> getPattern() {
		return Optional.ofNullable(pattern);
	}

	/**
	 * @return the pattern map
	 */
	public Map<Character, IslandsItem> getPatternKey() {
		return patternKey;
	}

	/**
	 * @return this storage size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return this storage strategy
	 */
	public StorageType getType() {
		return type;
	}

	/**
	 * Set parameters to translate the storage name, and enable internalisation
	 *
	 * @param nameParameters translation parameters
	 * @return this builder
	 */
	public StorageBuilder setGlobalNameParameter(Supplier<Object[]> nameParameters) {
		Preconditions.checkNotNull(nameParameters);

		this.nameParameters = nameParameters;
		return this;
	}

	/**
	 * Set an item according to this key, item's event is redefine to activate {@link org.bukkit.event.Cancellable#setCancelled(boolean) true}
	 * Only work with {@link StorageType#GLOBAL}
	 *
	 * @param key  a char key, different from 'O' and ' '
	 * @param item a non null item
	 * @return this builder
	 */
	public StorageBuilder supplyPattern(char key, IslandsItem item) {
		Preconditions.checkNotNull(key);
		Preconditions.checkNotNull(item);
		Preconditions.checkState(key, ref -> !ref.equals('O') && !ref.equals(' '), "A pattern key must be different from air key ('O', ' ')!");

		item.onClick((player, event) -> event.setCancelled(true));
		patternKey.put(key, item);
		return this;
	}

	/**
	 * Set a new max stack size
	 *
	 * @param maxSlot an int between [1;128[
	 * @return this builder
	 */
	public StorageBuilder withMaxSlot(int maxSlot) {
		Preconditions.checkState(maxSlot, ref -> ref >= 1 && ref <= 128);

		this.maxSlot = maxSlot;
		return this;
	}

	/**
	 * Supply a pattern to fulfil this inventory. It will fetch all char and set an item according to
	 * the char. 'O' means nothing, it's equals to ' ' and indicates to skip this item index
	 *
	 * @param pattern a pattern String that is not empty
	 * @return this builder
	 * @see #supplyPattern(char, IslandsItem)
	 */
	public StorageBuilder withPattern(String pattern) {
		Preconditions.checkNotNull(pattern);
		Preconditions.checkState(pattern, ref -> !ref.isEmpty() && ref.length() <= getSize());

		this.pattern = pattern;
		return this;
	}
}
