package uk.rythefirst.chatter.liseners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import uk.rythefirst.chatter.Main;

public class PlayerLeave implements Listener {

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(Main.LeaveMessageMgr.getLMessage(e.getPlayer().getUniqueId()));
	}

}
