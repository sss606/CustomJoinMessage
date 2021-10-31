package ru.solomka.join.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.enums.InventoryType;
import ru.solomka.join.core.inventory.Menu;
import ru.solomka.join.core.messages.Messages;

public class MainMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {

        if(!(sender instanceof Player)) {
            Messages.onlyForPlayer.send(sender);
            return true;
        }

        Player p = (Player) sender;

        if(!p.hasPermission("CJMessage.user"))  {
            Messages.noHavePerm.replace("{perm}", "CJMessage.user");
            return true;
        }
        new Menu().create(InventoryType.MAIN, p);
        new CustomConfig("data").setValue("players." + p.getUniqueId() + ".Premium", true);
        return true;
    }
}
