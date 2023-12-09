package com.test.combatsystems.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import static com.test.combatsystems.Listener.PlayerInteract.playerDataMap;


public class PlayerItemSwitch implements Listener {
    @EventHandler
    public void onPlayerSwitchItem(PlayerItemHeldEvent e) {
        Player player = e.getPlayer();
        if (e.getPreviousSlot() != e.getNewSlot()) {
            try {
                playerDataMap.get(player).setBow(0);
                playerDataMap.get(player).setAxp(0);
                playerDataMap.get(player).setSword(0);
                playerDataMap.get(player).setShield(0);
                playerDataMap.get(player).setBlock(0);
                playerDataMap.get(player).setAgainst(false);
            } catch (Exception ignored) {
            }
        }
    }
}
