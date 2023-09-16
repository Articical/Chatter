package uk.rythefirst.chatter.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;

public class Lmessage implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		if (!(player.hasPermission("chatter.lmessage"))) {
			return true;
		}

		if (args.length < 1) {
			player.sendMessage(ChatColor.DARK_RED + "Incorrect Usage! Avaliable commands:");
			player.sendMessage(ChatColor.GOLD + "/" + label + " set <message>");
			player.sendMessage(ChatColor.GOLD + "/" + label + " get <player>");
			player.sendMessage(ChatColor.GOLD + "/" + label + " reset");
			if (player.hasPermission("v2.mod")) {
				player.sendMessage("");
				player.sendMessage("" + ChatColor.GOLD + ChatColor.UNDERLINE + "--- Mod Commands ---");
				player.sendMessage("");
				player.sendMessage(ChatColor.GOLD + "/" + label + " clear <player>");
			}
			return true;
		}

		if (args[0].equals("clear")) {
			if (!(player.hasPermission("chatter.mod"))) {
				return true;
			}
			if (!(args.length == 2)) {
				player.sendMessage(ChatColor.DARK_RED + "Invalid usage! Use: " + label + " clear <player>");
				return true;
			}
			Player targ = Bukkit.getPlayer(args[1]);
			if (targ == null | !(Bukkit.getOnlinePlayers().contains(targ))) {
				player.sendMessage(ChatColor.DARK_RED + "Invalid player!");
				return true;
			}
			Main.LeaveMessageMgr.clearLMessage(targ.getUniqueId());
			player.sendMessage(ChatColor.GOLD + args[1] + "'s leave message is reset!");
		}

		if (args[0].equals("reset")) {
			Main.LeaveMessageMgr.clearLMessage(player.getUniqueId());
			player.sendMessage(ChatColor.GOLD + "Your leave message was reset!");
		}

		if (args[0].equals("set")) {
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				builder.append(args[i]);
				if (!(i == args.length - 1)) {
					builder.append(" ");
				}
			}

			Main.LeaveMessageMgr.setLMessage(player.getUniqueId(), builder.toString());

			player.sendMessage(ChatColor.DARK_GREEN + "Leave Message Set!");
		}

		if (args[0].equals("get")) {

			if (!(args.length == 2)) {
				player.sendMessage(ChatColor.DARK_RED + "Incorrect Usage: /lmessage get <player>");
			}

			Player targ = Bukkit.getPlayer(args[1]);

			String msg = Main.LeaveMessageMgr.getLMessage(targ.getUniqueId());

			player.sendMessage(ChatColor.DARK_RED + args[1] + "'s leave message is: " + ChatColor.RESET + msg);
		}

		return true;
	}

}
