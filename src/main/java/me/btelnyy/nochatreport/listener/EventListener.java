package me.btelnyy.nochatreport.listener;

import me.btelnyy.nochatreport.NoChatReport;
import me.btelnyy.nochatreport.service.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    private static final String MESSAGES_SPOOFED = Utils.coloured("&8Your messages are automatically being spoofed, use /nochatreport to stop this.");

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if ((
                        NoChatReport.getInstance().getConfigData().operatorAutoAddOnJoin &&
                        event.getPlayer().isOp()
                ) || (
                        NoChatReport.getInstance().getConfigData().permissionAutoAddOnJoin &&
                        event.getPlayer().hasPermission(NoChatReport.getInstance().getConfigData().replaceMessagePermission)
                )
        ) {
            NoChatReport.getInstance().getSystemMessagePlayers().add(event.getPlayer());
            event.getPlayer().sendMessage(MESSAGES_SPOOFED);
        }
    }

    /*
        This line will make it so if another plugin cancels the message, the message won't be sent.
        Example: Muting
    */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (
                NoChatReport.getInstance().getSystemMessagePlayers().contains(event.getPlayer()) ||
                (event.getPlayer().isOp() && NoChatReport.getInstance().getConfigData().operatorsForcedToUse) ||
                event.getPlayer().hasPermission(NoChatReport.getInstance().getConfigData().replaceMessagePermission) ||
                NoChatReport.getInstance().getConfigData().everyoneSysMessages
        ) {
            replaceMessage(event);
        }
    }

    static void replaceMessage(AsyncPlayerChatEvent event){
        // Cancel the event itself, so it there won't be duplicate messages
        event.setCancelled(true);

        // Send the message to all players who were supposed to get it
        String message = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
        for (Player p : event.getRecipients()) {
            p.sendMessage(message);
        }
    }
}
