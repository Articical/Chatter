package uk.rythefirst.chatter.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.ResolvePlaceholders;

public class Tablist {

	public BukkitTask tabTask = null;

	public void runTimer() {

		tabTask = new BukkitRunnable() {

			@Override
			public void run() {

				for (Player player : Bukkit.getOnlinePlayers()) {
					List<String> headerDef = Main.cache.tabHeader;
					List<String> footerDef = Main.cache.tabFooter;
					StringBuilder sbHeader = new StringBuilder();
					Integer headerCycle = 0;
					for (String str : headerDef) {
						sbHeader.append(
								ChatColor.translateAlternateColorCodes('&', ResolvePlaceholders.resolve(player, str)));
						if (headerCycle != headerDef.size() - 1) {
							sbHeader.append("\n");
							headerCycle++;
						}
					}
					StringBuilder sbFooter = new StringBuilder();
					Integer footerCycle = 0;
					for (String string : footerDef) {
						sbFooter.append(ChatColor.translateAlternateColorCodes('&',
								ResolvePlaceholders.resolve(player, string)));
						if (footerCycle != footerDef.size() - 1) {
							sbHeader.append("\n");
							footerCycle++;
						}
					}

					player.setPlayerListHeaderFooter(sbHeader.toString(), sbFooter.toString());

				}

			}
		}.runTaskTimer(Main.instance, 0, 20);

	}

	public void stopTimer() {
		if (!(tabTask == null)) {
			tabTask.cancel();
			tabTask = null;
		}
	}

}
