package com.test.combatsystems;


import com.test.combatsystems.Listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static com.test.combatsystems.DataManager.DataManager.loadAll;


public final class CombatSystems extends JavaPlugin {

    private static CombatSystems plugin;

    public static CombatSystems getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        //如果默认配置不存在 就加载一个
        Bukkit.getPluginCommand("combatsystems").setExecutor(new Command());

        try {
            loadAll();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //加载默认配置
        // Plugin startup logic
        getLogger().info("插件已安装");
        Bukkit.getPluginManager().registerEvents(new APLister(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerAttack(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerItemSwitch(),this);
        Bukkit.getPluginManager().registerEvents(new PlayerFighting(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("插件已卸载");

    }

    public static void reload() throws FileNotFoundException, UnsupportedEncodingException {
        plugin.saveDefaultConfig();
        //加载默认配置
        loadAll();
    }

}

