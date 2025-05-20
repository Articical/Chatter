package uk.rythefirst.chatter.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.util.Chat;

public class ClearChat implements CommandExecutor {
	
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("chatter.mod")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        for (int i = 0; i < 100; i++) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("");
            }
        }

        String clearedBy = (sender instanceof Player) ? sender.getName() : "Console";
        Chat.SendCenteredServerChatAnnounce(ChatColor.GRAY + "Chat has been cleared by " + ChatColor.YELLOW + clearedBy + ChatColor.GRAY + ".");

        return true;
    }
}