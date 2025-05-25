package uk.rythefirst.chatter.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Templates;

public class ConfigManager {

	private File configFile = new File(Main.instance.getDataFolder() + "/config.yml");
	private YamlConfiguration configYaml;

	private File jMsgFile = new File(Main.instance.getDataFolder() + "/jmessage.yml");
	private YamlConfiguration jmsgCfg;

	private File lMsgFile = new File(Main.instance.getDataFolder() + "/lmessage.yml");
	private YamlConfiguration lmsgCfg;

	private File nickFile = new File(Main.instance.getDataFolder() + "/nick.yml");
	private YamlConfiguration nickCfg;
	
	private File dragonDamageFile = new File(Main.instance.getDataFolder() + "/ddamage.yml");
	private YamlConfiguration dragonCfg;
	
	public void loadConfig() {
		Main.instance.saveResource("config.yml", false);
		configYaml = YamlConfiguration.loadConfiguration(configFile);
		Main.cache.prefix = configYaml.getString("format");
		Bukkit.getLogger().info("[Chatter] Config.yml loaded");
		Main.cache.setTabNick = configYaml.getBoolean("nick-in-tab");
		Main.cache.setTabPrefix = configYaml.getBoolean("prefix-in-tab");
		Main.cache.format = configYaml.getString("format");
		Main.cache.defaultJoinMsg = configYaml.getString("djoin");
		Main.cache.defaultLeaveMsg = configYaml.getString("dleave");
		Main.xpValue = configYaml.getDouble("XPValue");
		if (!(configYaml.contains("tab-enabled"))) {
			ArrayList<String> headerDef = new ArrayList<String>();
			headerDef.add("&6&lWelcome &2&l<displayname>");
			headerDef.add("&6&lServer TPS: <tps>");

			ArrayList<String> footerDef = new ArrayList<String>();
			footerDef.add("&6&lSpigot.org");

			configYaml.set("tab-enabled", true);
			configYaml.set("tab-header", headerDef);
			configYaml.set("tab-footer", footerDef);
			try {
				configYaml.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Main.cache.tabEnabled = configYaml.getBoolean("tab-enabled");
		Main.cache.tabHeader = configYaml.getStringList("tab-header");
		Main.cache.tabFooter = configYaml.getStringList("tab-footer");
		if (Main.cache.tabEnabled) {
			Main.tabMgr.runTimer();
		} else {
			Main.tabMgr.stopTimer();
		}
	}
	
	
	public double getXPValue() {
		
		return configYaml.getDouble("XPValue");
	}

	public ConfigManager() {
		loadConfig();
		
		if (!(jMsgFile.exists())) {
			Bukkit.getLogger().info(Templates.consolePrefix + "Jmsg File not found, creating...");
			try {
				jMsgFile.createNewFile();
				this.jmsgCfg = YamlConfiguration.loadConfiguration(jMsgFile);
				jmsgCfg.set("messages", new ArrayList<String>());
				jmsgCfg.save(jMsgFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			jmsgCfg = YamlConfiguration.loadConfiguration(jMsgFile);
		}

		if (!(lMsgFile.exists())) {
			Bukkit.getLogger().info(Templates.consolePrefix + "Lmsg File not found, creating...");
			try {
				lMsgFile.createNewFile();
				this.lmsgCfg = YamlConfiguration.loadConfiguration(lMsgFile);
				lmsgCfg.set("messages", new ArrayList<String>());
				lmsgCfg.save(lMsgFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			lmsgCfg = YamlConfiguration.loadConfiguration(lMsgFile);
		}

		if (!(nickFile.exists())) {
			Bukkit.getLogger().info(Templates.consolePrefix + "Nickname File not found, creating...");
			try {
				nickFile.createNewFile();
				this.nickCfg = YamlConfiguration.loadConfiguration(nickFile);
				nickCfg.set("nicknames", new ArrayList<String>());
				nickCfg.save(nickFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			nickCfg = YamlConfiguration.loadConfiguration(nickFile);
		}
		
		if (!(dragonDamageFile.exists())) {
			Bukkit.getLogger().info(Templates.consolePrefix + "DragonDamage File not found, creating...");
			try {
				dragonDamageFile.createNewFile();
				this.dragonCfg = YamlConfiguration.loadConfiguration(dragonDamageFile);
				dragonCfg.set("values", new ArrayList<String>());
				dragonCfg.save(dragonDamageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			dragonCfg = YamlConfiguration.loadConfiguration(dragonDamageFile);
		}
		
		Main.xpValue = configYaml.getDouble("XPValue");
		
	}

	public void saveJoinMessage(Boolean disabling) {
		TreeMap<String, String> prefixMap = Main.cache.jMessageMap;
		String divide = "#::#";
		List<String> fixLst = new ArrayList<String>();
		for (Entry<String, String> entry : prefixMap.entrySet()) {
			fixLst.add(entry.getKey() + divide + entry.getValue());
		}
		jmsgCfg.set("messages", fixLst);
		if(!disabling) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
		try {
			jmsgCfg.save(jMsgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}});
		}else {
			try {
				jmsgCfg.save(jMsgFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		}

	public void loadJoinMessage() {
		String divide = "#::#";
		List<String> fixLst = jmsgCfg.getStringList("messages");
		for (String str : fixLst) {
			String[] spltStr = str.split(divide);
			Main.cache.jMessageMap.put(spltStr[0], spltStr[1]);
		}
	}

	public void saveLeaveMessage(Boolean disabling) {
		TreeMap<String, String> leaveMap = Main.cache.lMessageMap;
		String divide = "#::#";
		List<String> fixLst = new ArrayList<String>();
		for (Entry<String, String> entry : leaveMap.entrySet()) {
			fixLst.add(entry.getKey() + divide + entry.getValue());
		}
		lmsgCfg.set("messages", fixLst);
		if(!(disabling)) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
		try {
			lmsgCfg.save(lMsgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}});
		}else {
			try {
				lmsgCfg.save(lMsgFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadLeaveMessage() {
		String divide = "#::#";
		List<String> fixLst = lmsgCfg.getStringList("messages");
		for (String str : fixLst) {
			String[] spltStr = str.split(divide);
			Main.cache.lMessageMap.put(spltStr[0], spltStr[1]);
		}
	}

	public void loadNicks() {
		String divide = "#::#";
		List<String> loadLst = nickCfg.getStringList("nicknames");
		for (String str : loadLst) {
			String[] strSplit = str.split(divide);
			Main.cache.nickMap.put(strSplit[0], strSplit[1]);
		}
	}

	public void saveNicks(Boolean disabling) {
		TreeMap<String, String> saveMap = Main.cache.nickMap;
		String divide = "#::#";
		List<String> pvpLst = new ArrayList<String>();
		for (Entry<String, String> entry : saveMap.entrySet()) {
			pvpLst.add(entry.getKey() + divide + entry.getValue());
		}
		nickCfg.set("nicknames", pvpLst);
		if(!(disabling)) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
		try {
			nickCfg.save(nickFile);
		} catch (IOException e) {
			e.printStackTrace();
		}});
		}else {
			try {
				nickCfg.save(nickFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadTab() {
		if (!(configYaml.contains("tab-enabled"))) {
			ArrayList<String> headerDef = new ArrayList<String>();
			headerDef.add("&6&lWelcome &2&l<displayname>");
			headerDef.add("&6&lServer TPS: <tps>");

			ArrayList<String> footerDef = new ArrayList<String>();
			footerDef.add("&6&lSpigot.org");

			configYaml.set("tab-enabled", true);
			configYaml.set("tab-header", headerDef);
			configYaml.set("tab-footer", footerDef);
			try {
				configYaml.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Main.cache.tabEnabled = configYaml.getBoolean("tab-enabled");
		Main.cache.tabHeader = configYaml.getStringList("tab-header");
		Main.cache.tabFooter = configYaml.getStringList("tab-footer");
	}
	
	public void saveDragonDamage(Boolean disabling) {
		TreeMap<String, Double> saveMap = Main.cache.DragonDamageMap;
		String divide = "#::#";
		List<String> pvpLst = new ArrayList<String>();
		for (Entry<String, Double> entry : saveMap.entrySet()) {
			pvpLst.add(entry.getKey() + divide + entry.getValue());
		}
		dragonCfg.set("values", pvpLst);
		if(!disabling) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
		try {
			dragonCfg.save(dragonDamageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}});
		}else {
			try {
				dragonCfg.save(dragonDamageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadDragonDamage() {
		String divide = "#::#";
		List<String> loadLst = dragonCfg.getStringList("values");
		for (String str : loadLst) {
			String[] strSplit = str.split(divide);
			try {
				Double.parseDouble(strSplit[1]);
			} catch (NumberFormatException e) {
				return;
			}
			Main.cache.DragonDamageMap.put(strSplit[0], Double.parseDouble(strSplit[1]));
		}
	}
	
	public void saveAllAsync() {
		this.saveJoinMessage(false);
		this.saveLeaveMessage(false);
		this.saveNicks(false);
	}

}
