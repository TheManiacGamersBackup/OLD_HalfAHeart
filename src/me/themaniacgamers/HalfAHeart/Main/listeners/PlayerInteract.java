package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;
import me.themaniacgamers.HalfAHeart.Main.utils.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by TheManiacGamers on 4/2/2016.
 */
public class PlayerInteract implements Listener {
    Main plugin;

    public PlayerInteract(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void grenadeThrow(ProjectileHitEvent event) {

        if (event.getEntity() instanceof Egg) {
            Player p = (Player) event.getEntity().getShooter();
            p.sendMessage(ChatColor.RED + "Sorry, but there's been some errors in the code with this item.");
        }
//            Egg grenade = (Egg) event.getEntity();
//            double posX = event.getEntity().getLocation().getX();
//            double posY = event.getEntity().getLocation().getY();
//            double posZ = event.getEntity().getLocation().getZ();

//            for (Entity nearby : grenade.getNearbyEntities(5, 5, 5)) {
//                if (!(nearby instanceof Player)) { // You can't damage entities
//                    continue;
//                }
//                Player nearbyp = (Player) nearby;
//                Location loc = grenade.getLocation();
//                grenade.getWorld().createExplosion(loc, 0.0F);
//                ParticleEffect.EXPLOSION_LARGE.display(1, 1, 1, 1, 100, grenade.getLocation(), nearbypEffect);
//                BountifulAPI.sendActionBar(nearbyp.getPlayer(), ChatColor.GOLD + "" + ChatColor.BOLD + "Fire in the hole!!!");
//
//            }
//            for (Entity nearbyEffect : grenade.getNearbyEntities(20, 20, 20)) {
//                if (!(nearbyEffect instanceof Player)) {
//                    return;
//                }
//                Player nearbypEffect = (Player) nearbyEffect;
//                ParticleEffect.EXPLOSION_NORMAL.display(1, 1, 1, 1, 100, grenade.getLocation(), nearbypEffect);
//            }
//            for (Entity nearbyEffect : grenade.getNearbyEntities(10, 10, 10)) {
//                if (!(nearbyEffect instanceof Player)) {
//                    return;
//                }
//                Player nearbypEffect = (Player) nearbyEffect;
//                BountifulAPI.sendActionBar(nearbypEffect.getPlayer(), ChatColor.GOLD + "" + ChatColor.BOLD + "Fire in the hole!!!");
//            }
//        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void eyeofenderDisable(PlayerInteractEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.EYE_OF_ENDER)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void smokebombThrow(ProjectileHitEvent e) {
        if (e.getEntity() instanceof EnderPearl) {
            EnderPearl smokebomb = (EnderPearl) e.getEntity();
            double posX = e.getEntity().getLocation().getX();
            double posY = e.getEntity().getLocation().getY();
            double posZ = e.getEntity().getLocation().getZ();

            for (Entity nearby : smokebomb.getNearbyEntities(5, 5, 5)) {
                if (!(nearby instanceof Player)) { // You can't damage entities
                    continue;
                }
                Player nearbyp = (Player) nearby;
                Location loc = smokebomb.getLocation();
//                grenade.getWorld().createExplosion(loc, 0.0F);
//                ParticleEffect.EXPLOSION_LARGE.display(1, 1, 1, 1, 100, grenade.getLocation(), nearbypEffect);
//                BountifulAPI.sendActionBar(nearbyp.getPlayer(), ChatColor.GOLD + "" + ChatColor.BOLD + "Fire in the hole!!!");
                nearbyp.addPotionEffect(PotionEffectType.SLOW.createEffect(100, 100));
                nearbyp.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(160, 100));
                BountifulAPI.sendActionBar(nearbyp.getPlayer(), ChatColor.BLACK + "" + ChatColor.BOLD + "You've been smoked!");
            }
            for (Entity nearbyEffect : smokebomb.getNearbyEntities(20, 20, 20)) {
                if (!(nearbyEffect instanceof Player)) {
                    return;
                }
                Player nearbypEffect = (Player) nearbyEffect;
                ParticleEffect.SMOKE_LARGE.display(1, 1, 1, 1, 100, smokebomb.getLocation(), nearbypEffect);
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = e.getCause();
        if (cause.equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
                || cause.equals(PlayerTeleportEvent.TeleportCause.UNKNOWN)) {
            e.setCancelled(true);
        }
    }
}