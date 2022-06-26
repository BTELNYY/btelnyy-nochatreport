package me.btelnyy.nochatreport.service;

import org.bukkit.configuration.file.FileConfiguration;

import me.btelnyy.nochatreport.constants.Globals;

public class ConfigLoader {
    static FileConfiguration config;
    public static void Init(FileConfiguration loader){
        config = loader;
    }
    public static void Load(){
        Globals.everyoneSysMessages = config.getBoolean("everyone_system_message");
        Globals.operatorsForcedToUse = config.getBoolean("operator_forced_to_use");
    }
}
