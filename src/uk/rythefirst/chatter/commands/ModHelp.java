package uk.rythefirst.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.util.Messages;

public class ModHelp implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,  String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player p = (Player) sender;
		if(p.hasPermission("chatter.mod")) {
			Messages.sendCenteredMessage(p,ChatColor.GOLD + "----- Moderator Help -----");
			p.sendMessage(ChatColor.GOLD + "/modmode - Vanish, for use during staff work.");
			p.sendMessage(ChatColor.GOLD + "/co inspect - CoreProtect block inspector.");
			p.sendMessage(ChatColor.GOLD + "/co rollback r:<block radius> t:<time> - Roll back the area around you by the <time> specified (ex. 5m for 5 mins)");
			p.sendMessage(ChatColor.GOLD + "/back - returns you to your last location.");
			p.sendMessage(ChatColor.GOLD + "/socialspy - Enable social spy (see messages between other players)");
			p.sendMessage(ChatColor.GOLD + "/lands admin chatspy * - Enable lands chat spy.");
			p.sendMessage(ChatColor.GOLD + "/tp <user> - force teleport to a user. (only permitted when on duty)");
			p.sendMessage(ChatColor.GOLD + "/punish <user> - Ban|Kick|Mute GUI");
			p.sendMessage(ChatColor.GOLD + "/clearchat - Clears the chat..");
			p.sendMessage(ChatColor.GOLD + "/lockdown - Locks the server down (only use this if something game-breaking occurs)");
			p.sendMessage(ChatColor.GOLD + "/togglechat - Toggle the chat on or off.");
			p.sendMessage(ChatColor.GOLD + "/(checkban|checkmute) <player> - Check if a player is banned or muted.");
			p.sendMessage(ChatColor.GOLD + "/history <player> - Check a users punishment history.");
			p.sendMessage(ChatColor.GOLD + "/invsee <player> - View a users inventory.");
			Messages.sendCenteredMessage(p,ChatColor.GOLD + "----- End of Help -----");
			return true;
		}
		return false;
	}

}
