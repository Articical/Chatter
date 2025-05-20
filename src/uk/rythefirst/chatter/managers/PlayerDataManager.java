package uk.rythefirst.chatter.managers;

import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;

public class PlayerDataManager {
	
	public void createIfNotExists(Player p) {
		if(Main.PDataMgr.fileExists(p.getUniqueId())) {
			return;
		}else {
			Main.PDataMgr.createFileAsync(p.getUniqueId(), null, null);
		}
	}

}
