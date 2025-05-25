package uk.rythefirst.chatter.liseners;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import net.md_5.bungee.api.ChatColor;
import uk.rythefirst.chatter.Main;
import uk.rythefirst.chatter.util.Chat;

public class Dragon implements Listener{
	
	private final Map<UUID, Set<UUID>> dragonDamagers = new HashMap<>();
	private final Map<UUID, Map<UUID, Double>> dragonDamageMap = new HashMap<>();
	private final int xpPool = 60000;
	
	@EventHandler
    public void onDragonSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
        	
        	Chat.SendCenteredServerChatAnnounce(ChatColor.GOLD + "The " + ChatColor.DARK_PURPLE + "Ender Dragon" + ChatColor.GOLD + " has Returned!");
            
            for(Player player : Bukkit.getOnlinePlayers()) {
            	if(player.getWorld().getName().toLowerCase() == "world_the_end") {
            		player.sendTitle(ChatColor.DARK_PURPLE + "The Ender Dragon has returned!", ChatColor.GOLD + "Watch your back!", 10, 70, 20);
            		player.playSound(player.getLocation(), Sound.ENTITY_PARROT_IMITATE_ENDER_DRAGON, SoundCategory.AMBIENT, 2, 0.3f);
            	}
            }
        	
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
		
		//Check that the entity being damaged is actually a dragon
        if (!(event.getEntity() instanceof EnderDragon)) return;
        
        //If it's an explosion, cancel it (no cheesing thanks)
        if(event.getDamager() instanceof Explosive || event.getDamager().getType().toString().contains("EXPLOSION")) {
        	event.setCancelled(true);
        	return;
        }
        //Cast the dragon as the entity of the event and declare a null player for later use.
        EnderDragon dragon = (EnderDragon) event.getEntity();
        Player player = null;

        //If the entity that damages the dragon is an arrow then set "player" to the person who shot the arrow.
        if (event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if (arrow.getShooter() instanceof Player) {
                player = (Player) arrow.getShooter();
            }
        } else if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();
        }

        if (player == null) return;
        UUID playerId = player.getUniqueId();
        UUID dragonId = dragon.getUniqueId();
        double damage = event.getFinalDamage();
        dragonDamagers.computeIfAbsent(dragonId, k -> new HashSet<>()).add(player.getUniqueId());
        dragonDamageMap
        .computeIfAbsent(dragonId, k -> new HashMap<>())
        .merge(playerId, damage, Double::sum);
        
        event.setDamage(event.getDamage() * 0.75);
    }
	
	@EventHandler
	public void onDragonDamagedByBlockExplosion(EntityDamageEvent event) {
	    if (event.getEntity().getType() == EntityType.ENDER_DRAGON) {
	        if (event.getCause() == DamageCause.BLOCK_EXPLOSION) {
	            event.setCancelled(true);
	        }
	    }
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
