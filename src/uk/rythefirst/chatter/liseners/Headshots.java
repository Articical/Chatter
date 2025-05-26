package uk.rythefirst.chatter.liseners;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Headshots implements Listener {

    @EventHandler
    public void onArrowHitEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow arrow)) return;

        Entity victim = event.getEntity();
        if (!(victim instanceof LivingEntity entity)) return;

        // Filter to supported entities
        if (!(entity instanceof Player || entity instanceof Skeleton || entity instanceof Zombie ||
              entity instanceof Pillager || entity instanceof Creeper)) return;

        // Arrow impact position
        double impactY = arrow.getLocation().getY();

        // Base Y of entity
        double entityBaseY = entity.getLocation().getY();

        // Head Y range
        double headMinY = entityBaseY + getHeadStartOffset(entity);
        double headMaxY = headMinY + getHeadHeight(entity);

        if (impactY >= headMinY && impactY <= headMaxY) {
            // Headshot detected
            event.setDamage(event.getDamage() * 1.5);

            Location hitLocation = entity.getLocation().add(0, 1.5, 0);
            entity.getWorld().spawnParticle(Particle.CRIT, hitLocation, 10, 0.1, 0.1, 0.1, 0.02);
            entity.getWorld().playSound(hitLocation, Sound.ENTITY_PLAYER_ATTACK_CRIT, 2, 2);

            // Shooter feedback
            if (arrow.getShooter() instanceof Player shooter) {
                shooter.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.DARK_RED + "HEADSHOT!"));
                shooter.playSound(shooter.getLocation(), Sound.ENTITY_PLAYER_ATTACK_CRIT, 2, 2);
            }

            // Victim feedback (if player)
            if (entity instanceof Player player) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new TextComponent(ChatColor.DARK_RED + "HEADSHOT!"));
            }
        }
    }

    // Starting Y offset of head from base Y of entity
    private double getHeadStartOffset(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (player.isSwimming() || player.isGliding()) return 0.6;
            if (player.isSneaking()) return 1.1;
            return 1.37;
        } else if (entity instanceof Creeper) {
            return 1.25;
        } else {
            return 1.45; // For Zombie, Skeleton, Pillager
        }
    }

    // Approximate height of the head zone
    private double getHeadHeight(LivingEntity entity) {
        if (entity instanceof Creeper) {
            return 0.3;
        } else if (entity instanceof Player) {
            return 0.25;
        } else {
            return 0.3;
        }
    }
}
