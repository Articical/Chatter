package uk.rythefirst.chatter.util;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;

public class Tools {
	
	public static Integer getPlayerCountNoStaff() {
		
		Integer i = 0;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!(p.hasPermission("chatter.staff"))) {
				i++;
			}
		}
		
		return i;
	}
	
	 public void loadYamlAsync(File file, Consumer<YamlConfiguration> callback) {
	        CompletableFuture.supplyAsync(() -> {
	            if (!file.exists()) return null;
	            return YamlConfiguration.loadConfiguration(file);
	        }).thenAccept(config -> {
	            // Switch to main thread for Bukkit-safe operations
	            Bukkit.getScheduler().runTask(Main.instance, () -> {
	                callback.accept(config);
	            });
	        });
	    }

}
