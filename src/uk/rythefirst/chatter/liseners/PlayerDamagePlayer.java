package uk.rythefirst.chatter.liseners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import uk.rythefirst.chatter.Main;

public class PlayerDamagePlayer implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerDamagePlayerEvent(EntityDamageByEntityEvent e) {
		
		if(!(e.getDamageSource().getCausingEntity() instanceof Player)){
			return;
		}
		
		if(!(e.getEntity() instanceof Player)) {
			return;
		}
		
			Player dmger = (Player) e.getDamager();
			if (dmger.getItemInHand() != null) {
				ItemStack is = dmger.getItemInHand();
				ItemMeta im = is.getItemMeta();
				if ((!(im == null)) && im.hasLore() && im.getLore().get(0).contains("BAN THEM.")) {
					if (e.getDamager().hasPermission("chatter.staff.bsword")) {
						Bukkit.dispatchCommand(dmger,
								"ban " + e.getEntity().getName() + " The Ban Sword Has Spoken.");
					}
					e.setCancelled(true);
				}
			}
		
		Player attacked = (Player) e.getEntity();
		
		Player attacker = (Player) e.getDamageSource().getCausingEntity();
		
		Main.pvpManager.setLastHitEpoch(attacker);
		Main.pvpManager.setLastHitEpoch(attacked);
		
		return;
		
	}

}
