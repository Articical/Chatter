package uk.rythefirst.chatter.layouts;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;

public class PlayerBounty {
	
	private OfflinePlayer p;
	private OfflinePlayer setter;
	private Boolean isset;
	private double amount;
	
	public PlayerBounty(OfflinePlayer player,Boolean set,@Nullable double value,@Nullable OfflinePlayer whoset) {
		
		setter = whoset;
		amount = value;
		p = player;
		isset = set;
		
	}
	
	public OfflinePlayer getPlayer() {
		return p;
	}
	
	public OfflinePlayer getWhoSet() {
		return setter;
	}
	
	public Double getValue() {
		return amount;
	}
	
	public Boolean isSet() {
		return isset;
	}

}
