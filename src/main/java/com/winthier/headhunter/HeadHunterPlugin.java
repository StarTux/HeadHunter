package com.winthier.headhunter;

import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
        item.editMeta(m -> {
                SkullMeta meta = (SkullMeta) m;
                meta.setOwningPlayer(player);
                meta.setPlayerProfile(player.getPlayerProfile());
                meta.lore(List.of(Component.text().decoration(TextDecoration.ITALIC, false)
                                  .append(Component.text("Killed by ", NamedTextColor.GRAY))
                                  .append(Component.text(killer.getName(), NamedTextColor.GOLD))
                                  .build()));
            });
        return item;
    }
}
