package uk.rythefirst.chatter.commands;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;

public class Nick implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		if (!(player.hasPermission("chatter.nick"))) {
			return true;
		}

		if (args.length == 0) {
			player.sendMessage(ChatColor.DARK_RED + "Icorrect Usage! The following commands exist:");
			player.sendMessage(ChatColor.GOLD + "/" + label + " set <nickname>");
			player.sendMessage(ChatColor.GOLD + "/" + label + " reset");
			player.sendMessage(ChatColor.GOLD + "/" + label + " who <player>");
			player.sendMessage(ChatColor.GOLD + "/" + label + " list");
			if (player.hasPermission("chatter.mod")) {
				player.sendMessage("");
				player.sendMessage("" + ChatColor.GOLD + ChatColor.UNDERLINE + "--- Mod Commands ---");
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD + "/" + label + " clear <player>");
			}
			return false;
		}

		if (args[0].equalsIgnoreCase("set")) {
			if (!(args.length > 1)) {
				player.sendMessage(ChatColor.DARK_RED + "Incorrect Usage!");
				player.sendMessage(ChatColor.GOLD + "/" + label + " set <nickname>");
				return true;
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				sb.append(args[i]);
				if (!(i == args.length)) {
					sb.append(" ");
				}
			}
			Main.NickMgr.setNickName(player, sb.toString());
		} 
		else if (args[0].equalsIgnoreCase("clear")) {
			if (!(player.hasPermission("chatter.mod"))) {
				return true;
			}
			if (args.length == 2) {
				Main.NickMgr.clearNickName(Bukkit.getPlayer(args[1]));
				player.sendMessage(ChatColor.GREEN + "Cleared " + args[1] + "'s Nick");
			}
		} 
		else if (args[0].equalsIgnoreCase("reset")) {
			if (args.length == 1) {
				Main.NickMgr.clearNickName(player);
				player.sendMessage(ChatColor.GREEN + "Cleared your Nick");
			}
		} 
		else if (args[0].equalsIgnoreCase("who")) {
			if (args.length == 2) {
				if (!(Main.NickMgr.getNickName(Bukkit.getPlayer(args[1])) == null)) {
					String nick = Main.NickMgr.getNickName(Bukkit.getPlayer(args[1]));
					player.sendMessage(ChatColor.GREEN + args[1] + "'s Nickname is "
							+ ChatColor.translateAlternateColorCodes('&', nick));
				} else {
					player.sendMessage(ChatColor.GREEN + args[1] + " hasn't got a nickname!");
				}

			}
		} 
		else if (args[0].equalsIgnoreCase("list")) {
			if (args.length == 1) {
				TreeMap<String, String> mapc = Main.cache.nickMap;
				for (Entry<String, String> entry : mapc.entrySet()) {
					if (Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).isOnline()) {
						player.sendMessage(
								ChatColor.GOLD + Bukkit.getOfflinePlayer(UUID.fromString(entry.getKey())).getName()
										+ ChatColor.DARK_RED + " -> " + ChatColor.RESET
										+ ChatColor.translateAlternateColorCodes('&', entry.getValue()));
					}
				}
			}
		}

		return true;
	}

}
