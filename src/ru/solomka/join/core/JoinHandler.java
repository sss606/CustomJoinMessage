package ru.solomka.join.core;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.solomka.join.config.utils.CustomConfig;
import ru.solomka.join.core.enums.MessageType;
import ru.solomka.join.core.messages.Messages;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static ru.solomka.join.config.ConfigManager.getIntegerList;
import static ru.solomka.join.config.ConfigManager.getString;

public class JoinHandler {

    private int messagesSize = 0;

    public void setJoinMessage(@NotNull Player p, int id) {
        new CustomConfig("data").setValue("players." + p.getUniqueId() + ".ActiveJoinMessage", id);
    }

    public String getRandomMessage(Player p, int random) {
        return translateAlternateColorCodes('&', getString("Messages.Id." + random + ".JoinMessage"));
    }

    public String getModeMsg(@NotNull Player p) {
        return new CustomConfig("data").getString("players." + p.getUniqueId() + ".Mode");
    }

    public @Nullable Sound getSoundName(@NotNull String sound) {
        switch (sound) {
            case "ENTITY_TNT_PRIMED" : return Sound.ENTITY_TNT_PRIMED;
            case "ENTITY_GHAST_SHOOT" : return Sound.ENTITY_GHAST_SHOOT;
            case "ENTITY_FIREWORK_SHOOT" : return Sound.ENTITY_FIREWORK_SHOOT;
            case "ENTITY_VILLAGER_YES" : return Sound.ENTITY_VILLAGER_YES;
            case "ENTITY_WITHER_DEATH" : return Sound.ENTITY_WITHER_DEATH;
            case "ENTITY_PLAYER_ATTACK_CRIT" : return Sound.ENTITY_PLAYER_ATTACK_CRIT;
            case "ENTITY_SKELETON_DEATH" : return Sound.ENTITY_SKELETON_DEATH;
            case "ITEM_CHORUS_FRUIT_TELEPORT" : return Sound.ITEM_CHORUS_FRUIT_TELEPORT;
            default: Messages.logger("Couldn't find the sound: " + sound);
        }
        return null;
    }

    public String translateSound(@NotNull Sound sound) {
        switch (sound) {
            case ENTITY_TNT_PRIMED: return "Звук поджигания TNT";
            case ENTITY_GHAST_SHOOT: return  "Звук выстрела гаста";
            case ENTITY_FIREWORK_SHOOT: return "Звук выстрела Fireball";
            case ENTITY_VILLAGER_YES: return "Звук успешной торговли с жителем";
            case ENTITY_WITHER_DEATH: return "Звук смерти Визера";
            case ENTITY_PLAYER_ATTACK_CRIT: return "Звук атаки с крит.ударом";
            case ENTITY_SKELETON_DEATH: return "Звук смерти скелета";
            case ITEM_CHORUS_FRUIT_TELEPORT: return "Звук телепорта с помощью хоруса";
        }
        return null;
    }

    public boolean isSizeMoreLimit(@NotNull MessageType classType) {
        switch (classType) {
            case DEFAULT:
            case PREMIUM: {
                for (int i : getIntegerList("RegIds")) {
                    if (getString("Messages.Id." + i + ".Type").equals(classType.name())) messagesSize++;
                }
                try {
                    if (messagesSize <= 20) return true;
                } catch (IndexOutOfBoundsException exception) {
                    throw new IndexOutOfBoundsException("You have exceeded the number limit in the class <" + classType.name() + ">. Size: " + messagesSize + ". Limit: 20");
                }
            }
        }
        return false;
    }

}