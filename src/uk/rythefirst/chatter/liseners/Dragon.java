package uk.rythefirst.chatter.liseners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import uk.rythefirst.chatter.Main;

public class Dragon implements Listener{
	
	private final Map<UUID, Set<UUID>> dragonDamagers = new HashMap<>();
	private final Map<UUID, Map<UUID, Double>> dragonDamageMap = new HashMap<>();
	private final int xpPool = 60000;
	
	@EventHandler
    public void onDragonSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
        	EnderDragon dragon = (EnderDragon) event.getEntity();
            if (dragon.getAttribute(Attribute.MAX_HEALTH) != null) {
                dragon.getAttribute(Attribute.MAX_HEALTH).setBaseValue(1000.0);
                dragon.setHealth(1000.0);
                
                Main.instance.getLogger().info("Boosted dragon HP to 1000.");
            }
        }
    }
	
	@EventHandler
    public void onDragonDamaged(EntityDamageByEntityEvent event) {
		
        if (!(event.getEntity() instanceof EnderDragon)) return;
        if(event.getDamager() instanceof EnderCrystal) {
        	event.setCancelled(true);
        	return;
        }
        EnderDragon dragon = (EnderDragon) event.getEntity();
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        UUID playerId = player.getUniqueId();
        UUID dragonId = dragon.getUniqueId();
        double damage = event.getFinalDamage();
        dragonDamagers.computeIfAbsent(dragonId, k -> new HashSet<>()).add(player.getUniqueId());
        dragonDamageMap
        .computeIfAbsent(dragonId, k -> new HashMap<>())
        .merge(playerId, damage, Double::sum);
    }
	
	@EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;
        EnderDragon dragon = (EnderDragon) event.getEntity();
        UUID dragonId = dragon.getUniqueId();
        Map<UUID, Double> playerDamageMap = dragonDamageMap.getOrDefault(dragonId, Map.of());

        // Prevent vanilla XP drop
        event.setDroppedExp(0);

        // Calculate total damage dealt
        double totalDamage = playerDamageMap.values().stream().mapToDouble(Double::doubleValue).sum();

        // Give scaled XP
        for (Map.Entry<UUID, Double> entry : playerDamageMap.entrySet()) {
            UUID playerId = entry.getKey();
            double playerDamage = entry.getValue();

            Player player = Bukkit.getPlayer(playerId);
            if (player != null && player.isOnline()) {
                double ratio = playerDamage / totalDamage;
                int xpReward = (int) Math.round(ratio * xpPool);
                
                player.giveExp(xpReward);
                player.sendMessage("You dealt " + String.format("%.1f", playerDamage) +
                        " damage and earned " + xpReward + " XP!");
                
                if(Main.cache.DragonDamageMap.containsKey(player.getUniqueId().toString())) {
                	Double oldVal = Main.cache.DragonDamageMap.get(player.getUniqueId().toString());
                	Double newVal = oldVal + playerDamage;
                	Main.cache.DragonDamageMap.replace(player.getUniqueId().toString(), newVal);
                }else {
                	Main.cache.DragonDamageMap.put(player.getUniqueId().toString(), playerDamage);
                }
            }
        }

        // Clean up
        dragonDamageMap.remove(dragonId);
        dragonDamagers.remove(dragonId);
    }
	
	
	@EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        World world = block.getWorld();

        // Check if the world is The End
        if (world.getEnvironment() == World.Environment.THE_END) {
            // Check if the block is any bed type
            if (isBed(block.getType())) {
                event.setCancelled(true);
                player.sendMessage("You can't place beds in The End!");
            }
        }
    }

    private boolean isBed(Material material) {
        return material.name().endsWith("_BED");
    }

}
