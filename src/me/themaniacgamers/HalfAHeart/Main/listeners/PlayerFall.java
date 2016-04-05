package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;
import org.w3c.dom.events.EventException;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerFall implements Listener {
    Main plugin;

    public PlayerFall(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (((Player) e.getEntity()).isSneaking()) {
                Player p = (Player) ((Player) e.getEntity()).getPlayer();
                e.setCancelled(true);
                if (p.hasPermission("Hah.Donator")) {
                    if (p.getFallDistance() <= 9) {
                        BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "Graceful Landing!");
                        e.setDamage(0.0);
                        return;
                    }
                    if (p.getFallDistance() >= 10 && (!(p.getFallDistance() <= 9))) {
                        BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "Butt SLAM!");
                        for (Entity nearby : p.getNearbyEntities(5.0, 5.0, 5.0)) {
                            if (!(nearby instanceof Player)) { // You can't damage entities
                                continue;
                            }
                            Player nearbyp = (Player) nearby;
                            Vector direction = nearby.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                            direction.setX(direction.getX() + 1);
                            direction.setZ(direction.getZ() + 1);
                            nearby.setVelocity(direction);
                            BountifulAPI.sendActionBar(nearbyp.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "You were BUTT SLAMMED!!!");
                            return;
                        }
                    }
                    if (p.getFallDistance() >= 20 && (!(p.getFallDistance() <= 19))) {
                        BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "Extreme Butt SLAM!");
                        for (Entity nearby : p.getNearbyEntities(10.0, 10.0, 10.0)) {
                            if (!(nearby instanceof Player)) { // You can't damage entities
                                continue;
                            }
                            Player nearbyp = (Player) nearby;
                            Vector direction = nearby.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
                            direction.setX(direction.getX() + 2);
                            direction.setY(direction.getY() + 1);
                            direction.setZ(direction.getZ() + 2);
                            nearby.setVelocity(direction);
                            ((Player) nearby).damage(1.0);
                            BountifulAPI.sendActionBar(nearbyp.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "You were BUTT SLAMMED!!!");
                        }
                    } else {
                        try {
                            BountifulAPI.sendActionBar(((Player) e.getEntity()).getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "Graceful Landing");
                            e.setDamage(0.0);
                        } catch (EventException ex) {
                            //do nothing
                        }
                        //DO NOTHING
                    }
                } else {
                    try {
                        BountifulAPI.sendActionBar(((Player) e.getEntity()).getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "Graceful Landing");
                        e.setDamage(0.0);
                    } catch (EventException ex) {
                        //do nothing
                    }
                    //DO NOTHING
                }
            }
        }
    }
}
