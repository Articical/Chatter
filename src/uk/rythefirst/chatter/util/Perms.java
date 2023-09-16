package uk.rythefirst.chatter.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import uk.rythefirst.chatter.Main;

public class Perms {

	public static User loadUser(Player player) {
		LuckPerms lpm = Main.lpermsAPI;

		if (!player.isOnline()) {
			throw new IllegalStateException("Player is offline");
		}

		return lpm.getUserManager().getUser(player.getUniqueId());
	}

	public static String getPrefix(Player p) {
		User user = Perms.loadUser(p);

		CachedMetaData metaData = user.getCachedData().getMetaData();
		String prefix = "";
		if (!(metaData.getPrefix() == null)) {
			prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		}
		return prefix;
	}

}
