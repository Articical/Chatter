package uk.rythefirst.chatter.liseners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Perms;

public class PlayerChat implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
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
	}

}
