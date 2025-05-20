package uk.rythefirst.chatter.util;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Chat {

	public static String prefixFormatted = ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "NESS" + ChatColor.GOLD + "] ";

	public static void SendCenteredServerChat(String message) {

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {

			Messages.sendCenteredMessage(p, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
			Messages.sendCenteredMessage(p, "");
			Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + message);
			Messages.sendCenteredMessage(p, "");

		}

	}

	public static void SendCenteredServerChatAnnounce(String message) {

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {

			Messages.sendCenteredMessage(p, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
			Messages.sendCenteredMessage(p, "");
			Messages.sendCenteredMessage(p, ChatColor.DARK_PURPLE + message);
			Messages.sendCenteredMessage(p, "");

			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 1f, 1f);

		}

	}

}
