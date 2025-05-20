package uk.rythefirst.chatter.bounties;

import org.bukkit.inventory.Inventory;

public class BHolder {
	
	private static Inventory inv;
	
	private static  GUIBuilder builder = new GUIBuilder();
	
	public static void loadInv() {
		
		inv = builder.buildBInv();
		
	}
	
	public static void reloadInv() {
		
		inv = builder.buildBInv();
		
	}
	
	public static Inventory getInv() {
		
		return inv;
		
	}

}
