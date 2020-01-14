package com.winthier.headhunter;

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
}
