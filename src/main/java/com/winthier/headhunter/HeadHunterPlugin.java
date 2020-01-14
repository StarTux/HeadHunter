package com.winthier.headhunter;

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

    ItemStack makePlayerHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(player);
        meta.setPlayerProfile(player.getPlayerProfile());
        item.setItemMeta(meta);
        return item;
    }
}
