package me.themaniacgamers.HalfAHeart.listeners;

import org.bukkit.event.Listener;

import me.themaniacgamers.HalfAHeart.Main;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerChat implements Listener {
    Main plugin;

    public PlayerChat(Main plugin) {
        this.plugin = plugin;
    }

//    @EventHandler
//    public void onPlayerChat(AsyncPlayerChatEvent e) {
//        Player p = (Player) e.getPlayer();
//        String msg = e.getMessage();
//        if (p.hasPermission("Hah.Chat.Owner") || p.hasPermission("Hah.*") || p.hasPermission("Hah.Chat.*") || p.isOp()) {
//            e.setFormat(ChatColor.WHITE + "" + ChatColor.BOLD + "☆ " + ChatColor.DARK_GRAY + p.getName() + ChatColor.GRAY + " >> " + ChatColor.WHITE + msg);
//            return;
//        }
//        if (p.hasPermission("Hah.Chat.Admin") && (!(p.isOp()))) {
//            e.setFormat(ChatColor.WHITE + "" + ChatColor.BOLD + "☀ " + ChatColor.DARK_GRAY + p.getName() + ChatColor.GRAY + " >> " + ChatColor.WHITE + msg);
//            return;
//        }
//        if (p.hasPermission("Hah.Chat.Mod") && (!(p.isOp()))) {
//            e.setFormat(ChatColor.WHITE + "" + ChatColor.BOLD + "☢ " + ChatColor.DARK_GRAY + p.getName() + ChatColor.GRAY + " >> " + ChatColor.WHITE + msg);
//        } else {
//            e.setFormat(ChatColor.WHITE + "" + ChatColor.BOLD + "" + ChatColor.DARK_GRAY + p.getName() + ChatColor.GRAY + " >> " + ChatColor.WHITE + msg);
//        }
//    }
}