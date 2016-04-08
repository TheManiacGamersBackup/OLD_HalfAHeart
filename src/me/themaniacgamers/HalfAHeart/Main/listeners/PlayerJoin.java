package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerJoin implements Listener {
    public HashMap<Player, String> stats = new HashMap<Player, String>();
    Main plugin;
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();
    double kills;
    double deaths;

    public PlayerJoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.hasPermission("Hah.Donator") || p.isOp()) {
            e.getPlayer().setMaxHealth(2D);
            e.getPlayer().setHealth(2D);
        } else {
            e.getPlayer().setMaxHealth(1D);
            e.getPlayer().setHealth(1D);
        }
        p.setCollidable(false);
        e.getPlayer().performCommand("spawn");
        p.sendMessage(strings.hahPrefix);
        UUID playerUUID = p.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        String pName = (ChatColor.BLUE + "" + ChatColor.BOLD + p.getName() + ChatColor.AQUA);
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        p.setGameMode(GameMode.SURVIVAL);
        e.setJoinMessage(null);
        if (p.hasPermission("Hah.JoinNotify")) {
            e.setJoinMessage(strings.join + p.getName());
        }
        if (pFile.exists()) {
            p.sendMessage(strings.welcomeBack + p.getName() + strings.aquaExclamation);
            System.out.println(strings.logPrefix + strings.loadedFile + p.getUniqueId() + " (" + pName + ")!");
        } else {
            try {
                pFile.createNewFile();
                System.out.println(strings.hah + strings.createdFile + p.getUniqueId() + " (" + p.getName() + ")!");
                Bukkit.broadcastMessage(strings.defaultMsgs + p.getDisplayName() + ChatColor.GREEN + " has joined for the first time, welcome!");
                playerData.createSection("Options.Username");
                playerData.createSection("Options.Balance");
                playerData.createSection("Options.Group");
                playerData.createSection("Stats.Level");
                playerData.createSection("Stats.Checkpoints");
                playerData.createSection("Stats.Kills");
                playerData.createSection("Stats.Deaths");
                playerData.createSection("Stats.HighestKS");
                playerData.createSection("Stats.Bounty");
                playerData.createSection("Stats.XPtoNxtLvl");
                playerData.createSection("Stats.Killstreak");
                playerData.set("Options.Group", "Citizen");
                playerData.set("Options.Username", p.getName());
                playerData.set("Options.Balance", 500);
                playerData.set("Stats.XPtoNxtLvl", 100);
                playerData.set("Stats.Kills", 0.0);
                playerData.set("Stats.Deaths", 0.0);
                playerData.set("Stats.HighestKS", 0);
                playerData.set("Stats.Bounty", 0);
                playerData.set("Stats.Level", 0);
                playerData.set("Stats.Checkpoints", 0);
                playerData.set("Stats.Killstreak", 0);
                try {
                    playerData.save(pFile);
                } catch (IOException ex) {
                    System.out.println("[HalfAHeart] [Error] COULD NOT SAVE THE PLAYER FILE!");
                }
            } catch (IOException ex) {
                //do nothing
            }

        }
//        if (pFile.exists()) {
//            p.sendMessage(strings.welcomeBack + p.getName() + strings.aquaExclamation);
//            System.out.println(strings.logPrefix + strings.loadedFile + p.getUniqueId() + " (" + pName + ")!");
//        } else {
//            try {
//                pFile.createNewFile();
//                System.out.println(strings.hah + strings.createdFile + p.getUniqueId() + " (" + p.getName() + ")!");
//                Bukkit.broadcastMessage(strings.defaultMsgs + p.getDisplayName() + ChatColor.GREEN + " has joined for the first time, welcome!");
//                playerData.createSection("Options.Username");
//                playerData.createSection("Options.Balance");
//                playerData.createSection("Options.Group");
//                playerData.createSection("Stats.Level");
//                playerData.createSection("Stats.Checkpoints");
//                playerData.createSection("Stats.Kills");
//                playerData.createSection("Stats.Deaths");
//                playerData.createSection("Stats.HighestKS");
//                playerData.createSection("Stats.Bounty");
//                playerData.createSection("Stats.XPtoNxtLvl");
//                playerData.createSection("Stats.Killstreak");
//                playerData.set("Options.Group", "Citizen");
//                playerData.set("Options.Username", p.getName());
//                playerData.set("Options.Balance", 500);
//                playerData.set("Stats.XPtoNxtLvl", 100);
//                playerData.set("Stats.Kills", 0.0);
//                playerData.set("Stats.Deaths", 0.0);
//                playerData.set("Stats.HighestKS", 0);
//                playerData.set("Stats.Bounty", 0);
//                playerData.set("Stats.Level", 0);
//                playerData.set("Stats.Checkpoints", 0);
//                playerData.set("Stats.Killstreak", 0);
//                try {
//                    playerData.save(pFile);
//                } catch (IOException ex) {
//                    System.out.println("[HalfAHeart] [Error] COULD NOT SAVE THE PLAYER FILE!");
//                }
//            } catch (IOException ex) {
//                //do nothing
//            }
//        }
    }

}