package ru.solomka.join.core.messages;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.solomka.join.Main;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.JoinHandler;
import ru.solomka.join.core.shop.ShopHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.translateAlternateColorCodes;

public enum Messages {

    noMoney,
    alreadyHaveMessage,
    setMessageJoin,
    setRandomMessageJoin,
    onlyForPlayer,
    noHavePerm,
    reloadCfg,
    successBuy, error, selectMode, setDefaultMessage, noHaveMessage;

    private List<String> msg;

    private static final Main plugin = Main.getInstance();
    private static final ShopHandler shop = new ShopHandler();

    public static void load(FileConfiguration c) {
        for(Messages message : Messages.values()) {
            Object obj = c.get("Messages." + message.name().replace("_", "."));
            if(obj instanceof List) {
                message.msg = (((List<String>) obj)).stream().map(m -> translateAlternateColorCodes('&', m)).collect(Collectors.toList());
            }
            else {
                message.msg = Lists.newArrayList(obj == null ? "" : translateAlternateColorCodes('&', obj.toString()));
            }
        }
    }

    public static String access(Player player) {
        if(shop.hasPremium(player)) return "&aНажмите, чтобы открыть...";
        return "&cУ вас нет доступа";
    }

    public static String status(int id, Player p) {
        if(shop.isHaveMessage(p, id)) return "&aКУПЛЕНО";
        return "&cНЕ КУПЛЕНО";
    }

    public static String mode(Player p) {
        if(Objects.equals(new CustomConfig("data").getString("players." + p.getUniqueId() + ".Mode"), "")) {
            return "&7не выбрано";
        } else if(new CustomConfig("data").getString("players." + p.getUniqueId() + ".Mode").contains("MESSAGE")) {
            return "&aMESSAGE";
        } else if (new CustomConfig("data").getString("players." + p.getUniqueId() + ".Mode").contains("RANDOM")) {
            return "&cRANDOM";
        }
        return null;
    }

    public static void logger(String... info) {
        for(String string : info) {
            plugin.getLogger().info(string);
        }
    }

    public Sender replace(String from, String to) {
        Sender sender = new Sender();
        return sender.replace(from, to);
    }

    public void send(CommandSender player) {
        new Sender().send(player);
    }

    public class Sender {
        private final Map<String, String> placeholders = new HashMap<>();

        public void send(CommandSender player) {
            for (String message : Messages.this.msg) {
                sendMessage(player, replacePlaceholders(message));
            }
        }

        public Sender replace(String from, String to) {
            placeholders.put(from, to);
            return this;
        }

        private void sendMessage(CommandSender player, @NotNull String message) {
            if (message.startsWith("json:")) {
                player.sendMessage(String.valueOf(new TextComponent(ComponentSerializer.parse(message.substring(5)))));
            } else {
                player.sendMessage(message);
            }
        }

        private String replacePlaceholders(@NotNull String message) {
            if (!message.contains("{")) return message;
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                message = message.replace(entry.getKey(), entry.getValue());
            }
            return message;
        }
    }
}