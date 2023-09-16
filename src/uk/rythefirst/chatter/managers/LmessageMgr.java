package uk.rythefirst.chatter.managers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Perms;

public class LmessageMgr {

	public Boolean hasLMessage(UUID uuid) {
		return Main.cache.lMessageMap.containsKey(uuid.toString());
	}

	public String getLMessage(UUID uuid) {
		if (!(Main.cache.lMessageMap.containsKey(uuid.toString()))) {
			Player p = Bukkit.getPlayer(uuid);
			String def = Main.cache.defaultLeaveMsg;
			def = def.replace("<name>", p.getName());
			def = def.replace("<lp_prefix>", Perms.getPrefix(p));
			def = def.replace("<displayname>", p.getDisplayName());
			def = def.replace("<nick>", Main.NickMgr.getNickName(p));
			def = ChatColor.translateAlternateColorCodes('&', def);
			return def;
		} else {
			return ChatColor.translateAlternateColorCodes('&', Main.cache.lMessageMap.get(uuid.toString()));
		}
	}

	public void setLMessage(UUID uuid, String msg) {
		Main.cache.lMessageMap.put(uuid.toString(), msg);
	}

	public void clearLMessage(UUID uuid) {
		if (Main.cache.lMessageMap.containsKey(uuid.toString())) {
			Main.cache.lMessageMap.remove(uuid.toString());
		}
	}

}
