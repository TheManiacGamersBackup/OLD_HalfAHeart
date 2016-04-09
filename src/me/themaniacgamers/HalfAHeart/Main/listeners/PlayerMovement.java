package me.themaniacgamers.HalfAHeart.Main.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.themaniacgamers.HalfAHeart.Main.Main;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerMovement implements Listener {

    Main plugin;

    public PlayerMovement(Main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onHoldingEnderEye(final PlayerMoveEvent e) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Player pl = (Player) e.getPlayer();
                if (pl.getInventory().getItemInMainHand().getType().equals(Material.EYE_OF_ENDER)) {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 5));
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, -5));
                }
            }
        }, 0, 20);
    }
}