package ru.solomka.join.core.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.JoinHandler;
import ru.solomka.join.core.enums.InventoryType;
import ru.solomka.join.core.enums.ItemType;
import ru.solomka.join.core.enums.MessageType;
import ru.solomka.join.core.messages.Messages;
import ru.solomka.join.core.shop.ShopHandler;

import java.util.Objects;

import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.join.config.ConfigManager.*;
import static ru.solomka.join.core.inventory.utils.ItemManager.setItemInv;
import static ru.solomka.join.core.inventory.utils.ItemManager.setItemMeta;
import static ru.solomka.join.core.messages.Messages.access;
import static ru.solomka.join.core.messages.Messages.status;
import static ru.solomka.join.core.messages.utils.ListHandler.replace;

public class Menu {

    private Inventory main;
    private Inventory premium;
    private Inventory defaults;
    private Inventory develop;
    private Inventory defaultDevelop;
    private Inventory premiumDevelop;

    private final CustomConfig menu = new CustomConfig("menu");

    private final ShopHandler shop = new ShopHandler();
    private final JoinHandler join = new JoinHandler();

    public void create(@NotNull InventoryType type, Player p) {
        switch (type) {
            case MAIN: {
                if (main == null) {
                    main = Bukkit.createInventory(null, 36, "Главное меню");
                    setItems(p, main, type, ItemType.DEFAULT_ITEM, ItemType.MSG_DEFAULT, ItemType.MSG_DEVELOP ,ItemType.MSG_PREMIUM);
                }
                p.openInventory(main);
                return;
            }

            case PREMIUM: {
                if (premium == null){
                    premium = Bukkit.createInventory(null, 36, "Вип сообщения");
                    setItems(p, premium, type, ItemType.DEFAULT_ITEM);
                }
                setItems(p, premium, type, ItemType.DEFAULT_ITEM);
                p.openInventory(premium);
                return;
            }

            case DEVELOP: {
                if (develop == null){
                    develop = Bukkit.createInventory(null, 36, "Управление сообщениями");
                    setItems(p, develop, type, ItemType.DEFAULT_ITEM, ItemType.MSG_DEFAULT, ItemType.MSG_DEVELOP, ItemType.CHANGE_MODE);
                }
                setItems(p, develop, type, ItemType.DEFAULT_ITEM, ItemType.MSG_DEFAULT, ItemType.MSG_DEVELOP, ItemType.CHANGE_MODE);
                p.openInventory(develop);
                return;
            }

            case DEFAULT: {
                if (defaults == null){
                    defaults = Bukkit.createInventory(null, 36, "Стандартные сообщения");
                    setItems(p, defaults, type, ItemType.DEFAULT_ITEM);
                }
                setItems(p, defaults, type, ItemType.DEFAULT_ITEM);
                p.openInventory(defaults);
                return;
            }

            case DEFAULT_DEVELOP: {
                if (defaultDevelop == null){
                    defaultDevelop = Bukkit.createInventory(null, 36, "Настройка стандартного сообщения");
                    setItems(p, defaultDevelop, type, ItemType.DEFAULT_ITEM);
                }
                setItems(p, defaultDevelop, type, ItemType.DEFAULT_ITEM);
                p.openInventory(defaultDevelop);
                return;
            }

            case PREMIUM_DEVELOP: {
                if (premiumDevelop == null){
                    premiumDevelop = Bukkit.createInventory(null, 36, "Настройка VIP сообщения");
                    setItems(p, premiumDevelop, type, ItemType.DEFAULT_ITEM);
                }
                setItems(p, premiumDevelop, type, ItemType.DEFAULT_ITEM);
                p.openInventory(premiumDevelop);
            }
        }
    }

    public void setItems(Player p, Inventory inventory,
                         @NotNull InventoryType type, ItemType... item) {

        if(type != InventoryType.MAIN) {
            inventory.setItem(18, setItemMeta(ItemType.BACK_ITEM.getItem(), translateAlternateColorCodes('&', "&cВернуться"), menu.getList(" ")));
        }

        switch (type) {
            case DEFAULT_DEVELOP: {
                setItemInv(item[0].getItem(), inventory, 0, 36);
                for(int i : shop.getAllMessageType(MessageType.DEFAULT)) {
                    inventory.setItem(getInt("Messages.Id." + i + ".Slot"),
                            setItemMeta(new ItemStack(Material.NAME_TAG),
                                    getString("Messages.Id." + i + ".Name")
                                            .replace("{purchased}", status(i, p)),
                                    replace("{message}", getString("Messages.Id." + i + ".JoinMessage")
                                            .replace("{player}", p.getName()))
                                            .replace("{cost}", String.valueOf(getInt("Messages.Id." + i + ".Cost")))
                                            .list(getStringList("Messages.Id." + i + ".Lore"))));
                }
                return;
            }

            case PREMIUM_DEVELOP: {
                setItemInv(item[0].getItem(), inventory, 0, 36);
                for(int i : shop.getAllMessageType(MessageType.PREMIUM)) {
                    inventory.setItem(getInt("Messages.Id." + i + ".Slot"),
                            setItemMeta(new ItemStack(Material.NAME_TAG),
                                    getString("Messages.Id." + i + ".Name")
                                            .replace("{purchased}", status(i, p)),
                                    replace("{message}", getString("Messages.Id." + i + ".JoinMessage")
                                            .replace("{player}", p.getName()))
                                            .replace("{cost}", String.valueOf(getInt("Messages.Id." + i + ".Cost"))).replace("{sound}",
                                                    join.translateSound(Objects.requireNonNull(join.getSoundName(getString("Messages.Id." + i + ".Sound.Type")))))
                                            .list(getStringList("Messages.Id." + i + ".Lore"))));
                }
                return;
            }

            case PREMIUM: {
                setItemInv(item[0].getItem(), inventory, 0, 36);

                for(int i : shop.getAllMessageType(MessageType.PREMIUM)) {
                    inventory.setItem(getInt("Messages.Id." + i + ".Slot"),
                            setItemMeta(new ItemStack(Material.NAME_TAG),
                                    getString("Messages.Id." + i + ".Name")
                                            .replace("{purchased}", status(i, p)),
                                    replace("{message}", getString("Messages.Id." + i + ".JoinMessage")
                                            .replace("{player}", p.getName()))
                                            .replace("{cost}", String.valueOf(getInt("Messages.Id." + i + ".Cost"))).replace("{sound}",
                                                    join.translateSound(Objects.requireNonNull(join.getSoundName(getString("Messages.Id." + i + ".Sound.Type")))))
                                            .list(getStringList("Messages.Id." + i + ".Lore"))));
                }
                return;
            }

            case DEVELOP: {

                setItemInv(item[0].getItem(), inventory, 0, 36);

                if(new CustomConfig("data").getString("players." + p.getUniqueId() + ".Mode") == null) {
                    new CustomConfig("data").setValue("players." + p.getUniqueId() + ".Mode", "");
                }

                inventory.setItem(11, setItemMeta(item[1].getItem(), menu.getString("GUI.DevelopMenu.Default.Name"), menu.getList("GUI.DevelopMenu.Default.Lore")));

                inventory.setItem(15, setItemMeta(item[2].getItem(), menu.getString("GUI.DevelopMenu.Premium.Name"), menu.getList("GUI.DevelopMenu.Premium.Lore")));

                inventory.setItem(31, setItemMeta(item[3].getItem(), menu.getString("GUI.DevelopMenu.ChangeMode.Name"),
                        replace("{mode}", Messages.mode(p)).list(menu.getList("GUI.DevelopMenu.ChangeMode.Lore"))));

                return;
            }

            case MAIN: {
                setItemInv(item[0].getItem(), inventory, 0, 36);
                inventory.setItem(11, setItemMeta(item[1].getItem(), menu.getString("GUI.MainMenu.Default.Name"), menu.getList("GUI.MainMenu.Default.Lore")));
                inventory.setItem(22, setItemMeta(item[2].getItem(), menu.getString("GUI.MainMenu.DevelopMsg.Name"), menu.getList("GUI.MainMenu.DevelopMsg.Lore")));
                inventory.setItem(15, setItemMeta(item[3].getItem(), menu.getString("GUI.MainMenu.Premium.Name"),
                        replace("{access}", access(p)).list(menu.getList("GUI.MainMenu.Premium.Lore"))));
                return;
            }

            case DEFAULT: {
                setItemInv(item[0].getItem(), inventory, 0, 36);

                for(int i : shop.getAllMessageType(MessageType.DEFAULT)) {
                    inventory.setItem(getInt("Messages.Id." + i + ".Slot"),
                            setItemMeta(new ItemStack(Material.NAME_TAG), getString("Messages.Id." + i + ".Name").replace("{purchased}", status(i, p)), replace("{message}", getString("Messages.Id." + i + ".JoinMessage").replace("{player}", p.getName()))
                                    .replace("{cost}", String.valueOf(getInt("Messages.Id." + i + ".Cost"))).list(getStringList("Messages.Id." + i + ".Lore"))));
                }
            }
        }
    }
}