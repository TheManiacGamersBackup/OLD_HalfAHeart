package me.themaniacgamers.HalfAHeart.Main.stored;

import me.themaniacgamers.HalfAHeart.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;

/**
 * Created by Corey on 4/8/2016.
 */
public class PlayerAchievements implements Listener {
    public String FindAllCheckpoints, Reach10Kills, Reach50Kills, ReachThug, KillTheManiacGamers, KillHtgan, Join50Times, Join100Times, Join150Times, Join200Times, ReachAKSOf20, ReachGod;
    Main plugin;

    public PlayerAchievements(Player pl, Main plugin) {
        this.plugin = plugin;
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + pl.getUniqueId() + ".yml");
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        Reach10Kills = playerData.getConfigurationSection("Achievements").getString("Reach10Kills"); //1
        Reach50Kills = playerData.getConfigurationSection("Achievements").getString("Reach50Kills");//2
        ReachThug = playerData.getConfigurationSection("Achievements").getString("ReachThug");//3
        KillTheManiacGamers = playerData.getConfigurationSection("Achievements").getString("KillTheManiacGamers");//4
        KillHtgan = playerData.getConfigurationSection("Achievements").getString("KillHtgan");//5
        Join50Times = playerData.getConfigurationSection("Achievements").getString("Join50Times");//6
        Join100Times = playerData.getConfigurationSection("Achievements").getString("Join100Times");//7
        Join150Times = playerData.getConfigurationSection("Achievements").getString("Join150Times");//8
        Join200Times = playerData.getConfigurationSection("Achievements").getString("Join200Times");//9
        ReachAKSOf20 = playerData.getConfigurationSection("Achievements").getString("ReachAKSOf20");//10
        ReachGod = playerData.getConfigurationSection("Achievements").getString("ReachGod");//11
        FindAllCheckpoints = playerData.getConfigurationSection("Achievements").getString("HasAllCheckpoints");//12
    }
}
