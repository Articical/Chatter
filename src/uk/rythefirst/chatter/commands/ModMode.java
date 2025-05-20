package uk.rythefirst.chatter.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;

public class ModMode implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			return false;
		}
		
		Player p = (Player) sender;
		
		if(p.hasPermission("chatter.mod")) {
			if(!Main.hManager.isHidden(p.getUniqueId())) {
				Main.hManager.hide(p);
				
				p.setFlying(true);
				
				p.setAllowFlight(true);
				
				p.setCanPickupItems(false);
				
				p.setCollidable(false);
				
				p.setInvulnerable(true);
				
				p.setGameMode(GameMode.SPECTATOR);
				
				p.performCommand("v");
				
				if(Bukkit.getServer().getOnlinePlayers().size() == 1) {
					return true;
				}
				
				for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
					if(pl.hasPermission("chatter.mod") && !(pl == p)) {
						pl.sendMessage(ChatColor.DARK_BLUE + p.getName() + " has entered mod mode!");
					}else {
						pl.sendMessage(Main.LeaveMessageMgr.getLMessage(p.getUniqueId()));
					}
				}
				
			}else {
				Main.hManager.unHide(p);
				
				p.setFlying(false);
				
				p.setAllowFlight(false);
				
				p.setCanPickupItems(true);
				
				p.setCollidable(true);
				
				p.setInvulnerable(false);
				
				p.setGameMode(GameMode.SURVIVAL);
				
				p.performCommand("v");
				
				p.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "ModMode disabled! You are now visible again!");
				
				for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
					if(pl.hasPermission("chatter.mod") && !(pl == p)) {
						pl.sendMessage(ChatColor.DARK_BLUE + p.getName() + " has exited mod mode!");
					}else {
						pl.sendMessage(Main.JoinMsgManager.getJMessage(p.getUniqueId()));
					}
				}
			}
		}
		
		return true;
	}

}
