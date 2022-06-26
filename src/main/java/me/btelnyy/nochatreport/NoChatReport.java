package me.btelnyy.nochatreport;
import me.btelnyy.nochatreport.commands.*;
import me.btelnyy.nochatreport.listener.EventListener;
import me.btelnyy.nochatreport.service.ConfigLoader;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class NoChatReport extends JavaPlugin {

    @Override
    public void onEnable() {
        // config
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                saveDefaultConfig();
            } catch (Exception e) {
                getLogger().log(Level.SEVERE, "Config.yml could not be created. Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        FileConfiguration c = getConfig();
        ConfigLoader.Init(c);
        ConfigLoader.Load();
        registerCommandExecutor("nochatreport", new CommandToggleMessages());
        registerCommandExecutor("msg", new CommandMsg());
        //event handle
        getServer().getPluginManager().registerEvents(new EventListener(), this);
        //github
        getLogger().log(Level.INFO, "Check out the project on GitHub!: https://github.com/BTELNYY/btelnyy-nochatreport");
    }

    private void registerCommandExecutor(String commandName, CommandExecutor commandExecutor) {
        PluginCommand command = this.getCommand(commandName);
        if (command == null)
            throw new NullPointerException(String.format("\"%s\" is not registered in the plugin.yml", commandName));
        command.setExecutor(commandExecutor);
    }
}
