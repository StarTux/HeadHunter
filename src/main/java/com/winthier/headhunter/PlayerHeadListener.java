package com.winthier.headhunter;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@RequiredArgsConstructor
public final class PlayerHeadListener implements Listener {
    final HeadHunterPlugin plugin;
    final Random random = new Random(System.currentTimeMillis());
    double chance;
    double messageRange;
    String messagePrefix;
    List<String> messages;

    void load() {
        chance = plugin.getConfig().getDouble("PlayerChance");
        messageRange = plugin.getConfig().getDouble("MessageRange");
        messagePrefix = plugin.getConfig().getString("MessagePrefix");
        messages = plugin.getConfig().getStringList("Messages");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) return;
        if (random.nextDouble() >= chance) return;
        // No return; drop head
        ItemStack drop = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) drop.getItemMeta();
        meta.setOwningPlayer(victim);
        meta.setPlayerProfile(victim.getPlayerProfile());
        drop.setItemMeta(meta);
        victim.getWorld().dropItemNaturally(event.getEntity().getEyeLocation(), drop);
        // Send Message
        if (messages.isEmpty()) return;
        String message = messages.get(random.nextInt(messages.size()));
        message = messagePrefix + message;
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replace("%player%", victim.getName());
        double range = messageRange * messageRange;
        for (Player player : victim.getWorld().getPlayers()) {
            Location loc = victim.getLocation();
            if (!loc.getWorld().equals(player.getWorld())) continue;
            if (loc.distanceSquared(player.getLocation()) > range) continue;
            player.sendMessage(message);
        }
    }
}
