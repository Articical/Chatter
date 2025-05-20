package uk.rythefirst.chatter.bounties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.layouts.PlayerBounty;

public class GUIBuilder {
	
	public Inventory inv;
	
	public void buildInv() {
		
		inv = buildBInv();
		
	}
	
	public Plugin plugin = Main.instance;
	
	public Inventory buildBInv() {
		
		TreeMap<UUID,Double> bounties = DataHandler.getBounties();
		
		final List<ItemStack> listis = new ArrayList<ItemStack>();
		
		Inventory Inv = Bukkit.createInventory(null, 54, "" + ChatColor.DARK_RED + ChatColor.BOLD + "Active Bounties");
		
		new BukkitRunnable() {
			@Override
			public void run() {
				
				try {
					
					
					
					for(Map.Entry<UUID, Double> entry : bounties.entrySet()) {
						
						PlayerBounty b = DataHandler.getBounty(entry.getKey());
						
						OfflinePlayer p = Bukkit.getOfflinePlayer(entry.getKey());
						
						ItemStack is = new ItemStack(Material.PLAYER_HEAD);
						
						SkullMeta meta = (SkullMeta) is.getItemMeta();
						meta.setOwningPlayer(p);
						meta.setDisplayName(p.getName());
						List<String> llst = new ArrayList<String>();
						
						llst.add(ChatColor.GOLD + "Value: $" + b.getValue().toString());
						llst.add(ChatColor.GOLD + "Set by: " + b.getWhoSet().getName());
						
						meta.setLore(llst);
						
						is.setItemMeta(meta);
						
						listis.add(is);
						
					}
					
					new BukkitRunnable() {

						@Override
						public void run() {
							
							for(int i=0;i<listis.size();i++) {
								
								ItemStack is = listis.get(i);
								
								Inv.addItem(is);
								
							}
							
						}
						
					}.runTask(plugin);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}.runTaskAsynchronously(plugin);
		
		return Inv;
		
	}

}
