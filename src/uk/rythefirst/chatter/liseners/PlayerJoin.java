package uk.rythefirst.chatter.liseners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.Main;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Main.NickMgr.setVisibleName(e.getPlayer());
		
		if(!(e.getPlayer().hasPlayedBefore())) {
			e.setJoinMessage("" + ChatColor.GOLD + ChatColor.BOLD + "Welcome " + ChatColor.RED + ChatColor.BOLD + e.getPlayer().getName() + ChatColor.GOLD + ChatColor.BOLD + " to Mine-Play!");
			return;
		}
		
		if (!(Main.JoinMsgManager.hasJMessage(e.getPlayer().getUniqueId()))) {
			e.setJoinMessage(Main.JoinMsgManager.getDefaultJoin(e.getPlayer().getUniqueId()));
		} else {
			e.setJoinMessage(Main.JoinMsgManager.getJMessage(e.getPlayer().getUniqueId()));
		}
		
		
		
	}

}
