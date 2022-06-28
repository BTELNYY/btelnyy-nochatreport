package me.btelnyy.nochatreport.constants;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;


import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.playerdata.PlayerData;

public class Globals {
    //to reduce disk operations
    public static HashMap<String, PlayerData> CachedPlayers = new HashMap<String, PlayerData>();
    //for optimizations
    public static HashMap<Player, List<Player>> IgnoredPlayers = new HashMap<Player, List<Player>>();
    //this one should not change, its effectively legacy code, but it works perfectly
    public final static String Path = NoChatReport.getInstance().getDataFolder().toString() + "PlayerData";
}
