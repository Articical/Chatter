package uk.rythefirst.chatter.layouts;

import org.bukkit.OfflinePlayer;

public class Bounty {
	
	private OfflinePlayer p;
	private OfflinePlayer set;
	private Double val;
	
	public Bounty(OfflinePlayer player,OfflinePlayer setter,Double value) {
		p = player;
		set = setter;
		val = value;
	}
	
	public OfflinePlayer getTarget() {
		return p;
	}
	
	public OfflinePlayer setBy() {
		return set;
	}
	
	public Double getValue() {
		return val;
	}

}
