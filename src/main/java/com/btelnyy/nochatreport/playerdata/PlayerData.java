package com.btelnyy.nochatreport.playerdata;

import java.util.ArrayList;
import java.util.List;

public class PlayerData
{
    public String PlayerUuid = "";
    public List<String> ignoredUUIDs = new ArrayList<String>();
    //not a mute, it just doesnt send you chat
    public boolean NoChat = false;

    public String getUniqueId()
    {
        return this.PlayerUuid;
    }
}
