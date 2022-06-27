package me.btelnyy.nochatreport.constants;

import me.btelnyy.nochatreport.service.Configuration;

public class ConfigData {
    public boolean everyoneSysMessages;
    public boolean operatorsForcedToUse;
    public boolean operatorAutoAddOnJoin;
    public boolean permissionAutoAddOnJoin;
    public String replaceMessagePermission;

    public void load(Configuration config) {
        everyoneSysMessages = config.getBoolean("everyone_system_message");
        operatorsForcedToUse = config.getBoolean("operator_forced_to_use");
        operatorAutoAddOnJoin = config.getBoolean("operator_add_to_list_on_join");
        permissionAutoAddOnJoin = config.getBoolean("permission_add_to_list_on_join");

        replaceMessagePermission = config.getString("replace_permission");
    }

}
