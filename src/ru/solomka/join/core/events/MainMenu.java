package ru.solomka.join.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.join.core.JoinHandler;
import ru.solomka.join.core.enums.InventoryType;
import ru.solomka.join.core.inventory.Menu;
import ru.solomka.join.core.messages.Messages;
import ru.solomka.join.core.shop.ShopHandler;

import static ru.solomka.join.core.JoinHandler.*;

public class MainMenu implements Listener {

    private final Menu menu = new Menu();
    private final ShopHandler shop = new ShopHandler();

    @EventHandler
    public void clickMainInventory(InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if(click.getTitle().contains("Управление сообщениями")) return;
        if (p.getOpenInventory().getTitle().contains("Главное меню")) {
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {
                case 11: {
                    menu.create(InventoryType.DEFAULT, p);
                    return;
                }

                case 15: {
                    if (!shop.hasPremium(p)) {
                        Messages.noHavePerm.replace("{perm}", "CJMessage.premium").send(p);
                        p.closeInventory();
                        return;
                    }
                    menu.create(InventoryType.PREMIUM, p);
                    return;
                }

                case 22: {
                    menu.create(InventoryType.DEVELOP, p);
                }
            }
        }
    }
}