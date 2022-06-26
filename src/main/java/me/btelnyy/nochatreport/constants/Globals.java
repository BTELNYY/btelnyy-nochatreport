package me.btelnyy.nochatreport.constants;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Globals {
    public static List<Player> sysMsgPlayers = new ArrayList<Player>();

    //config options
    public static boolean everyoneSysMessages = false;
    public static boolean operatorsForcedToUse = false;
    public static boolean operatorAutoAddOnJoin = true;
    public static String replaceMessagePermission = "btelnyy.replacemessages";
    public static boolean permissionAutoAddOnJoin = true;


    public static void AddPlayerToList(Player p){
        if(sysMsgPlayers.contains(p)){
            return;
        }
        sysMsgPlayers.add(p);
    }
}