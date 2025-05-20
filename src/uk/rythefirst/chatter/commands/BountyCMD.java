package uk.rythefirst.chatter.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import uk.rythefirst.chatter.bounties.BHolder;

public class BountyCMD implements CommandExecutor{
	
	public static HashMap<Player, Inventory> binvp = new HashMap<Player, Inventory>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player p = (Player) sender;
		
		Inventory bInv = BHolder.getInv();
		
		p.openInventory(bInv);
		
		return true;
	}

}
