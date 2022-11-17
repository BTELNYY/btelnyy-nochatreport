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
    //map is <user, people who ignored this user>
    //why did I do this thing this way? Dont know. Good luck figuring it out.
    public static HashMap<String, List<String>> IgnoredPlayers = new HashMap<String, List<String>>();
    //for reply command
    public static HashMap<Player, Player> ReplyMap = new HashMap<Player, Player>();
    //this one should not change, its effectively legacy code, but it works perfectly
    public final static String Path = NoChatReport.getInstance().getDataFolder().toString() + "/PlayerData/";
    //toggle the whole plugin with one command
    public static Boolean pluginToggle = true;
}