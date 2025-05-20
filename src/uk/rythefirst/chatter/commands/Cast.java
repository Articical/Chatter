package uk.rythefirst.chatter.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import uk.rythefirst.chatter.util.Chat;

public class Cast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender.hasPermission("chatter.admin"))) {
			return false;
		}

		StringBuilder sb = new StringBuilder();

		if (args.length == 0) {
			return false;
		}

		for (int i = 0; i < args.length; i++) {
			sb.append(args[i] + " ");
		}

		Chat.SendCenteredServerChatAnnounce(sb.toString());

		return true;
	}

}
