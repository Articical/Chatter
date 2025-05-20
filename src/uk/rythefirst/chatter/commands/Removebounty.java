package uk.rythefirst.chatter.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.bounties.DataHandler;

public class Removebounty implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.hasPermission("bounties.staff"))) {
			
			return false;
			
		}
		
		if(!(Bukkit.getPlayer(args[0]) == null)) {
			
			Player targ = Bukkit.getPlayer(args[0]);
			
			if(DataHandler.hasBounty(targ.getUniqueId())){
				
				DataHandler.RemoveBounty(targ);
				
				sender.sendMessage("Removed bounty from " + targ.getName());
				
			}
			
		}else if(Bukkit.getOfflinePlayer(args[0]) != null) {
			
			OfflinePlayer targ = Bukkit.getOfflinePlayer(args[0]);
			
			if(targ.getUniqueId() != null) {
				
				if(DataHandler.hasBounty(targ.getUniqueId())) {
					
					DataHandler.RemoveBounty(targ);
					
					sender.sendMessage("Removed bounty from " + targ.getName());
					
				}
				
			}else {
				
				sender.sendMessage("Invalid player");
				
			}
			
		}
			
		return true;
	}
	
	

}
