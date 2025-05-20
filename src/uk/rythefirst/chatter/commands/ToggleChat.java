package uk.rythefirst.chatter.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.layouts.ChatState;
import uk.rythefirst.chatter.util.Chat;

public class ToggleChat  implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			if(! sender.hasPermission("chatter.mod")) {
				return true;
			}
		}
		
		ChatState cState = Main.chatState;
		
		if(args.length == 0) {
			if(cState == ChatState.ENABLED) {
				Main.chatState = ChatState.LOCKED;
				Chat.SendCenteredServerChatAnnounce(ChatColor.DARK_RED + sender.getName() + " Locked Chat!");
			}else if (cState == ChatState.LOCKED){
				Main.chatState = ChatState.ENABLED;
				Chat.SendCenteredServerChatAnnounce(ChatColor.DARK_GREEN + sender.getName() + " Unlocked Chat!");
			}
		}else if(args.length == 1 && args[0] == "admin") {
			Main.chatState = ChatState.LOCKEDADMIN;
			Chat.SendCenteredServerChatAnnounce(ChatColor.DARK_RED + sender.getName() + " Locked Chat!");
		}
		
		return true;
	}

}
