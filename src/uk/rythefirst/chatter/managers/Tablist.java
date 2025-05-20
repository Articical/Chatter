package uk.rythefirst.chatter.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Perms;
import uk.rythefirst.chatter.util.ResolvePlaceholders;

public class Tablist {

	public BukkitTask tabTask = null;

	public void runTimer() {

		tabTask = new BukkitRunnable() {
			
			/*@Override
			public void run() {
			    // Create a list of players and sort it by group weight (highest to lowest)
			    List<Player> sortedPlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
			    sortedPlayers.sort((p1, p2) -> {
			    	
			        User u1 = Perms.loadUser(p1);
			        User u2 = Perms.loadUser(p2);
			        String group1 = u1.getPrimaryGroup();
			        String group2 = u2.getPrimaryGroup();

			        Group g1 = Main.lpermsAPI.getGroupManager().getGroup(group1);
			        Group g2 = Main.lpermsAPI.getGroupManager().getGroup(group2);

			        int weight1 = g1 != null && g1.getWeight().isPresent() ? g1.getWeight().getAsInt() : 0;
			        int weight2 = g2 != null && g2.getWeight().isPresent() ? g2.getWeight().getAsInt() : 0;
			        
			        // Sort descending
			        return Integer.compare(weight2, weight1);
			    });

			    // Now iterate over sorted players
			    for (Player player : sortedPlayers) {
			    	
			    	Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			    	
			        User user = Perms.loadUser(player);
			        String groupName = user.getPrimaryGroup();
			        Group group = Main.lpermsAPI.getGroupManager().getGroup(groupName);

			        int weight = group != null && group.getWeight().isPresent() ? group.getWeight().getAsInt() : 0;

			        // Pad weight for proper ordering (e.g. 001, 050, 100)
			        String teamName = String.format("%03d_%s", 999 - weight, groupName); // 999 - weight to sort DESCENDING

			        Team team = scoreboard.getTeam(teamName);
			        if (team == null) {
			            team = scoreboard.registerNewTeam(teamName);
			            
			            // Optional: Set a prefix (color/rank tag)
			            String prefix = group.getCachedData().getMetaData().getPrefix();
			            if (prefix != null) {
			                team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
			            }
			        }

			        team.addEntry(player.getName());
			    	
			        List<String> headerDef = Main.cache.tabHeader;
			        List<String> footerDef = Main.cache.tabFooter;
			        StringBuilder sbHeader = new StringBuilder();
			        Integer headerCycle = 0;
			        for (String str : headerDef) {
			            sbHeader.append(ChatColor.translateAlternateColorCodes('&', ResolvePlaceholders.resolve(player, str)));
			            if (headerCycle != headerDef.size() - 1) {
			                sbHeader.append("\n");
			                headerCycle++;
			            }
			        }
			        StringBuilder sbFooter = new StringBuilder();
			        Integer footerCycle = 0;
			        for (String string : footerDef) {
			            sbFooter.append(ChatColor.translateAlternateColorCodes('&', ResolvePlaceholders.resolve(player, string)));
			            if (footerCycle != footerDef.size() - 1) {
			                sbFooter.append("\n");
			                footerCycle++;
			            }
			        }

			        String prefix = "";
			        String TabName = "<name>";

			        if (Main.cache.setTabPrefix) {
			            TabName = "<prefix> <name>";
			        }

			        if (Main.cache.setTabNick) {
			            TabName = TabName.replace("<name>", "<nick>");
			        }

			        User user1 = Perms.loadUser(player);
			        CachedMetaData metaData = user1.getCachedData().getMetaData();

			        TabName = TabName.replace("<name>", player.getName());
			        TabName = TabName.replace("<nick>", Main.NickMgr.getNickName(player));

			        prefix = metaData.getPrefix() != null ? metaData.getPrefix() : "";
			        TabName = TabName.replace("<prefix>", prefix);

			        if (player.hasPermission("chatter.nickcc")) {
			            TabName = ChatColor.translateAlternateColorCodes('&', TabName);
			        }

			        player.setPlayerListName(TabName);
			        player.setPlayerListHeaderFooter(sbHeader.toString(), sbFooter.toString());
			    }
			}*/
			
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
					
					
					String prefix = "";
					
					String TabName = "<name>";

					if (Main.cache.setTabPrefix) {
						TabName = "<prefix> <name>";
					}

					if (Main.cache.setTabNick) {
						TabName = TabName.replace("<name>", "<nick>");
					}
					
					User user = Perms.loadUser(player);
					
					CachedMetaData metaData = user.getCachedData().getMetaData();
					
					TabName = TabName.replace("<name>", player.getName());
					TabName = TabName.replace("<nick>", Main.NickMgr.getNickName(player));
					if (metaData.getPrefix() == null) {
						prefix = "";
					} else {
						prefix = metaData.getPrefix();
					}
					TabName = TabName.replace("<prefix>", prefix);
					if (player.hasPermission("chatter.nickcc")) {
						TabName = ChatColor.translateAlternateColorCodes('&', TabName);
					}
					//Bukkit.getLogger().info("Tab converted: " + TabName);
					player.setPlayerListName(TabName);

					player.setPlayerListHeaderFooter(sbHeader.toString(), sbFooter.toString());

				}

			}
		}.runTaskTimer(Main.instance, 0, 40);
			
			

	}

	public void stopTimer() {
		if (!(tabTask == null)) {
			tabTask.cancel();
			tabTask = null;
		}
	}

}
