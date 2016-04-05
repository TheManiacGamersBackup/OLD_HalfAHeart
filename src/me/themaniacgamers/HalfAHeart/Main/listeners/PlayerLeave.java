package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.utils.PlayerData;
import org.bukkit.entity.Player;
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

    public void playerQuitEvent(PlayerQuitEvent e) {
        Player p = (Player) e.getPlayer();
        e.setQuitMessage("");
        if (p.hasPermission("Hah.LeaveNotify")) {
            e.setQuitMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "-" + ChatColor.DARK_GRAY + "]" + p.getName());
        }
        PlayerData currentPlayer = PlayerData.loadedPlayer.get(p.getUniqueId());
        currentPlayer.saveData();
    }
}