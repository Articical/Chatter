package uk.rythefirst.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.bounties.DataHandler;

public class BountiesSave implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		if(sender.hasPermission("bounties.staff")) {
		
			DataHandler.saveBounties(false);
		
			sender.sendMessage(ChatColor.DARK_RED + "Bounties saved!");
			
		}
		
		return true;
		
	}

}
