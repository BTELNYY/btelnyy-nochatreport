package com.btelnyy.nochatreport.constants;

import com.btelnyy.nochatreport.service.file_manager.Configuration;

public class ConfigData
{
    private static ConfigData instance;


    public boolean everyoneSysMessages;
    public boolean operatorsForcedToUse;
    public boolean operatorAutoAddOnJoin;
    public boolean permissionAutoAddOnJoin;
    public boolean useAlternativeReplaceMethod;
    public String replaceMessagePermission;
    public String noIgnorePermission;
    public String showGlobalStatusPermission;
    public String changeGlobalStatusPermission;
    public String langFile;

    public static ConfigData getInstance()
    {
        return instance;
    }

    public void load(Configuration config)
    {
        instance = this;
        everyoneSysMessages = config.getBoolean("everyone_system_message");
        operatorsForcedToUse = config.getBoolean("operator_forced_to_use");
        operatorAutoAddOnJoin = config.getBoolean("operator_add_to_list_on_join");
        permissionAutoAddOnJoin = config.getBoolean("permission_add_to_list_on_join");
        noIgnorePermission = config.getString("no_ignore_permission");
        replaceMessagePermission = config.getString("replace_permission");
        showGlobalStatusPermission = config.getString("view_global_status_permission");
        changeGlobalStatusPermission = config.getString("change_global_status_permission");
        langFile = config.getString("lang_file");
        useAlternativeReplaceMethod = config.getBoolean("use_alternative_replace_method");

    }
}
