package uk.rythefirst.chatter.util;

import java.text.DecimalFormat;

import org.bukkit.entity.Player;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import uk.rythefirst.chatter.Main;

public class ResolvePlaceholders {

	@SuppressWarnings({ "deprecation", "resource" })
	public static String resolve(Player p, String string) {

		User user = Perms.loadUser(p);

		CachedMetaData metaData = user.getCachedData().getMetaData();
		String prefix = "";
		if (!(metaData.getPrefix() == null)) {
			prefix = metaData.getPrefix();
		}
		string = string.replace("<lp_prefix>", prefix);

		string = string.replaceAll("<displayname>", p.getDisplayName());
		string = string.replaceAll("<name>", p.getName());
		string = string.replaceAll("<nick>", Main.NickMgr.getNickName(p));
		DecimalFormat df = new DecimalFormat("#.##");

		return string;
	}

}
