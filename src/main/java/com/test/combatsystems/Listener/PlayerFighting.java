package com.test.combatsystems.Listener;

import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.serverct.ersha.api.AttributeAPI;

import static com.test.combatsystems.DataManager.DataManager.*;
import static com.test.combatsystems.Listener.PlayerInteract.playerDataMap;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_ATTACK;

public class PlayerFighting implements Listener {

    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent e) {
        //格挡
        if (e.getEntity() instanceof Player
                && e.getCause().equals(ENTITY_ATTACK)
                && e.getDamager() instanceof LivingEntity) {
            Player player = (Player) e.getEntity();
            ItemStack handItem = player.getInventory().getItemInMainHand();
            String itemType1 = handItem.getType().toString();
            LivingEntity entity = (LivingEntity) e.getDamager();
            if (playerDataMap.get(player).isAgainst()
                    && playerDataMap.get(player).isRightAction()) {
                //剑
                if (openBlock && itemType1.contains("SWORD")) {
                    if (handItem.getItemMeta().hasDisplayName()) {
                        String itemName = handItem.getItemMeta().getDisplayName();
                        for (String name : trueCommand.keySet()) {
                            if (name.contains(itemName)) {
                                String a = trueCommand.get(itemName);
                                String s = a.replace("%player%", player.getName());
                                if (!player.isOp()) {
                                    try {
                                        player.setOp(true);
                                        Bukkit.dispatchCommand(player, s);
                                    } finally {
                                        player.setOp(false);
                                    }
                                    break;
                                } else {
                                    Bukkit.dispatchCommand(player, s);
                                }
                                break;
                            }
                        }
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 10, 10);
                    player.sendMessage("格挡" + trueTip);
                    player.playEffect(player.getLocation(), Effect.ANVIL_LAND, null);
                    player.spawnParticle(Particle.SWEEP_ATTACK, player.getLocation().getX(), player.getLocation().getY() + 1.0D, player.getLocation().getZ(), 1, 0.0D, 0.0D, 0.0D, null);
                    //剑反伤
                    double startDamage = e.getFinalDamage();
                    double damage1 = attack * startDamage;
                    entity.damage(damage1);
                    player.sendMessage("§b对目标造成§6" + (int) damage1 + "§b伤害");
                    //格挡三连
                    if (playerDataMap.get(player).getBlock() == 3) {
                        double damage = (player.getHealth() * 0.1) * (entity.getHealth() * 0.01) * damageNameNumber;
                        entity.damage(damage);
                        player.sendMessage("§4三连触发！§b对目标造成§6" + (int) damage + "§b伤害");
                        e.setDamage(0);
                        playerDataMap.get(player).setBlock(0);
                    } else {
                        e.setDamage(damage * startDamage);
                    }
                    if (playerDataMap.get(player).getBlock() < 4) {
                        int a = playerDataMap.get(player).getBlock() + 1;
                        playerDataMap.get(player).setBlock(a);
                    }
                    Long cooldown1 = playerDataMap.get(player).getCooldown();
                    if (playerDataMap.get(player).getCooldown() > 0.2 * cooldown) {
                        playerDataMap.get(player).setCooldown((long) (cooldown1 - 0.1 * cooldown));
                    } else playerDataMap.get(player).setCooldown((long) (0.2 * cooldown));
                }
                //盾
                if (openShield && handItem.getType() == Material.SHIELD && player.isHandRaised()) {
                    if (player.hasLineOfSight(entity)) {
                        Number[] numbers = new Number[0];
                        if (handItem.getType() != Material.AIR
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
                        if (handItem.getItemMeta().hasDisplayName()) {
                            String itemName = handItem.getItemMeta().getDisplayName();
                            for (String name : trueCommand.keySet()) {
                                if (name.contains(itemName)) {
                                    String a = trueCommand.get(itemName);
                                    String s = a.replace("%player%", player.getName());
                                    if (!player.isOp()) {
                                        try {
                                            player.setOp(true);
                                            Bukkit.dispatchCommand(player, s);
                                        } finally {
                                            player.setOp(false);
                                        }
                                    } else {
                                        Bukkit.dispatchCommand(player, s);
                                    }
                                    break;
                                }
                            }
                        }
                        player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_HURT, 10, 10);
                        if (playerDataMap.get(player).getShield() < 4) {
                            int a = playerDataMap.get(player).getShield() + 1;
                            playerDataMap.get(player).setShield(a);
                        }
                        //三连
                        if (playerDataMap.get(player).getShield() >= 3) {
                            double damage = itemDamage * damageNameNumber;
                            entity.damage(damage);
                            player.sendMessage("§4三连触发！§b对目标造成§6" + (int) damage + "§b伤害");
                        }
                        player.sendMessage("盾反" + trueTip);
                        player.playEffect(player.getLocation(), Effect.ANVIL_LAND, null);
                        player.spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation().getX(), player.getLocation().getY() + 1.0D, player.getLocation().getZ(), 1, 0.0D, 0.0D, 0.0D, null);
                        e.setCancelled(true);
                        Vector v = player.getLocation().getDirection();
                        v = v.setX(v.getX());
                        v = v.setY(0.2D);
                        v = v.setZ(v.getZ());
                        player.playSound(player.getLocation(), Sound.AMBIENT_CAVE, 10, 10);
                        entity.setVelocity(v);
                    }
                }
                //失败
                playerDataMap.get(player).setRightAction(false);
            } else if ((itemType1.contains("SWORD") || handItem.getType() == Material.SHIELD)
                    && !playerDataMap.get(player).isAgainst()
                    && playerDataMap.get(player).isCOOLDOWN()
                    && playerDataMap.get(player).isRightAction()) {
                player.sendMessage("§6时机不对," + falseTip);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 10, 10);
                player.spawnParticle(Particle.CRIT, player.getLocation().getX(), player.getLocation().getY() + 1.0D, player.getLocation().getZ(), 30, 0.0D, 0.0D, 0.0D, null);
                if (handItem.getItemMeta().hasDisplayName()) {
                    String itemName = handItem.getItemMeta().getDisplayName();
                    playerDataMap.get(player).setShield(0);
                    for (String name : falseCommand.keySet()) {
                        if (name.contains(itemName)) {
                            String a = falseCommand.get(itemName);
                            String s = a.replace("%player%", player.getName());
                            if (!player.isOp()) {
                                try {
                                    player.setOp(true);
                                    Bukkit.dispatchCommand(player, s);
                                } finally {
                                    player.setOp(false);
                                }
                            } else {
                                Bukkit.dispatchCommand(player, s);
                            }
                            break;
                        }
                    }
                }
                playerDataMap.get(player).setRightAction(false);
            }
        }
    }
}

