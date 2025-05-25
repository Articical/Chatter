package uk.rythefirst.chatter.liseners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Headshots implements Listener{
	
	@EventHandler
	public void onArrowHitPlayer(EntityDamageByEntityEvent event) {
	    if (!(event.getDamager() instanceof Arrow)) return;
	    if (!(event.getEntity() instanceof Player)) return;
	    
	    Player player = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getDamager();

	    // Get arrow direction and position
	    Vector arrowPos = arrow.getLocation().toVector();
	    Vector arrowDir = arrow.getVelocity().normalize();

	    // Determine adjusted eye height
	    double eyeHeight = player.getEyeHeight();
	    if (player.isSneaking()) {
	        eyeHeight -= 0.27; // crouching reduces height ~0.27 blocks
	    }

	    // Adjust even further for swimming or elytra flying
	    boolean isLyingDown = player.isSwimming() || player.isGliding();
	    double headMinY, headMaxY;
	    Vector playerHeadCenter;

	    if (isLyingDown) {
	        // When lying, treat the head as a horizontal bounding box
	        // Use player's direction to offset the head box slightly forward
	        Vector facing = player.getLocation().getDirection().normalize().setY(0);
	        Vector headOffset = facing.clone().multiply(0.5); // 0.5 block forward from body
	        Vector playerPos = player.getLocation().toVector().add(new Vector(0, 0.6, 0)); // lower head center

	        playerHeadCenter = playerPos.add(headOffset);
	        headMinY = playerHeadCenter.getY() - 0.25;
	        headMaxY = playerHeadCenter.getY() + 0.25;
	    } else {
	        // Standing or crouching
	        Location base = player.getLocation();
	        playerHeadCenter = base.toVector().add(new Vector(0, eyeHeight, 0));
	        headMinY = playerHeadCenter.getY() - 0.25;
	        headMaxY = playerHeadCenter.getY() + 0.25;
	    }

	    // Check if the arrow line comes within head bounds
	    Vector closestPoint = closestPointOnLine(arrowPos, arrowDir, playerHeadCenter);
	    double distance = closestPoint.distance(playerHeadCenter);

	    if (distance <= 0.3 &&
	        closestPoint.getY() >= headMinY &&
	        closestPoint.getY() <= headMaxY) {

	        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
	            new TextComponent(ChatColor.DARK_RED + "HEADSHOT!"));
	        player.playSound(player.getLocation(), Sound.ENTITY_GOAT_SCREAMING_HURT, 2, 2);
	        event.setDamage(event.getDamage() * 1.5);
	        if(arrow.getShooter() instanceof Player) {
	        	Player shooter = (Player) arrow.getShooter();
	        	shooter.spigot().sendMessage(ChatMessageType.ACTION_BAR,
	    	            new TextComponent(ChatColor.DARK_RED + "HEADSHOT!"));
	    	        shooter.playSound(shooter.getLocation(), Sound.ENTITY_GOAT_SCREAMING_HURT, 2, 2);
	        }
	    }
	}

	    /**
	     * Calculates the closest point on a line to a given point.
	     * Line: starts at linePoint, goes in direction lineDir (must be normalized).
	     */
	    private Vector closestPointOnLine(Vector linePoint, Vector lineDir, Vector point) {
	        Vector relative = point.clone().subtract(linePoint);
	        double dot = relative.dot(lineDir);
	        return linePoint.clone().add(lineDir.clone().multiply(dot));
	    }

}
