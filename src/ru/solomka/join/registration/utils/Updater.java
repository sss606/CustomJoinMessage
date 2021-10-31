package ru.solomka.join.registration.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;
import ru.solomka.join.core.messages.Messages;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Updater {

    private final JavaPlugin plugin;
    private final int id;

    public Updater(JavaPlugin plugin, int id) {
        this.plugin = plugin;
        this.id = id;
    }

    public void getVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.id)
                    .openStream();
                 Scanner scanner = new Scanner(inputStream))
            {
                if(scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch(IOException exception) {
                Messages.logger("Cannot look for updates: " + exception.getMessage());
            }
        });
    }

    public void check(String url) {
        getVersion(version ->
        {
            if (plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
                Messages.logger("No updates detected");
            } else {
                Messages.logger("A new version of the plugin has been found!", "Download the update: " + url);
            }
        });
    }
}