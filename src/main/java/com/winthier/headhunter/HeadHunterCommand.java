package com.winthier.headhunter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class HeadHunterCommand implements CommandExecutor {
    final HeadHunterPlugin plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length == 0) {
            return false;
        } else if (args[0].equals("reload")) {
            plugin.load();
            sender.sendMessage("Config reloaded");
        } else {
            return false;
        }
        return true;
    }
}
