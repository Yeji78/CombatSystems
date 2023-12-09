package com.test.combatsystems;


import org.bukkit.entity.Player;

import java.util.Objects;

import static com.test.combatsystems.DataManager.DataManager.*;
import static com.test.combatsystems.Listener.PlayerInteract.playerDataMap;


public class SleepThread extends Thread {
    private final Player player;

    public SleepThread(Player player) {
        this.player = player;
    }

    public void run() {
        time = playerDataMap.get(player).getTime();
        if (playerDataMap.get(player).isHandSword() && sword) {
            time = 200L;
        }
        cooldown = playerDataMap.get(player).getCooldown();
        if (time < cooldown) {
            try {
                Thread.sleep(time);
            } catch (Exception ignored) {
            }
            playerDataMap.get(player).setAgainst(false);
            playerDataMap.get(player).setRightAction(false);
            try {
                Thread.sleep(cooldown - time);
            } catch (Exception ignored) {
            }
            playerDataMap.get(player).setCOOLDOWN(true);
        }
        if (Objects.equals(time, cooldown)) {
            try {
                Thread.sleep(time);
            } catch (Exception ignored) {
            }
            playerDataMap.get(player).setRightAction(false);
            playerDataMap.get(player).setCOOLDOWN(true);
            playerDataMap.get(player).setAgainst(false);

        }
        if (time > cooldown) {
            try {
                Thread.sleep(cooldown);
            } catch (Exception ignored) {
            }
            playerDataMap.get(player).setRightAction(false);
            playerDataMap.get(player).setCOOLDOWN(true);
            try {
                Thread.sleep(time - cooldown);
            } catch (Exception ignored) {
            }
            playerDataMap.get(player).setAgainst(false);
        }
        playerDataMap.get(player).setHandSword(false);
    }
}
