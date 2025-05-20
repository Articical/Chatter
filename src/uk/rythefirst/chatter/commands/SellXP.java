package uk.rythefirst.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.ExperienceManager;

public class SellXP implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		if(!(args.length == 1)) {
			sender.sendMessage(ChatColor.DARK_RED + "Correct usage: \"/sellxp <amount>\"");
			return false;
		}
		
		Float amount = 0f;
		
		Player p = (Player) sender;
		
		ExperienceManager expMan = new ExperienceManager(p);
		
		try {
		amount = Float.parseFloat(args[0]);
		}catch(NumberFormatException e) {
			p.sendMessage(ChatColor.DARK_RED + "Expected a number, recieved " + args[0]);
			return true;
		}
		
		float oldXP = expMan.getCurrentExp();
		
		if(amount < 0) {
			sender.sendMessage(ChatColor.DARK_RED + "I'm not giving you XP!");
			
			return false;
		}
		
		
		if(!(oldXP > amount)) {
			
			p.sendMessage(ChatColor.DARK_RED + "You don't have Enough XP!");
			p.sendMessage(ChatColor.DARK_RED + "Your XP: " + oldXP);
			return true;
			
		}
		
		int roundedxp = Math.round(amount);
		
		Boolean divisible = roundedxp % 10 == 0;
		
		if(!(divisible)) {
			
			p.sendMessage(ChatColor.DARK_RED + "Value must be a multiple of 10!");
			//p.sendMessage(""+roundedxp);
			return true;
			
		}
		
		
		
		int count = (int) (amount/10);
		Double Moneyval = 0d;
		for(int i=0;i<count;i++) {
			Moneyval = Moneyval + Main.xpValue;
		}
		
		int oldIntXP = Math.round(oldXP);
		int amountInt = Math.round(amount);
		
		int newEXP = oldIntXP - amountInt;
		
		expMan.setExp(newEXP);
		
		Main.getEconomy().depositPlayer(p, Moneyval);
		
		p.sendMessage(ChatColor.GOLD + "You traded " + amount + "XP for $" + Moneyval + "!");
		
		return true;
	}
	
	

}
