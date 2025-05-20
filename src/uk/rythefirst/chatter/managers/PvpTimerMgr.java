package uk.rythefirst.chatter.managers;

import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PvpTimerMgr {
	
	private TreeMap<UUID, Long> playerLastHit = new TreeMap<UUID, Long>();
	
	public Long getLastHitEpoch(Player p) {
		if(!(playerLastHit.containsKey(p.getUniqueId()))){
			return null;
		}
		return playerLastHit.get(p.getUniqueId());
	}
	
	public boolean setLastHitEpoch(Player p) {
		if(playerLastHit.containsKey(p.getUniqueId())) {
			playerLastHit.remove(p.getUniqueId());
		}
		playerLastHit.put(p.getUniqueId(), java.time.Instant.now().toEpochMilli());
		return true;
	}
	
	public Boolean milisSinceLastHitGreater(Player p, Long greaterThan) {

		Long newEpoch = java.time.Instant.now().toEpochMilli();
		
		Long oldEpoch = getLastHitEpoch(p);
		
		if(oldEpoch != null) {
			
		}
		
		return true;
		
	}

}
