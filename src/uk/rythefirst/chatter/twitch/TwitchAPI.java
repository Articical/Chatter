package uk.rythefirst.chatter.twitch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Messages;

public class TwitchAPI {

	public Plugin pl = Main.instance;

	public static TwitchInfo info = new TwitchInfo(null, null, null, false, null);

	public static boolean Live = false;

	public static String getUrlAsString(String url) {
		StringBuilder response = new StringBuilder();
		try {

			URL urlObj = new URL(url);
			URLConnection con = urlObj.openConnection();

			con.setDoOutput(true);
			con.setUseCaches(false);
			con.connect();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;

			String newLine = System.getProperty("line.separator");
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine + newLine);
			}

			in.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return response.toString();

	}

	public static TwitchInfo isLive(@Nullable String channel) throws IOException {

		String s;

		if (channel == null) {
			s = getUrlAsString("http://198.244.177.21/live.php?user=aoisakura");
		} else {
			s = getUrlAsString("http://198.244.177.21/live.php?user=" + channel);
		}

		s = s.replace("<body>", " ");
		s = s.replace("</body>", " ");
		s = s.replace("</html>", " ");

		// Bukkit.getLogger().log(Level.INFO, s);

		String[] info = s.split("#@#");

		// Bukkit.getLogger().log(Level.WARNING, info[0]);

		if (!(s.contains("offline")) && !(s.contains("error"))) {

			String title = info[1];
			Integer vc = Integer.parseInt(info[2]);
			String game = info[3];

			// Bukkit.getLogger().log(Level.INFO, info[2]);

			return new TwitchInfo("nihachu", title, game, true, vc);

		} else {
			return new TwitchInfo(null, null, null, false, null);
		}
	}

	public void StartWatcher() {
		Bukkit.getScheduler().runTaskTimer(pl, new Runnable() {

			@Override
			public void run() {

				Bukkit.getLogger().log(Level.INFO, "Checking stream status...");

				// Bukkit.broadcastMessage("Checking stream status...");

				try {

					Plugin plugin = Main.instance;

					new BukkitRunnable() {

						@Override
						public void run() {

							try {

								TwitchInfo tInf = isLive(null);

								new BukkitRunnable() {

									@Override
									public void run() {

										info = tInf;
									}

								}.runTask(plugin);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}.runTaskAsynchronously(plugin);

					if (info.isLive() == true && Live == false) {

						String viewer = info.getViewerCount().toString();
						String game = info.getGame();
						game = game.replace("</body>", " ");
						// String title = info.getTitle();
						Bukkit.getLogger().log(Level.INFO, "Stream live.");
						for (Player player : Bukkit.getServer().getOnlinePlayers()) {

							player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1f, 1f);

							Messages.sendCenteredMessage(player, "");
							Messages.sendCenteredMessage(player,
									"" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Mineplay");
							Messages.sendCenteredMessage(player, "");
							Messages.sendCenteredMessage(player,
									"" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "AoiSakura is now live!");
							Messages.sendCenteredMessage(player,
									"" + ChatColor.DARK_PURPLE + "@ https://www.twitch.tv/aoisakura");
							// Messages.sendCenteredMessage(player,"" + ChatColor.DARK_PURPLE + title);
							Messages.sendCenteredMessage(player, "" + ChatColor.DARK_PURPLE + "Playing: " + game);
							Messages.sendCenteredMessage(player,
									"" + ChatColor.DARK_PURPLE + "With " + viewer + " Viewers!");
							Messages.sendCenteredMessage(player, "");

						}
						Live = true;
						return;
					} else if (info.isLive() == false && Live == true) {
						Bukkit.getLogger().log(Level.INFO, "Stream Ended.");
						Bukkit.broadcastMessage("" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Stream just ended!");
						info = new TwitchInfo(null, null, null, false, null);
						Live = false;
					} else if (info.isLive()) {
						Bukkit.getLogger().log(Level.INFO, "Stream Live (Already known)");
					} else {
						Bukkit.getLogger().log(Level.INFO, "Stream Offline");
						// Bukkit.getLogger().log(Level.WARNING, info.isLive().toString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}, 0L, 600L);
	}

	public static Boolean live() {
		return Live;
	}

}
