package uk.rythefirst.chatter.liseners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.bounties.DataHandler;
import uk.rythefirst.chatter.layouts.PlayerBounty;
import uk.rythefirst.chatter.util.Chat;

public class PlayerDeath implements Listener {

	@EventHandler
	public void Pd(PlayerDeathEvent e) {

		if (e.getEntity().getKiller() != null && !(e.getEntity() == e.getEntity().getKiller())) {

			if (DataHandler.hasBounty(e.getEntity().getUniqueId())) {

				Economy eco = Main.getEconomy();

				PlayerBounty pb = DataHandler.getBounty(e.getEntity().getUniqueId());

				Double val = pb.getValue();

				eco.depositPlayer(e.getEntity().getKiller(), val);

				Chat.SendCenteredServerChat("The bounty on " + e.getEntity().getName() + " was claimed by: "
						+ e.getEntity().getKiller().getName());

				DataHandler.RemoveBounty(e.getEntity());

			}
			
			ItemStack is = new ItemStack(Material.PLAYER_HEAD);
			SkullMeta meta = (SkullMeta) is.getItemMeta();
			meta.setOwningPlayer((Player) e.getEntity());
			meta.setDisplayName(ChatColor.GOLD + e.getEntity().getName() + "'s Head");

			List<String> llst = new ArrayList<String>();
			
			llst.add(ChatColor.GOLD + "Killed by: " + ChatColor.DARK_RED + e.getEntity().getKiller().getName());
			
			meta.setLore(llst);
			
			is.setItemMeta(meta);
			
			e.getDrops().add(is);
			
		}

	}
}
