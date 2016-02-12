package com.winthier.headhunter;

import org.bukkit.plugin.java.JavaPlugin;

public class HeadHunterPlugin extends JavaPlugin {
    static HeadHunterPlugin instance = null;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerHeadListener(), this);
        getServer().getPluginManager().registerEvents(new MobHeadListener(), this);
        getCommand("headhunter").setExecutor(new HeadHunterCommand());
        load();
    }

    void load() {
        reloadConfig();
        PlayerHeadListener.instance.load();
        MobHeadListener.instance.load();
    }
}
