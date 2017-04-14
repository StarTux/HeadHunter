package com.winthier.headhunter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

class PlayerHeadListener implements Listener {
    final Random random = new Random(System.currentTimeMillis());
    int chance = 0;
    List<String> messages = new ArrayList<String>();
    static PlayerHeadListener instance = null;

    PlayerHeadListener() {
        instance = this;
    }

    void load() {
        chance = HeadHunterPlugin.instance.getConfig().getInt("PlayerChance", 10);
        for (String message : HeadHunterPlugin.instance.getConfig().getStringList("Messages")) {
            messages.add(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player damager;
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent)event.getEntity().getLastDamageCause();
        if (!(damageEvent.getDamager() instanceof Player)) {
            if (!(damageEvent.getDamager() instanceof Projectile)) return;
            Projectile projectile = (Projectile)damageEvent.getDamager();
            if (!(projectile.getShooter() instanceof Player)) return;
            damager = (Player)projectile.getShooter();
        } else {
            damager = (Player)damageEvent.getDamager();
        }
        if (damager == null || damager.equals(event.getEntity())) return;
        if (random.nextInt(100) < chance) {
            ItemStack drop = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
            SkullMeta meta = (SkullMeta)drop.getItemMeta();
            meta.setOwner(event.getEntity().getName());
            drop.setItemMeta(meta);
            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getEyeLocation(), drop);
            HeadHunterPlugin.instance.getLogger().info(event.getEntity().getName() + " dropped their head.");
            if (messages.size() > 0) {
                String message = messages.get(random.nextInt(messages.size()));
                event.getEntity().sendMessage(message);
            }
            for (Player player : event.getEntity().getWorld().getPlayers()) {
                Location loc = event.getEntity().getLocation();
                if (loc.getWorld().equals(player.getWorld()) &&
                    loc.distanceSquared(player.getLocation()) <= 32.0*32.0) {
                    player.sendMessage("" + ChatColor.DARK_RED + ChatColor.ITALIC + event.getEntity().getName() + " dropped their head.");
                }
            }
        }
    }
}
