package me.btelnyy.nochatreport.service;

import org.bukkit.configuration.file.FileConfiguration;

import me.btelnyy.nochatreport.constants.Globals;

public class ConfigLoader {
    static FileConfiguration config;
    //haha, OOP bs. Does not matter much, but its cooler to run the method "Init" over telling the computer to set a value in a static class
    public static void Init(FileConfiguration loader){
        config = loader;
    }
    public static void Load(){
        Globals.everyoneSysMessages = config.getBoolean("everyone_system_message");
        Globals.operatorsForcedToUse = config.getBoolean("operator_forced_to_use");
        Globals.operatorAutoAddOnJoin = config.getBoolean("operator_add_to_list_on_join");
        Globals.replaceMessagePermission = config.getString("replace_permission");
        Globals.permissionAutoAddOnJoin = config.getBoolean("permission_add_to_list_on_join");
    }
}
