package ru.solomka.join.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.JoinHandler;
import ru.solomka.join.core.enums.InventoryType;
import ru.solomka.join.core.inventory.Menu;
import ru.solomka.join.core.messages.Messages;
import ru.solomka.join.core.shop.ShopHandler;

import static ru.solomka.join.config.ConfigManager.getString;

public class DevelopMsgMenu implements Listener {

    private final ShopHandler shop = new ShopHandler();
    private final Menu menu = new Menu();

    @EventHandler
    public void clickMainInventory(InventoryClickEvent e) {
        Inventory click = e.getClickedInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if(click.getTitle().contains("Главное меню")) return;
        if (p.getOpenInventory().getTitle().contains("Управление сообщениями")) {
            if (slot <= 36) e.setCancelled(true);
            switch (slot) {

                case 11: {
                    new Menu().create(InventoryType.DEFAULT_DEVELOP, p);
                    return;
                }
                case 15: {
                    new Menu().create(InventoryType.PREMIUM_DEVELOP, p);
                    return;
                }

                case 31: {
                    CustomConfig data = new CustomConfig("data");
                    if(data.getString("players." + p.getUniqueId() + ".Mode").equals("") || data.getString("players." + p.getUniqueId() + ".Mode").equals("MESSAGE")) {
                        if(data.getString("players." + p.getUniqueId() + ".HaveMessages") == null)  {
                            data.setValue("players." + p.getUniqueId() + ".ActiveMessage", getString("Messages.Id.DefaultMessage"));
                            data.setValue("players." + p.getUniqueId() + ".Mode", "MESSAGE");
                            Messages.selectMode.replace("{mode}", "MESSAGE").send(p);
                            Messages.setDefaultMessage.send(p);
                            p.closeInventory();
                            return;
                        }
                        data.setValue("players." + p.getUniqueId() + ".Mode", "RANDOM");
                        data.setValue("players." + p.getUniqueId() + ".ActiveJoinMessage", "");
                        data.setValue("players." + p.getUniqueId() + ".ActiveMessage", "");
                        Messages.selectMode.replace("{mode}", "RANDOM").send(p);
                        p.closeInventory();
                        return;
                    }

                    if(data.getString("players." + p.getUniqueId() + ".Mode").equals("RANDOM")) {
                        data.setValue("players." + p.getUniqueId() + ".Mode", "MESSAGE");
                        Messages.selectMode.replace("{mode}", "MESSAGE").send(p);
                        if(data.getString("players." + p.getUniqueId() + ".HaveMessages") == null)  {
                            data.setValue("players." + p.getUniqueId() + ".ActiveMessage", getString("Messages.Id.DefaultMessage"));
                            data.setValue("players." + p.getUniqueId() + ".Mode", "MESSAGE");
                            Messages.selectMode.replace("{mode}", "MESSAGE").send(p);
                            Messages.setDefaultMessage.send(p);
                            p.closeInventory();
                            return;
                        }
                        data.setValue("players." + p.getUniqueId() + ".Mode", "MESSAGE");
                        data.setValue("players." + p.getUniqueId() + ".ActiveMessage", new JoinHandler().getRandomMessage(p, data.getRandom(shop.getAllHaveMsg(p))));
                        p.closeInventory();
                    }
                }
            }
        }
    }
}