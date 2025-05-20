package uk.rythefirst.chatter.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.bounties.BHolder;
import uk.rythefirst.chatter.bounties.DataHandler;
import uk.rythefirst.chatter.util.Chat;

public class SetBounty implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		Economy eco = Main.getEconomy();
		
		Double amount = 0d;
		
		Player targ;
		
		if(!(sender instanceof Player)) {
			
			return true;
			
		}
		
		if(!(args.length == 2)) {
			
			sender.sendMessage(ChatColor.DARK_RED + "Correct usage: /setbounty <player> <amount>");
			
			return true;
			
		}
		
		try {
			
			amount = Double.parseDouble(args[1]);
			
		}catch(Exception e){
			
			sender.sendMessage(ChatColor.DARK_RED + "Correct usage: /setbounty <player> <amount>");
			
			return true;
			
		}
		
		try {
			
			targ = Bukkit.getPlayer(args[0]);
			
		}catch(Exception e) {
			sender.sendMessage(ChatColor.DARK_RED + "Invalid player!");
			
			return true;
		}
		
		Player p = (Player) sender;
		
		if(eco.getBalance(p) < amount) {
			
			sender.sendMessage(ChatColor.DARK_RED + "You don't have enough money, stupid.");
			
			return true;
			
		}else {
			
			if(targ == null) {
				
				sender.sendMessage(ChatColor.DARK_RED + "Invalid player!");
				
				return true;
			}
			
			if(DataHandler.hasBounty(targ.getUniqueId())) {
				
				sender.sendMessage(ChatColor.DARK_RED + "Player already has a bounty!");
				
				return true;
				
			}
			
			if(targ.getUniqueId() == p.getUniqueId()) {
				
				sender.sendMessage(ChatColor.DARK_PURPLE + "Can't set a bounty on yourself, stupid.");
				
				return true;
				
			}
			
			if(amount < 1000) {
				
				sender.sendMessage(ChatColor.DARK_PURPLE + "Minimum bounty: $1000");
				
				return true;
				
			}
			
			eco.withdrawPlayer(p, amount);
			
			DataHandler.setBounty(targ, p, amount);
			
			BHolder.reloadInv();
			
			Chat.SendCenteredServerChat(p.getName() + " set a bounty of $" + amount.toString() + " on " + targ.getName());
			
		}
		
		return true;
		
	}

}
