package com.test.combatsystems.Listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.serverct.ersha.api.AttributeAPI;
import org.serverct.ersha.api.event.attribute.AttrAttributeTriggerEvent;

import static com.test.combatsystems.Listener.PlayerInteract.playerDataMap;

public class APLister implements Listener {
    @EventHandler
    public void onAttribute(AttrAttributeTriggerEvent.After e) {
        String s = e.toServerName("闪避几率");
        LivingEntity livingEntity1 = e.getAttributeHandle().getEntity();
        if (e.getSubAttribute().getAttributeName().equals(s)) {
            try {
                Player player = (Player) livingEntity1;
                if (playerDataMap.get(player).getSword() > 0) {
                    Integer number = playerDataMap.get(player).getSword();
                    playerDataMap.get(player).setSword(number - 3);
                }
                if (playerDataMap.get(player).getBow() > 0) {
                    Integer number = playerDataMap.get(player).getBow();
                    playerDataMap.get(player).setBow(number - 3);
                }
                if (playerDataMap.get(player).getAxp() > 0) {
                    Integer number = playerDataMap.get(player).getAxp();
                    playerDataMap.get(player).setAxp(number - 3);
                }
            } catch (
                    Exception ignored) {
            }
        }

    }
}
