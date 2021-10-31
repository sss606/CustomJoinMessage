package ru.solomka.join.core.shop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.JoinHandler;
import ru.solomka.join.core.enums.MessageType;
import ru.solomka.join.core.enums.ModeMsgType;
import ru.solomka.join.core.messages.Messages;
import ru.solomka.join.vault.VaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.solomka.join.config.ConfigManager.*;
import static ru.solomka.join.config.ConfigManager.getString;

public class ShopHandler {

    private final List<Integer> messages = new ArrayList<>();
    private final List<Integer> allMessage = new ArrayList<>();

    private final JoinHandler join = new JoinHandler();

    public void buyMessage(@NotNull Player p, int cost, int id) {
        CustomConfig data = new CustomConfig("data");
        if(id == -1) return;

        if(isHaveMessage(p,id)) {
            Messages.alreadyHaveMessage.send(p);
            return;
        }

        if(!new VaultHandler().takeMoney(p, cost)) {
            Messages.noMoney.send(p);
            return;
        }

        new VaultHandler().takeMoney(p, cost);
        Messages.successBuy.replace("{msg_id}", String.valueOf(id)).send(p);
        data.setValue("players." + p.getUniqueId() + ".HaveMessages." + id + ".Upload", "");
        if(data.getString("players." + p.getUniqueId() + ".Mode") == null) data.setValue("players." + p.getUniqueId() + ".Mode", ModeMsgType.MESSAGE.name());
        if(!Objects.equals(data.getString("players." + p.getUniqueId() + ".Mode"), "RANDOM")) join.setJoinMessage(p, id);
        p.closeInventory();
    }

    public boolean hasPremium(@NotNull Player p) {
        return new CustomConfig("data").getBoolean("players." + p.getUniqueId() + ".Premium");
    }

    public List<Integer> getAllHaveMsg(Player p) {
        for(int i : getIntegerList("RegIds")) {
            if(!isHaveMessage(p, i)) continue;
            messages.add(i);
        }
        return messages;
    }

    public List<Integer> getAllMessageType(MessageType type) {
        for(int i : getIntegerList("RegIds")) {
            if(!getString("Messages.Id." + i + ".Type").contains(type.name())) continue;
            allMessage.add(i);
        }
        return allMessage;
    }

    public int getMsgId(MessageType type, int slot) {
        for(int i : getIntegerList("RegIds")) {
            if(getInt("Messages.Id." + i + ".Slot") != slot || !getString("Messages.Id." + i + ".Type").equals(type.name())) continue;
            return i;
        }
        return -1;
    }

    public void settingsMsg(MessageType type, @NotNull Player p, int slot) {
        CustomConfig data = new CustomConfig("data");
        if(new CustomConfig("data").getString("players." + p.getUniqueId() + ".HaveMessages." + getMsgId(type, slot)) == null) {
            Messages.noHaveMessage.send(p);
            return;
        }
        data.setValue("players." + p.getUniqueId() + ".ActiveJoinMessage", getMsgId(type, slot));
        data.setValue("players." + p.getUniqueId() + ".ActiveMessage", getString("Messages.Id." + getMsgId(type, slot) + ".JoinMessage"));
        Messages.setMessageJoin.replace("{message}", ChatColor.translateAlternateColorCodes('&', getString("Messages.Id." + getMsgId(type, slot) + ".JoinMessage").replace("{player}", p.getName()))).send(p);
        p.closeInventory();
    }
}
