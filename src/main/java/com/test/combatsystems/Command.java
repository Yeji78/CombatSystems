package com.test.combatsystems;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import static com.test.combatsystems.CombatSystems.getPlugin;
import static com.test.combatsystems.CombatSystems.reload;


public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        //如果触发者是玩家
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")
                    && player.hasPermission("combatsystems.reload")) {
                try {
                    reload();
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                player.sendMessage("重载成功");
                return true;
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                try {
                    reload();
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                getPlugin().getLogger().info("重载成功");
                return true;
            }
        }
        return true;
    }
}


