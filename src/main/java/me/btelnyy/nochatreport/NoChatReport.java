package me.btelnyy.nochatreport;

import me.btelnyy.nochatreport.commands.CommandMe;
import me.btelnyy.nochatreport.commands.CommandMsg;
import me.btelnyy.nochatreport.commands.CommandToggleMessages;
import me.btelnyy.nochatreport.constants.ConfigData;
import me.btelnyy.nochatreport.listener.EventListener;
import me.btelnyy.nochatreport.service.file_manager.Configuration;
import me.btelnyy.nochatreport.service.file_manager.FileID;
import me.btelnyy.nochatreport.service.file_manager.FileManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class NoChatReport extends JavaPlugin {
    // An instance of the plugin, so we don't need to make everything static
    private static NoChatReport instance;

    private Configuration config;
    private ConfigData configData;
    private FileManager fileManager;

    private final LinkedList<UUID> systemMessagePlayers = new LinkedList<>();

    @Override
    public void onEnable() {
        // Self-explanatory
        instance = this;

        // Generate files
        fileManager = new FileManager(this);
        fileManager.addFile(FileID.LANGUAGE, fileManager.create(null, "language.yml"));

        // Load config
        saveDefaultConfig();
        {
            // Load it into a more user-friendly object
            config = new Configuration(getConfig());

            // Cache it
            configData = new ConfigData();
            configData.load(config);
        }

        // Register commands
        registerCommandExecutor("nochatreport", new CommandToggleMessages());
        registerCommandExecutor("msg", new CommandMsg());
        registerCommandExecutor("me", new CommandMe());

        // Register events
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        // GitHub message
        getLogger().info("Check out the project on GitHub! https://github.com/BTELNYY/btelnyy-nochatreport");
    }
    public void log(Level level, Object message){
        getLogger().log(level, message.toString());
    }

    private void registerCommandExecutor(String commandName, CommandExecutor commandExecutor) {
        PluginCommand command = getCommand(commandName);

        /*
        If the command is null java will trigger an error any ways, the goal with this is to
        not trigger the error, so calling an explicit NullPointerError does not make things any better
         */
        if (command == null) {
            getLogger().severe("The command " + commandName + " could not be registered, please contact the plugin authors " + getDescription().getAuthors());
            return;
        }
        command.setExecutor(commandExecutor);
    }

    public static NoChatReport getInstance() {
        return instance;
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public List<UUID> getSystemMessagePlayers() {
        return systemMessagePlayers;
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
