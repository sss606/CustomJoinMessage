package ru.solomka.join.core.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ru.solomka.join.Main;
import ru.solomka.join.config.ConfigManager;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.JoinHandler;
import ru.solomka.join.core.enums.ModeMsgType;
import ru.solomka.join.core.shop.ShopHandler;

import java.util.Objects;

import static org.bukkit.ChatColor.*;
import static ru.solomka.join.config.ConfigManager.*;

public class Join implements Listener {

    private final JoinHandler join = new JoinHandler();
    private final ShopHandler shop = new ShopHandler();

    @EventHandler
    public void join(PlayerJoinEvent event) {
        CustomConfig data = new CustomConfig("data");

        event.setJoinMessage("");

        Player p = event.getPlayer();

        int messageId = data.getInt("players." + p.getUniqueId() + ".ActiveJoinMessage");

        new BukkitRunnable() {

            @Override
            public void run() {
                if(Objects.equals(join.getModeMsg(p), ModeMsgType.MESSAGE.name())) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (getBoolean("Messages.Id." + messageId + ".Sound.Mode")) {
                            pl.playSound(pl.getLocation(), join.getSoundName(getString("Messages.Id." + messageId + ".Sound.Type")), 1.5f, 1.5f);
                            p.playSound(p.getLocation(), join.getSoundName(getString("Messages.Id." + messageId + ".Sound.Type")), 1.5f, 1.5f);
                        }
                        if(data.getString("players." + p.getUniqueId() + ".HaveMessages") == null) {
                            pl.sendMessage(translateAlternateColorCodes('&', getString("Messages.Id.DefaultMessage").replace("{player}", p.getName())));
                            continue;
                        }
                        pl.sendMessage(translateAlternateColorCodes('&', getString("Messages.Id." + messageId + ".JoinMessage").replace("{player}", p.getName())));
                        return;
                    }
                } else if(Objects.equals(join.getModeMsg(p), ModeMsgType.RANDOM.name())) {
                    int random = data.getRandom(shop.getAllHaveMsg(p));
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (getBoolean("Messages.Id." + random + ".Sound.Mode")) {
                            pl.playSound(pl.getLocation(), join.getSoundName(getString("Messages.Id." + random + ".Sound.Type")), 0.5f, 0.5f);
                            p.playSound(p.getLocation(), join.getSoundName(getString("Messages.Id." + random + ".Sound.Type")), 1.5f, 1.5f);
                        }
                        if(data.getString("players." + p.getUniqueId() + ".HaveMessages") == null) pl.sendMessage(translateAlternateColorCodes('&', getString("Messages.Id.DefaultMessage").replace("{player}", p.getName())));
                        pl.sendMessage(translateAlternateColorCodes('&', join.getRandomMessage(p, random).replace("{player}", p.getName())));
                    }
                }
            }
        }.runTaskLater(Main.getInstance(), 15L);
    }
}