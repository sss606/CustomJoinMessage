package ru.solomka.join.core.enums;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.solomka.join.config.utils.CustomConfig;

public enum ItemType {

    DEFAULT_ITEM(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) new CustomConfig("menu").getInt("GUI.Global.Background.ColorPane"))),
    MSG_DEFAULT(new ItemStack(Material.BOOK)),
    MSG_PREMIUM(new ItemStack(Material.ENCHANTED_BOOK)),
    MSG_DEVELOP(new ItemStack(Material.REDSTONE_COMPARATOR)),
    BACK_ITEM(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14)),
    CHANGE_MODE(new ItemStack(Material.REDSTONE));

    @Getter private final ItemStack item;

    ItemType(ItemStack item) {
        this.item = item;
    }
}
