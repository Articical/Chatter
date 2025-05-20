package uk.rythefirst.chatter.commands;

import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.twitch.TwitchAPI;
import uk.rythefirst.chatter.twitch.TwitchInfo;
import uk.rythefirst.chatter.util.Messages;

public class Twitch implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		TwitchInfo tinfo = TwitchAPI.info;
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player p = (Player) sender;
		
		if(args.length == 0) {
			
			if(tinfo.isLive() == true) {
			
				Messages.sendCenteredMessage(p, "");
				Messages.sendCenteredMessage(p, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
				Messages.sendCenteredMessage(p, "");
				Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "AoiSakura is live!");
				Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "@ https://www.twitch.tv/aoisakura");
				Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "Playing: " + tinfo.getGame());
				Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "With " + tinfo.getViewerCount() + " Viewers!");
				Messages.sendCenteredMessage(p, "");
			
			}else {
				
				Messages.sendCenteredMessage(p, "");
				Messages.sendCenteredMessage(p, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
				Messages.sendCenteredMessage(p, "");
				Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "AoiSakura is currently offline!");
				Messages.sendCenteredMessage(p, "");
				
			}
			
		}else if(args.length == 1) {
			
			try {
				
				TwitchInfo twinfo = TwitchAPI.isLive(args[0]);
				
				if(twinfo.isLive()) {
					
					Messages.sendCenteredMessage(p, "");
					Messages.sendCenteredMessage(p, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
					Messages.sendCenteredMessage(p, "");
					Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + args[0] + " is live!");
					Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "@ https://www.twitch.tv/" + args[0]);
					Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "Playing: " + twinfo.getGame());
					Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + "With " + twinfo.getViewerCount() + " Viewers!");
					Messages.sendCenteredMessage(p, "");
					
				}else {
					
					Messages.sendCenteredMessage(p, "");
					Messages.sendCenteredMessage(p, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
					Messages.sendCenteredMessage(p, "");
					Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + args[0] + " is currently offline!");
					Messages.sendCenteredMessage(p, "");
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
		
	}
	
}
