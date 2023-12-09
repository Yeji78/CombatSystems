package com.test.combatsystems.DataManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

import static com.test.combatsystems.CombatSystems.getPlugin;


public class DataManager {
    public static Long time;
    public static Long cooldown;
    public static double damage;
    public static double attack;
    public static Set<String> allName;
    public static HashMap<String, String> trueCommand = new HashMap<>();
    public static HashMap<String, String> falseCommand = new HashMap<>();
    public static String trueTip;
    public static String falseTip;
    public static String damageName;
    public static double damageNameNumber;
    public static double swordDamage;
    public static double bowDamage;
    public static double axenowDamage;
    public static double axeedDamage;
    public static boolean sword;
    public static boolean openSword;
    public static boolean openAxp;
    public static boolean openBow;
    public static boolean openBlock;
    public static boolean openShield;


    public static void loadAll() throws FileNotFoundException, UnsupportedEncodingException {
//        File = new File(getPlugin().getDataFolder(), "config.yml");
//        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
//        YamlConfiguration yml = YamlConfiguration.loadConfiguration(reader);
        FileConfiguration yml = YamlConfiguration.loadConfiguration(new File(getPlugin().getDataFolder(), "config.yml"));
        trueCommand.clear();
        falseCommand.clear();
        time = yml.getLong("time");
        cooldown = yml.getLong("cooldown");
        damage = yml.getDouble("减免倍率");
        attack = yml.getDouble("反击伤害倍率");
        allName = yml.getConfigurationSection("触发指令").getKeys(false);
        openSword = yml.getConfigurationSection("开启效果").getBoolean("剑连击加成");
        openAxp = yml.getConfigurationSection("开启效果").getBoolean("斧连击加成");
        openBow = yml.getConfigurationSection("开启效果").getBoolean("弓连击加成");
        openBlock = yml.getConfigurationSection("开启效果").getBoolean("格挡");
        openShield = yml.getConfigurationSection("开启效果").getBoolean("盾反");
        trueTip = yml.getString("成功提示").replace("&", "§");
        falseTip = yml.getString("失败提示").replace("&", "§");
        damageName = yml.getString("伤害名称");
        damageNameNumber = yml.getDouble("三连反伤倍率");
        swordDamage = yml.getDouble("剑附加伤害");
        axenowDamage = yml.getDouble("怪物目前血量比例");
        axeedDamage = yml.getDouble("怪物已损失血量比例");
        bowDamage = yml.getDouble("弓附加伤害");
        sword = yml.getBoolean("剑固定时间");
        for (String name : allName) {
            String command = yml.getString("触发指令." + name + ".成功指令");
            String command1 = yml.getString("触发指令." + name + ".失败指令");
            trueCommand.put(name, command);
            falseCommand.put(name, command1);
        }
    }

}