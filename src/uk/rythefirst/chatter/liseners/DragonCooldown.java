package uk.rythefirst.chatter.liseners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DragonCooldown implements Listener{
	
	private final HashMap<String, Long> worldCooldowns = new HashMap<>();
	private final long cooldownMillis = 60 * 60 * 1000;
	
	@EventHandler
    public void onPlaceCrystal(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() != Material.END_CRYSTAL) return;

        World world = event.getBlock().getWorld();
        if (world.getEnvironment() != World.Environment.THE_END) return;

        Block below = event.getBlock().getRelative(BlockFace.DOWN);
        if (below.getType() != Material.BEDROCK && below.getType() != Material.END_PORTAL_FRAME) return;

        String worldName = world.getName();
        long now = System.currentTimeMillis();

        if (worldCooldowns.containsKey(worldName)) {
            long last = worldCooldowns.get(worldName);
            long timeLeft = cooldownMillis - (now - last);
            if (timeLeft > 0) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cYou must wait " + (timeLeft / 1000) + " seconds before respawning the Ender Dragon.");
                return;
            }
        }

        // Count nearby End Crystals to determine if respawn is likely starting
        long nearbyCrystals = world.getEntitiesByClass(EnderCrystal.class).stream()
            .filter(c -> c.getLocation().distance(event.getBlock().getLocation()) < 10)
            .count();

        if (nearbyCrystals >= 4) {
            worldCooldowns.put(worldName, now);
            Bukkit.getLogger().info("Cooldown started for dragon respawn in world: " + worldName);
        }
    }


}
