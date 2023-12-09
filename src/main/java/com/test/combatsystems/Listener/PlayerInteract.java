package com.test.combatsystems.Listener;

import com.test.combatsystems.DataManager.PlayerData;
import com.test.combatsystems.SleepThread;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.serverct.ersha.api.AttributeAPI;
import org.serverct.ersha.attribute.data.AttributeData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.test.combatsystems.DataManager.DataManager.time;


public class PlayerInteract implements Listener {
    public static HashMap<Player, PlayerData> playerDataMap = new HashMap<>();

    @EventHandler
    public void onShieldRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!playerDataMap.containsKey(player)) {
            PlayerData playerData = new PlayerData(e.getPlayer());
            playerDataMap.put(player, playerData);
        }
        if (e.hasItem()) {
            Material itemType = e.getItem().getType();
            String itemType1 = itemType.toString();
            //判断右键
            if (!playerDataMap.get(player).isAgainst() && playerDataMap.get(player).isCOOLDOWN()) {
                playerDataMap.get(player).setCOOLDOWN(false);
                if (itemType == Material.SHIELD &&
                        (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                    playerDataMap.get(player).setAgainst(true);
                    playerDataMap.get(player).setRightAction(true);
                    SleepThread st1 = new SleepThread(e.getPlayer());
                    st1.start();
                }
                if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                        && itemType1.contains("SWORD")) {
                    playerDataMap.get(player).setAgainst(true);
                    playerDataMap.get(player).setRightAction(true);
                    SleepThread st1 = new SleepThread(e.getPlayer());
                    playerDataMap.get(player).setHandSword(true);
                    st1.start();
                }
            }
        }

    }
}

