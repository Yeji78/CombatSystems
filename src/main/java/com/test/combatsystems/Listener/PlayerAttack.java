package com.test.combatsystems.Listener;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.serverct.ersha.api.AttributeAPI;

import static com.test.combatsystems.DataManager.DataManager.*;
import static com.test.combatsystems.Listener.PlayerInteract.playerDataMap;
import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.PROJECTILE;

public class PlayerAttack implements Listener {
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player
                && e.getCause().equals(ENTITY_ATTACK)
                && e.getEntity() instanceof LivingEntity) {
            Player player = (Player) e.getDamager();
            LivingEntity entity1 = (LivingEntity) e.getEntity();
            ItemStack handItem = player.getInventory().getItemInMainHand();

            Number[] numbers = new Number[0];
            if (handItem != null
                    && handItem.getType() != Material.AIR
                    && AttributeAPI.getAttributeSource(handItem).getAttributeValue().get(damageName) != null) {
                numbers = AttributeAPI.getAttributeSource(handItem).getAttributeValue().get(damageName);
            }
            Double damageMin = 0.0;
            Double damageMax = 1.0;
            if (numbers != null && numbers.length > 1) {
                damageMin = (Double) numbers[0];
                damageMax = (Double) numbers[1];
            }
            double itemDamage = (damageMin + Math.random() * (damageMax - damageMin + 1));

            //平A
            if (handItem != null) {
                if (e.getCause().equals(ENTITY_ATTACK)) {
                    String itemType1 = handItem.getType().toString();
                    if (openSword && itemType1.contains("SWORD")) {
                        int a = playerDataMap.get(player).getSword() + 1;
                        if (playerDataMap.get(player).getSword() < 9) {
                            player.sendMessage("§4连击数§6" + a);
                            playerDataMap.get(player).setSword(a);
                        }
                        if (playerDataMap.get(player).getSword() == 9) {
                            player.sendMessage("§4剑连击数已满§6(" + playerDataMap.get(player).getSword() + ")层");
                        }
                        if (playerDataMap.get(player).getSword() >= 3 && entity1 != null) {
                            int number = playerDataMap.get(player).getSword() / 3;
                            double damage = ((itemDamage * swordDamage) * (double) number);
                            entity1.damage(damage);
                            player.sendMessage("§4三连触发！§b对目标造成§6" + (int) damage + "§b伤害");
                            return;
                        }
                    }
                    if (openAxp && itemType1.contains("AXE") && !itemType1.contains("PICKAXE")) {
                        int a = playerDataMap.get(player).getAxp() + 1;
                        if (playerDataMap.get(player).getAxp() < 9) {
                            player.sendMessage("§4连击数§6" + a);
                            playerDataMap.get(player).setAxp(a);
                        }
                        if (playerDataMap.get(player).getAxp() == 9) {
                            player.sendMessage("§4斧子连击数已满§6(" + playerDataMap.get(player).getSword() + ")层");
                        }
                        if (playerDataMap.get(player).getAxp() >= 3
                                && entity1 != null
                                && !(entity1 instanceof Player)) {
                            int number = playerDataMap.get(player).getAxp() / 3;
                            double health = entity1.getHealth();
                            double healthed = entity1.getAttribute(GENERIC_MAX_HEALTH).getValue() - health;
                            double damage = ((axenowDamage * health + axeedDamage * healthed) * number);
                            entity1.damage(damage);
                            player.sendMessage("§4三连触发！§b对目标造成§6" + (int) damage + "§b伤害");
                            return;
                        }
                    }
                }
                if (openBow && e.getCause().equals(PROJECTILE)
                        && handItem.getType() == Material.BOW) {
                    if (playerDataMap.get(player).getBow() < 10) {
                        int a = playerDataMap.get(player).getBow() + 1;
                        player.sendMessage("§4连击数§6" + a);
                        playerDataMap.get(player).setBow(a);
                    }
                    if (playerDataMap.get(player).getBow() >= 3) {
                        int number = playerDataMap.get(player).getBow() / 3;
                        if (entity1 != null) {
                            double damage = ((itemDamage * bowDamage) * number);
                            player.sendMessage("§4三连触发！§b对目标造成§6" + (int) damage + "§b伤害");
                            entity1.damage(damage);
                        }
                    }
                }
            }
        }
    }
}