package fr.islandswars.api.inventory.item;

import fr.islandswars.api.player.IslandsPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * File <b>IslandsItem</b> located on fr.islandswars.api.inventory.item
 * IslandsItem is a part of islands.
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
 * Created the 30/03/2024 at 21:21
 * @since 0.1
 */
public class IslandsItem {

    private static int             counter = 0;
    private final  Material        material;
    private final  int             amount;
    private final  List<Component> lores;
    private        Component       name;
    private        ItemStack       bukkitItemCache;
    private        ItemFlag[]      flags;
    private        Player          owningPlayer;

    protected IslandsItem(Material material, int amount) {
        this.lores = new ArrayList<>();
        this.material = material;
        this.amount = amount;
    }

    public static IslandsItem builder(Material material) {
        return builder(material, 1);
    }

    public static IslandsItem builder(Material material, int amount) {
        return new IslandsItem(material, amount);
    }

    public IslandsItem withDefaultName(Component name) {
        return withName(name.color(NamedTextColor.WHITE).style(st -> st.decoration(TextDecoration.ITALIC, false)));
    }

    public IslandsItem withName(Component name) {
        this.name = name;
        return this;
    }

    public IslandsItem addLoreLine(Component lore) {
        lores.add(lore);
        return this;
    }

    public IslandsItem addFlags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public IslandsItem setOwner(IslandsPlayer player) {
        this.owningPlayer = player.getBukkitPlayer();
        return this;
    }

    public ItemStack getBukkitItem() {
        return bukkitItemCache == null ? build() : bukkitItemCache;
    }

    public ItemStack build() {
        var item = new ItemStack(material, amount);
        var meta = item.getItemMeta();
        if (name != null) meta.displayName(name);
        if (!lores.isEmpty()) meta.lore(lores);
        if (flags != null) meta.addItemFlags(flags);
        if (material == Material.PLAYER_HEAD) {
            ((SkullMeta) meta).setOwningPlayer(owningPlayer);
        }
        item.setItemMeta(meta);
        this.bukkitItemCache = item;
        return item;
    }

    @Override
    public String toString() {
        return material.name();
    }
}
