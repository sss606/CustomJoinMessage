package ru.solomka.join.vault;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHandler {

    private static Economy e;

    public static void init() {
        RegisteredServiceProvider<Economy> reg = Bukkit.getServicesManager().getRegistration(Economy.class);
        if(reg != null) e = reg.getProvider();
    }

    public boolean takeMoney(Player p, double amount) {
        if(e == null) return false;
        return e.withdrawPlayer(p, amount).transactionSuccess();
    }

    public boolean giveSalary(Player p, double salary) {
        if(e == null) return false;
        return e.depositPlayer(p, salary).transactionSuccess();
    }

}
