package com.winthier.headhunter;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class HeadHunterCommand implements CommandExecutor {
    final HeadHunterPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        final Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0) {
            return false;
        }
        switch (args[0]) {
        case "reload":
            plugin.load();
            sender.sendMessage("Config reloaded");
            return true;
        case "give": {
            if (args.length > 2) return false;
            Player target;
            if (args.length >= 2) {
                target = plugin.getServer().getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found: " + args[1]);
                    return true;
                }
            } else {
                if (player == null) {
                    sender.sendMessage("Player expected!");
                    return true;
                }
                target = player;
            }
            target.getWorld()
                .dropItem(target.getEyeLocation(), plugin.makePlayerHead(target))
                .setOwner(target.getUniqueId());
            sender.sendMessage("Player head given to " + target.getName() + ".");
            return true;
        }
        case "spawn": {
            if (args.length > 2) return false;
            if (player == null) {
                sender.sendMessage("Player expected!");
                return true;
            }
            Player target;
            if (args.length >= 2) {
                target = plugin.getServer().getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player not found: " + args[1]);
                    return true;
                }
            } else {
                target = player;
            }
            player.getWorld()
                .dropItem(player.getEyeLocation(), plugin.makePlayerHead(target))
                .setOwner(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW
                               + "Spawned player head of " + target.getName() + ".");
            return true;
        }
        default:
            return false;
        }
    }
}
