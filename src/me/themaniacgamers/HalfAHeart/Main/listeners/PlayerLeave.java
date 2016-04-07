package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by TheManiacGamers on 4/2/2016.
 */
public class PlayerLeave implements Listener {

    Main plugin;

    public PlayerLeave(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        Player p = (Player) e.getPlayer();
        e.setQuitMessage(null);
        if (p.hasPermission("Hah.LeaveNotify")) {
            e.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + p.getName());
        }
//        PlayerData currentPlayer = PlayerData.loadedPlayer.get(p.getUniqueId());
//        currentPlayer.saveData();
    }
}