package uk.rythefirst.chatter.liseners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import uk.rythefirst.chatter.bounties.BHolder;

public class InventoryClick implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void InvClick(InventoryClickEvent e) {
		
		Inventory iv = e.getInventory();
		
		InventoryView ivv = e.getView();
		
		if(iv == BHolder.getInv() || ivv.getTitle().contains("Bounties")) {
			e.setCancelled(true);
		}
		
	}

}
