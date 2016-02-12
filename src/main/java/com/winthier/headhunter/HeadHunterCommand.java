package com.winthier.headhunter;

import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

class HeadHunterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = sender instanceof Player ? (Player)sender : null;
        if (args.length == 0) {
            return false;
        } else if (args[0].equals("reload")) {
            HeadHunterPlugin.instance.load();
            sender.sendMessage("Config reloaded");
        } else if (args[0].equals("info") && args.length == 2) {
            Player target = Bukkit.getServer().getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Player not found: " + args[1]);
                return true;
            }
            sender.sendMessage("HeadHunter info of " + target.getName() + " [");
            for (Map.Entry<EntityType, Integer> entry: MobHeadListener.instance.getPlayerData(target).entityScore.entrySet()) {
                int score = entry.getValue();
                if (score == 0) continue;
                sender.sendMessage(" " + entry.getKey() + ": " + entry.getValue());
            }
            sender.sendMessage("]");
        } else {
            return false;
        }
        return true;
    }
}
