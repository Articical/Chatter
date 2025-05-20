package uk.rythefirst.chatter.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.util.Chat;

public class Mding implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender.hasPermission("chatter.admin"))) {
			return false;
		}
		
		Chat.SendCenteredServerChat("" + ChatColor.DARK_PURPLE + sender.getName() + " dinged everyone!");
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			
			p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);
			
		}
		
		return true;
	}

}
