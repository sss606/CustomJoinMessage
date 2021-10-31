package ru.solomka.join;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.solomka.join.commands.MainMenuCommand;
import ru.solomka.join.commands.ReloadCommand;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.events.*;
import ru.solomka.join.core.messages.Messages;
import ru.solomka.join.registration.RegistrationHandler;
import ru.solomka.join.vault.VaultHandler;

public class Main extends JavaPlugin {

    @Getter private static Main instance;

    private final RegistrationHandler registrationHandler = new RegistrationHandler(this);

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        getDataFolder().mkdir();

        registrationHandler.registrationEvents(new MainMenu());

        registrationHandler.cfgInitialization("config", "messages", "menu", "data");

        VaultHandler.init();

        Messages.load(new CustomConfig("messages").getConfig());

        registrationHandler.registrationEvents(
                new MainMenu(),
                new DefaultMsgMenu(),
                new PremiumMsgMenu(),
                new DevelopMsgMenu(),
                new Join(),
                new DefaultDevelopMenu(),
                new PremiumDevelopMenu()
        );

        Bukkit.getPluginCommand("cmsg").setExecutor(new MainMenuCommand());
        Bukkit.getPluginCommand("creload").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {
        Messages.logger("disable!");
    }
}
