package ru.solomka.join.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.solomka.join.core.messages.Messages;

import static ru.solomka.join.config.ConfigManager.reload;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) {
            Messages.noHavePerm.send(sender);
            return true;
        }
        reload();
        Messages.reloadCfg.send(sender);
        return true;
    }
}
