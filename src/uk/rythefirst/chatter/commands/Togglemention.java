package uk.rythefirst.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.managers.MentionHandler;

public class Togglemention implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			
			return false;
			
		}
		
		Player p = (Player) sender;
		
		int res = MentionHandler.togglemute(p);
		
		if(res == 1) {
			
			p.sendMessage(ChatColor.GREEN + "Mentions are now enabled!");
			
		}else if(res == 2) {
			
			p.sendMessage(ChatColor.RED + "Mentions are now disabled!");
			
		}
		
		return true;
	}

}
