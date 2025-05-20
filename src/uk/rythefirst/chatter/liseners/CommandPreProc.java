package uk.rythefirst.chatter.liseners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProc implements Listener{
	
	@EventHandler
	public void onCommandPreProccessEvent(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().equalsIgnoreCase("/pl") || e.getMessage().equalsIgnoreCase("/plugins")) {
			e.setCancelled(true);
		}
	}

}
