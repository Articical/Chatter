package uk.rythefirst.chatter.managers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Perms;

public class JmessageMgr {

	public Boolean hasJMessage(UUID uuid) {
		return Main.cache.jMessageMap.containsKey(uuid.toString());
	}

	public String getJMessage(UUID uuid) {
		if (!(Main.cache.jMessageMap.containsKey(uuid.toString()))) {
			Player p = Bukkit.getPlayer(uuid);
			String def = Main.cache.defaultJoinMsg;
			def.replace("<name>", p.getName());
			def.replace("<prefix>", Perms.getPrefix(p));
			def.replace("<displayname>", p.getDisplayName());
			def = def.replace("<nick>", Main.NickMgr.getNickName(p));
			def = ChatColor.translateAlternateColorCodes('&', def);
			return def;
		} else {
			return ChatColor.translateAlternateColorCodes('&', Main.cache.jMessageMap.get(uuid.toString()));
		}
	}

	public String getDefaultJoin(UUID id) {
		Player p = Bukkit.getPlayer(id);
		String def = Main.cache.defaultJoinMsg;
		def = def.replace("<name>", p.getName());
		def = def.replace("<lp_prefix>", Perms.getPrefix(p));
		def = def.replace("<displayname>", p.getDisplayName());
		def = def.replace("<nick>", Main.NickMgr.getNickName(p));
		def = ChatColor.translateAlternateColorCodes('&', def);
		return def;
	}

	public void setCMessage(UUID uuid, String msg) {
		Main.cache.jMessageMap.put(uuid.toString(), msg);
	}

	public void clearCMessage(UUID uuid) {
		if (Main.cache.jMessageMap.containsKey(uuid.toString())) {
			Main.cache.jMessageMap.remove(uuid.toString());
		}
	}

}
