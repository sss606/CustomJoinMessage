package ru.solomka.join.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.join.core.enums.InventoryType;
import ru.solomka.join.core.enums.MessageType;
import ru.solomka.join.core.inventory.Menu;
import ru.solomka.join.core.shop.ShopHandler;

import static ru.solomka.join.config.ConfigManager.getInt;

public class DefaultMsgMenu implements Listener {

    private final ShopHandler shop = new ShopHandler();
    private final Menu menu = new Menu();

    @EventHandler
    public void clickMainInventory(InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if (p.getOpenInventory().getTitle().contains("Стандартные сообщения")) {
            if (click == null) return;
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 18: {
                    menu.create(InventoryType.MAIN, p);
                    return;
                }
                case 1 : case 2 : case 3 : case 4 : case 5 : case 6 : case 7 :
                case 8 : case 9 :  case 10 : case 11 : case 12 : case 13 : case 14 :
                case 15 : case 16 : case 17 : case 19 : case 20 : case 21 :
                case 22 : case 23 : case 24 : case 25 : case 26 : case 27 : case 28 :
                case 29 : case 30 : case 31 : case 32 : case 33 : case 34 : case 35 : case 36 : {
                    if(click.getTitle().contains("Главное меню")) return;
                    shop.buyMessage(p, getInt("Messages.Id." + shop.getMsgId(MessageType.DEFAULT, slot) + ".Cost"), shop.getMsgId(MessageType.DEFAULT, slot));
                }
            }
        }
    }
}