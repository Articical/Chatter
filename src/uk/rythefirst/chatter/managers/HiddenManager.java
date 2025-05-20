package uk.rythefirst.chatter.managers;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import uk.rythefirst.chatter.Main;

public class HiddenManager {
	
	private ArrayList<UUID> hiddenList = new ArrayList<UUID>();
	
	private TreeMap<UUID, ItemStack[]> inventoryList = new TreeMap<UUID, ItemStack[]>();
	
	public boolean isHidden(UUID Pid) {
		return hiddenList.contains(Pid);
	}
	
	public boolean hide(Player Pl) {
		
		UUID Pid = Pl.getUniqueId();
		hiddenList.add(Pid);
		
		Pl.setInvisible(true);
		
		if(!(inventoryList.containsKey(Pid))) {
			if(!(Pl.getInventory().getContents() == null)) {
			inventoryList.put(Pid, Pl.getInventory().getContents());
			Pl.getInventory().clear();
			}
		}
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(!p.hasPermission("chatter.mod")) {
				p.hidePlayer(Main.instance, Pl);
			}
		}
		
		return true;
	}
	
	public boolean hideForPlayer(Player Pl) {
		for(int i =0;i<hiddenList.size();i++) {
			Pl.hidePlayer(Main.instance, Bukkit.getPlayer(hiddenList.get(i)));
		}
		
		return true;
		
	}
	
	public boolean unHide(Player Pl) {
		UUID Pid = Pl.getUniqueId();
		hiddenList.remove(Pid);
		
		Pl.setInvisible(false);
		
		Pl.getInventory().setContents(inventoryList.get(Pid));
		
		inventoryList.remove(Pid);
		
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.showPlayer(Main.instance, Pl);
		}
		
		return true;
	}
	
	public void sendActionBar(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
		
		
	}
	
	public void runActionTimer() {
		Bukkit.getScheduler().runTaskTimer(Main.instance, () -> {
				for(int i = 0;i < hiddenList.size();i++) {
					if(!(hiddenList.isEmpty())) {
						sendActionBar(Bukkit.getPlayer(hiddenList.get(i)), ChatColor.DARK_GREEN + "ModMode Active!");
					}
				}
		}, 0L, 40L);
	}

}
