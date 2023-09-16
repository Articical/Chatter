package uk.rythefirst.chatter.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import uk.rythefirst.chatter.Main;

public class Chreload implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender.hasPermission("chatter.reload")) {
			Main.configManager.loadConfig();
			sender.sendMessage(ChatColor.GOLD + "[Chatter] " + ChatColor.RESET + "Reloaded the config.yml");
		}

		return true;
	}

}
