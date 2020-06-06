package com.winthier.headhunter;

import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeadHunterPlugin extends JavaPlugin {
    PlayerHeadListener playerHeadListener = new PlayerHeadListener(this);
    HeadHunterCommand command = new HeadHunterCommand(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(playerHeadListener, this);
        getCommand("headhunter").setExecutor(command);
        load();
    }

    void load() {
        reloadConfig();
        playerHeadListener.load();
    }

    ItemStack makePlayerHead(Player player, Player killer) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setPlayerProfile(player.getPlayerProfile());
        meta.setLore(Arrays.asList(ChatColor.WHITE
                                   + "Killed by "
                                   + ChatColor.GOLD
                                   + killer.getName()
                                   + ChatColor.WHITE + "."));
        item.setItemMeta(meta);
        return item;
    }
}
