package uk.rythefirst.chatter.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Perms;

public class NickNameMgr {

	public void setNickName(Player p, String Nickname) {
		if (Main.cache.nickMap.containsKey(p.getUniqueId().toString())) {
			Main.cache.nickMap.remove(p.getUniqueId().toString());
		}
		Main.cache.nickMap.put(p.getUniqueId().toString(), Nickname);
		setVisibleName(p);
	}

	public void clearNickName(Player p) {
		setNickName(p, p.getName());
	}

	public void setVisibleName(Player p) {
		StringBuilder sb = new StringBuilder();

		sb.append(Main.NickMgr.getNickName(p));
		sb.append("&r");
		if (p.hasPermission("chatter.nickcc")) {
			p.setDisplayName(ChatColor.translateAlternateColorCodes('&', sb.toString()));
		} else {
			p.setDisplayName(sb.toString());
		}

		String TabName = "<name>";

		if (Main.cache.setTabPrefix) {
			TabName = "<prefix> <name>";
		}

		if (Main.cache.setTabNick) {
			TabName = TabName.replace("<name>", "<nick>");
		}

		Bukkit.getLogger().info("Tab Format: " + TabName);

		User user = Perms.loadUser(p);

		CachedMetaData metaData = user.getCachedData().getMetaData();
		String prefix;

		TabName = TabName.replace("<name>", p.getName());
		TabName = TabName.replace("<nick>", getNickName(p));
		if (metaData.getPrefix() == null) {
			prefix = "";
		} else {
			prefix = metaData.getPrefix();
		}
		TabName = TabName.replace("<prefix>", prefix);
		if (p.hasPermission("chatter.nickcc")) {
			TabName = ChatColor.translateAlternateColorCodes('&', TabName);
		}
		Bukkit.getLogger().info("Tab converted: " + TabName);
		p.setPlayerListName(TabName);
	}

	public String getNickName(Player p) {
		if (Main.cache.nickMap.containsKey(p.getUniqueId().toString())) {
			return Main.cache.nickMap.get(p.getUniqueId().toString());
		}
		return p.getName();
	}

}
