package me.themaniacgamers.HalfAHeart.Main.listeners;

import me.themaniacgamers.HalfAHeart.Main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerFood implements Listener {
    Main plugin;

    public PlayerFood(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFoodLevelChange(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        p.setFoodLevel(20);
    }


}
