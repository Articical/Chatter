package uk.rythefirst.chatter.bounties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.layouts.PlayerBounty;

public class DataHandler {

	private static TreeMap<UUID, Double> bountyLst = new TreeMap<UUID, Double>();
	private static TreeMap<UUID, UUID> setterLst = new TreeMap<UUID, UUID>();

	public static TreeMap<UUID, Double> getBounties() {

		return bountyLst;

	}

	
	@SuppressWarnings("unchecked")
	public static void loadBounties() {

		Plugin pl = Main.instance;

		File bdatafile = new File(pl.getDataFolder() + "/bounties.yml");

		if (!(bdatafile.exists())) {
			try {
				bdatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FileConfiguration bdatacnf = YamlConfiguration.loadConfiguration(bdatafile);

		List<String> bties = (List<String>) bdatacnf.getList("bountieslst");

		List<String> setters = (List<String>) bdatacnf.getList("bsetters");

		if(bties == null) {
			
			return;
			
		}
		
		if (bties.size() == 0) {
			return;
		}

		for (int i = 0; i < bties.size(); i++) {

			String[] strblst = bties.get(i).split("#@#");

			bountyLst.put(UUID.fromString(strblst[0]), Double.parseDouble(strblst[1]));

		}

		for (int i = 0; i < setters.size(); i++) {

			String[] setLst = setters.get(i).split("#@#");

			setterLst.put(UUID.fromString(setLst[0]), UUID.fromString(setLst[1]));

		}

		Bukkit.getLogger().log(Level.INFO, "[Niha] Bounties Loaded!");

	}

	public void createFiles() {

	}

	public static void saveBounties(Boolean disabling) {

		Plugin pl = Main.instance;

		File bdatafile = new File(pl.getDataFolder() + "/bounties.yml");

		if (!(bdatafile.exists())) {
			Bukkit.getScheduler().runTaskAsynchronously(pl, () -> {
			try {
				bdatafile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}});
		}

		FileConfiguration bdatacnf = YamlConfiguration.loadConfiguration(bdatafile);

		ArrayList<String> bties = new ArrayList<String>();

		ArrayList<String> setters = new ArrayList<String>();

		for (Map.Entry<UUID, Double> entry : bountyLst.entrySet()) {

			String strbs = entry.getKey().toString() + "#@#" + entry.getValue().toString();

			bties.add(strbs);

		}

		for (Map.Entry<UUID, UUID> entry : setterLst.entrySet()) {

			String setterS = entry.getKey().toString() + "#@#" + entry.getValue().toString();

			setters.add(setterS);

		}

		bdatacnf.set("bountieslst", bties);
		bdatacnf.set("bsetters", setters);

		if(!disabling) {
		
		Bukkit.getScheduler().runTaskAsynchronously(pl, () -> {
		
		try {
			bdatacnf.save(bdatafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		});

		}else {
			try {
				bdatacnf.save(bdatafile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static boolean setBounty(Player target, Player setter, Double amount) {

		if (!(bountyLst.size() <= 18)) {
			return false;
		}

		setterLst.put(target.getUniqueId(), setter.getUniqueId());

		bountyLst.put(target.getUniqueId(), amount);

		return true;

	}

	public static Boolean hasBounty(UUID uuid) {

		return bountyLst.containsKey(uuid);
	}

	public static void RedeemBounty(Player killer, PlayerBounty bounty) {

	}

	public static void RemoveBounty(Player p) {

		if (bountyLst.containsKey(p.getUniqueId())) {

			bountyLst.remove(p.getUniqueId());

			setterLst.remove(p.getUniqueId());

			BHolder.reloadInv();

		}

	}

	public static PlayerBounty getBounty(UUID uuid) {

		if (!hasBounty(uuid)) {

			Player pl = Bukkit.getPlayer(uuid);
			PlayerBounty bounty = new PlayerBounty(pl, false, 0, null);

			return bounty;
		}

		OfflinePlayer setter = Bukkit.getOfflinePlayer(setterLst.get(uuid));

		Double value = bountyLst.get(uuid);

		OfflinePlayer pl = Bukkit.getOfflinePlayer(uuid);

		PlayerBounty bounty = new PlayerBounty(pl, true, value, setter);

		return bounty;
	}

	public static void RemoveBounty(OfflinePlayer p) {
		if (bountyLst.containsKey(p.getUniqueId())) {

			bountyLst.remove(p.getUniqueId());

			setterLst.remove(p.getUniqueId());

			BHolder.reloadInv();

		}
	}

}
