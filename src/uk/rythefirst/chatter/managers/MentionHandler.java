package uk.rythefirst.chatter.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;

public class MentionHandler {
	
	private static File disabledYML;
	
	private static FileConfiguration disabledcnf;
	
	private static List<String> mutedp = new ArrayList<String>();
	
	public void loadDisables() {
		
		disabledYML = new File(Main.instance.getDataFolder()+"/mentions.yml");
		disabledcnf = YamlConfiguration.loadConfiguration(disabledYML);
		
		if(!(disabledYML.exists())) {
			
			try {
				
				disabledcnf.save(disabledYML);
				
			}catch (IOException e){
				
				e.printStackTrace();
				
			}
			
		}
		
		List<String> uidlist = disabledcnf.getStringList("users");
		
		mutedp = uidlist;
		
	}
	
	public void saveDisables() {
		
		disabledcnf.set("users", mutedp);
		try {
			
			disabledcnf.save(disabledYML);
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static Boolean ismuted(Player p) {
		
		return mutedp.contains(p.getUniqueId().toString());
		
	}
	
	public static Integer togglemute(Player p) {
		
		if(ismuted(p)) {
			
			mutedp.remove(p.getUniqueId().toString());
			
			return 1;
			
		}else {
			
			mutedp.add(p.getUniqueId().toString());
			
			return 2;
			
		}
		
	}

}
