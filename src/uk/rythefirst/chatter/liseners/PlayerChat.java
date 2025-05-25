package uk.rythefirst.chatter.liseners;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.layouts.ChatState;
import uk.rythefirst.chatter.managers.MentionHandler;
import uk.rythefirst.chatter.speedtyper.ChatWord;
import uk.rythefirst.chatter.util.Messages;
import uk.rythefirst.chatter.util.Perms;
import uk.rythefirst.chatter.util.Tools;

public class PlayerChat implements Listener {
	
	public HashMap<Player,Long> pmap = new HashMap<Player,Long>();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {
		
		if(Main.chatState == ChatState.LOCKED && !e.getPlayer().hasPermission("chatter.mod")) {
			e.getPlayer().sendMessage(ChatColor.DARK_RED + "Chat Is Currently Locked!");
			e.setCancelled(true);
		}
		
		if(Main.chatState == ChatState.LOCKEDADMIN && !e.getPlayer().hasPermission("chatter.admin")) {
			e.getPlayer().sendMessage(ChatColor.DARK_RED + "Chat Is Currently Locked!");
			e.setCancelled(true);
		}
		
		if (Main.cwg.isOnGoing()) {
			ChatWord word = Main.cwg.getWord();

			if (e.getMessage().equalsIgnoreCase(word.getWord())) {
				word.setGuessed(true);
				// Calculate scaled reward
		        int playerCount = Tools.getPlayerCountNoStaff();
		        double baseReward = 200.0;
		        double totalReward = baseReward * playerCount;
				Main.cwg.setOnGoing(false);
				new BukkitRunnable() {
					@Override
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers()) {
							p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1f, 1f);
							Messages.sendCenteredMessage(p, "" + ChatColor.GOLD + ChatColor.UNDERLINE + "Speed Typer");
							Messages.sendCenteredMessage(p, ChatColor.GOLD + "Game over!");
							Messages.sendCenteredMessage(p,
									ChatColor.GOLD + e.getPlayer().getDisplayName() + " typed the word first!");
							if (p == e.getPlayer()) {
								Messages.sendCenteredMessage(p, ChatColor.GOLD + "You won $" + totalReward);
								Main.getEconomy().depositPlayer(e.getPlayer(), totalReward);
							} else {
								Messages.sendCenteredMessage(p, "");
							}
						}
					}
				}.runTaskLater(Main.instance, 2);

				Bukkit.getLogger().log(Level.INFO, "[Chatter] " + e.getPlayer().getName() + " Guessed the word!");
			}

		}
		
		String cFormat = Main.cache.format;
		cFormat = ChatColor.translateAlternateColorCodes('&', cFormat);
		cFormat = cFormat.replace("<name>", e.getPlayer().getName());

		cFormat = cFormat.replace("<displayname>",
				ChatColor.translateAlternateColorCodes('&', e.getPlayer().getDisplayName()));
		cFormat = cFormat.replace("<nick>",
				ChatColor.translateAlternateColorCodes('&', Main.NickMgr.getNickName(e.getPlayer())));

		User user = Perms.loadUser(e.getPlayer());

		CachedMetaData metaData = user.getCachedData().getMetaData();
		String prefix = "";
		if (!(metaData.getPrefix() == null)) {
			prefix = metaData.getPrefix();
		}
		cFormat = cFormat.replace("<lp_prefix>", ChatColor.translateAlternateColorCodes('&', prefix + "&r"));
		String message = e.getMessage();
		if (e.getPlayer().hasPermission("chatter.cc") || e.getPlayer().hasPermission("chatter.*")) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		}
		cFormat = cFormat.replace("<message>", message);
		e.setFormat(cFormat);
		//e.setCancelled(true);
		//String finalMessage = cFormat; // already replaced <name>, <lp_prefix>, etc.

		/*for (Player recipient : e.getRecipients()) {
		    recipient.sendMessage(finalMessage);
		}*/
		
		String name = e.getMessage().toLowerCase();	
		
		long ms = System.currentTimeMillis();	
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {	
			
			if(name.contains(p.getName().toLowerCase()) && !(p.getName() == e.getPlayer().getName())) {	
				
				if(pmap.containsKey(p) && !(e.getPlayer().hasPermission("chatter.mention.staff"))) {		
					Long oldval = pmap.get(p);
					oldval = oldval + 30000;
					
					if(!(ms > (oldval))) {
						//e.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't mention that player yet!");
						return;
					}else {		
						pmap.remove(p);		
					}
				}
				
				if(MentionHandler.ismuted(p) && !(e.getPlayer().hasPermission("chatter.mention.staff"))) {
					return;
				}
				
				p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1f, 1f);	
				pmap.put(p, System.currentTimeMillis());	
			}
					
		}
		
		
	}

}
