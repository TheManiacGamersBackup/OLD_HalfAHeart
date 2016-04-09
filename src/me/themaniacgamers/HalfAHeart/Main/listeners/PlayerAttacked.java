package me.themaniacgamers.HalfAHeart.Main.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.sk89q.minecraft.util.commands.ChatColor;

import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerAttacked implements Listener {

    Main plugin;
    StringsManager strings = StringsManager.getInstance();

    public PlayerAttacked(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAttacked(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player && e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        Player a = (Player) e.getDamager();
        if (a.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SWORD) {
            if (p.getInventory().getItemInMainHand().getType() == Material.EYE_OF_ENDER) {
                if (a.getLevel() != 0.0) {
                    if (p.getLevel() <= 49.0) {
                        BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "You do not have enough strength to block " + ChatColor.AQUA + "" + ChatColor.BOLD + a.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + "'s hits!");
                        BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.AQUA + "" + ChatColor.BOLD + p.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " tried to block your hits but doesn't have enough strength!");
                    } else {
                        BountifulAPI.sendActionBar(a.getPlayer(), ChatColor.AQUA + "" + ChatColor.BOLD + "" + p.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has blocked your hit! -25 strength!");
                        BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "You are blocking " + ChatColor.AQUA + "" + ChatColor.BOLD + a.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + "'s hits! -50 strength!");
                        e.setDamage(0.0);
                        e.setCancelled(true);
                        p.setLevel(p.getLevel() - 50);
                        a.setLevel(a.getLevel() - 25);
                        a.setVelocity(p.getVelocity().multiply(-4));
                        p.playSound(p.getPlayer().getLocation(), Sound.BLOCK_ANVIL_LAND, 4, 4);
                        a.playSound(p.getPlayer().getLocation(), Sound.BLOCK_ANVIL_LAND, 4, 4);
                    }
                } else {
                    BountifulAPI.sendActionBar(a.getPlayer(), ChatColor.RED + "" + ChatColor.BOLD + "You do not have enough strength to hit " + ChatColor.AQUA + "" + ChatColor.BOLD + p.getName() + ChatColor.RED + "" + ChatColor.BOLD + ", while they are blocking!");
                    BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.AQUA + "" + ChatColor.BOLD + a.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + " tried to hit you but didn't have enough strength!");
                    e.setDamage(0.0);
                    e.setCancelled(true);
                }
            }
        } else {
            e.setDamage(0.0);
            e.setCancelled(true);
            BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.DARK_RED + "" + ChatColor.BOLD + a.getName() + ChatColor.RED + "" + ChatColor.BOLD + " is trying to hit you, but isn't holding a sword!");
            BountifulAPI.sendActionBar(a.getPlayer(), ChatColor.RED + "" + ChatColor.BOLD + "You can't hit people with this item! Change to your sword!");
        }
    }
}
