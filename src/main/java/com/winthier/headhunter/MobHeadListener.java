package com.winthier.headhunter;

import com.winthier.exploits.bukkit.BukkitExploits;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

class MobHeadListener implements Listener {
    final Map<UUID, PlayerData> playerData = new HashMap<>();
    final Random random = new Random(System.currentTimeMillis());
    int chance = 5000;
    static MobHeadListener instance = null;

    MobHeadListener() {
        instance = this;
    }

    void load() {
        chance = HeadHunterPlugin.instance.getConfig().getInt("MobChance", 10000);
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();
        if (entity.getCustomName() != null) return;
        final Player killer = entity.getKiller();
        if (killer == null) return;
        PlayerData data = getPlayerData(killer);
        if (entity instanceof Monster && BukkitExploits.getInstance().recentKillDistance(killer, entity.getLocation(), 60) < 8) return;
        int score = data.getScore(entity.getType());
        score += 1;
        if (random.nextInt(chance) < score) {
            data.setScore(entity.getType(), 0);
            final ItemStack head = getMobHead(entity);
            if (head != null) {
                for (ItemStack item: killer.getInventory().addItem(head).values()) {
                    killer.getWorld().dropItem(killer.getEyeLocation(), item).setPickupDelay(0);
                }
                SkullMeta meta = (SkullMeta)head.getItemMeta();
                String name = null;
                if (meta.hasDisplayName()) {
                    name = meta.getDisplayName();
                } else if (meta.hasOwner()) {
                    name = meta.getOwner();
                } else {
                    name = "a head";
                }
                killer.sendMessage("The mob drops " + ChatColor.GREEN + name + ChatColor.RESET + ".");
                HeadHunterPlugin.instance.getLogger().info(killer.getName() + " got a " + entity.getType() + " head: " + name);
            }
        } else {
            data.setScore(entity.getType(), score);
        }
    }

    PlayerData getPlayerData(Player player) {
        PlayerData result = playerData.get(player.getUniqueId());
        if (result == null) {
            result = new PlayerData();
            playerData.put(player.getUniqueId(), result);
        }
        return result;
    }

    ItemStack getMobHead(LivingEntity entity) {
        boolean valid = false;
        short damage = 0;
        String owner = null;
        String name = null;
        switch (entity.getType()) {
        case SKELETON:
            final Skeleton skeleton = (Skeleton)entity;
            if (skeleton.getSkeletonType() == Skeleton.SkeletonType.WITHER) {
                damage = (short)3;
                owner = "MHF_WSkeleton";
                name = "Decorative Wither Skeleton Head";
            } else {
                damage = (short)0;
            }
            valid = true;
            break;
        case ZOMBIE:
            final Zombie zombie = (Zombie)entity;
            if (zombie.isBaby()) return null;
            if (zombie.isVillager()) {
                damage = (short)3;
                owner = "MHF_Villager";
                name = "Villager Head";
                valid = true;
            } else {
                damage = (short)2;
                valid = true;
            }
            break;
        case CREEPER:
            damage = (short)4;
            valid = true;
            break;
        case BLAZE:
            damage = (short)3;
            owner = "MHF_Blaze";
            name = "Blaze Head";
            valid = true;
            break;
        case CAVE_SPIDER:
            damage = (short)3;
            owner = "MHF_CaveSpider";
            name = "Cave Spider Head";
            valid = true;
            break;
        case CHICKEN:
            damage = (short)3;
            owner = "MHF_Chicken";
            name = "Chicken Head";
            valid = true;
            break;
        case COW:
            damage = (short)3;
            owner = "MHF_Cow";
            name = "Cow Head";
            valid = true;
            break;
        case ENDERMAN:
            damage = (short)3;
            owner = "MHF_Enderman";
            name = "Enderman Head";
            valid = true;
            break;
        case GHAST:
            damage = (short)3;
            owner = "MHF_Ghast";
            name = "Ghast Head";
            valid = true;
            break;
        case IRON_GOLEM:
            damage = (short)3;
            owner = "MHF_Golem";
            name = "Iron Golem Head";
            valid = true;
            break;
        case MAGMA_CUBE:
            damage = (short)3;
            owner = "MHF_LavaSlime";
            name = "Magma Cube Head";
            valid = true;
            break;
        case MUSHROOM_COW:
            damage = (short)3;
            owner = "MHF_MushroomCow";
            name = "Mushroom Cow Head";
            valid = true;
            break;
        case OCELOT:
            damage = (short)3;
            owner = "MHF_Ocelot";
            name = "Ocelot Head";
            valid = true;
            break;
        case WOLF:
            damage = (short)3;
            owner = "MHF_Wolf";
            name = "Wolf Head";
            valid = true;
            break;
        case PIG:
            damage = (short)3;
            owner = "MHF_Pig";
            name = "Pig Head";
            valid = true;
            break;
        case PIG_ZOMBIE:
            damage = (short)3;
            owner = "MHF_PigZombie";
            name = "Pig Zombie Head";
            valid = true;
            break;
        case SHEEP:
            damage = (short)3;
            owner = "MHF_Sheep";
            name = "Sheep Head";
            valid = true;
            break;
        case SLIME:
            damage = (short)3;
            owner = "MHF_Slime";
            name = "Slime Head";
            valid = true;
            break;
        case SPIDER:
            damage = (short)3;
            owner = "MHF_Spider";
            name = "Spider Head";
            valid = true;
            break;
        case SQUID:
            damage = (short)3;
            owner = "MHF_Squid";
            name = "Squid Head";
            valid = true;
            break;
        case VILLAGER:
            damage = (short)3;
            owner = "MHF_Villager";
            name = "Villager Head";
            valid = true;
            break;
        case GUARDIAN:
            damage = (short)3;
            owner = "MHF_Guardian";
            name = "Guardian Head";
            valid = true;
            break;
        case RABBIT:
            damage = (short)3;
            owner = "MHF_Rabbit";
            name = "Rabbit Head";
            valid = true;
            break;
        case SNOWMAN:
            damage = (short)3;
            owner = "MHF_SnowGolem";
            name = "Snow Golem Head";
            valid = true;
            break;
        case WITCH:
            damage = (short)3;
            owner = "MHF_Witch";
            name = "Witch Head";
            valid = true;
            break;
        case ENDER_DRAGON:
            damage = (short)3;
            owner = "MHF_EnderDragon";
            name = "Ender Dragon Head";
            valid = true;
            break;
        case WITHER:
            damage = (short)3;
            owner = "MHF_Wither";
            name = "Wither Head";
            valid = true;
            break;
        case SHULKER:
            damage = (short)3;
            owner = "MHF_Shulker";
            name = "Shulker Head";
            valid = true;
            break;
        }
        if (!valid) return null;
        final ItemStack result = new ItemStack(Material.SKULL_ITEM, 1, damage);
        if (damage == (short)3) {
            if (owner == null) return null;
            final SkullMeta meta = (SkullMeta)result.getItemMeta();
            meta.setOwner(owner);
            if (name != null) {
                meta.setDisplayName(ChatColor.RESET + name);
            }
            result.setItemMeta(meta);
        }
        return result;
    }
}

class PlayerData {
    Map<EntityType, Integer> entityScore = new EnumMap<>(EntityType.class);
    PlayerData() {
        for (EntityType type: EntityType.values()) entityScore.put(type, 0);
    }
    int getScore(EntityType type) {
        return entityScore.get(type);
    }
    void setScore(EntityType type, int score) {
        entityScore.put(type, score);
    }
}
