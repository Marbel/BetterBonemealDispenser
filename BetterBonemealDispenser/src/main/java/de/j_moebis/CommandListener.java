package de.j_moebis;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CommandListener implements CommandExecutor{
    final BetterBonemealDispenser plugin;

    CommandListener(BetterBonemealDispenser plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(!command.getName().equalsIgnoreCase("betterbonemealdispenser")) {
			return false;
        }

        if(!sender.hasPermission("betterbonemeal")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
            return true;
        }
        if(args.length < 1){
            sender.sendMessage(ChatColor.YELLOW + "Not enough arguments!");
            return false;
        }

        if(args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.BLUE + "Config reloaded!");
            return true;
        }

        return false;
    }
}