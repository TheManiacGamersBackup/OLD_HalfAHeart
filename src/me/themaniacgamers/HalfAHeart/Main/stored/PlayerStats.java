package me.themaniacgamers.HalfAHeart.Main.stored;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * @author Corey
 * @author Bee
 */
public class PlayerStats implements Listener {

    public int iFindAllCheckpoints, iReach10Kills, iReach50Kills, iReachThug, iKillTheManiacGamers, iKillHtgan, iJoin50Times, iJoin100Times, iJoin150Times, iJoin200Times, iReachAKSOf20, iReachGod, achievementsam, kills, deaths, killstreak, balance, level, bounty, xptonxtlevel, checkpoints, highestks;
    public String group, FindAllCheckpoints, Reach10Kills, Reach50Kills, ReachThug, KillTheManiacGamers, KillHtgan, Join50Times, Join100Times, Join150Times, Join200Times, ReachAKSOf20, ReachGod;
    Main plugin;

    public PlayerStats(Player pl, Main plugin) {
        this.plugin = plugin;
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + pl.getUniqueId() + ".yml");
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        kills = playerData.getConfigurationSection("Stats").getInt("Kills");
        deaths = playerData.getConfigurationSection("Stats").getInt("Deaths");
        highestks = playerData.getConfigurationSection("Stats").getInt("HighestKS");
        balance = playerData.getConfigurationSection("Options").getInt("Balance");
        level = playerData.getConfigurationSection("Stats").getInt("Level");
        xptonxtlevel = playerData.getConfigurationSection("Stats").getInt("XPtoNxtLvl");
        bounty = playerData.getConfigurationSection("Stats").getInt("Bounty");
        checkpoints = playerData.getConfigurationSection("Stats").getInt("Checkpoints");
        killstreak = playerData.getConfigurationSection("Stats").getInt("Killstreak");
        group = playerData.getConfigurationSection("Options").getString("Group");
        achievementsam = playerData.getConfigurationSection("Stats").getInt("Stats");
        Reach10Kills = playerData.getConfigurationSection("Stats").getString("Achievements.Reach10Kills"); //1
        Reach50Kills = playerData.getConfigurationSection("Stats").getString("Achievements.Reach50Kills");//2
        ReachThug = playerData.getConfigurationSection("Stats").getString("Achievements.ReachThug");//3
        KillTheManiacGamers = playerData.getConfigurationSection("Stats").getString("Achievements.KillTheManiacGamers");//4
        KillHtgan = playerData.getConfigurationSection("Stats").getString("Achievements.KillHtgan");//5
        Join50Times = playerData.getConfigurationSection("Stats").getString("Achievements.Join50Times");//6
        Join100Times = playerData.getConfigurationSection("Stats").getString("Achievements.Join100Times");//7
        Join150Times = playerData.getConfigurationSection("Stats").getString("Achievements.Join150Times");//8
        Join200Times = playerData.getConfigurationSection("Stats").getString("Achievements.Join200Times");//9
        ReachAKSOf20 = playerData.getConfigurationSection("Stats").getString("Achievements.ReachAKSOf20");//10
        ReachGod = playerData.getConfigurationSection("Stats").getString("Achievements.ReachGod");//11
        FindAllCheckpoints = playerData.getConfigurationSection("Stats").getString("Achievements.HasAllCheckpoints");//12
        iReach10Kills = playerData.getConfigurationSection("Stats").getInt("Count.Reach10Kills"); //1
        iReach50Kills = playerData.getConfigurationSection("Stats").getInt("Count.Reach50Kills"); //2
        iReachThug = playerData.getConfigurationSection("Stats").getInt("Count.ReachThug"); //3
        iKillTheManiacGamers = playerData.getConfigurationSection("Stats").getInt("Count.KillTheManiacGamers"); //4
        iKillHtgan = playerData.getConfigurationSection("Stats").getInt("Count.KillHtgan"); //5
        iJoin50Times = playerData.getConfigurationSection("Stats").getInt("Count.Join50Times"); //6
        iJoin100Times = playerData.getConfigurationSection("Stats").getInt("Count.Join100Times"); //7
        iJoin150Times = playerData.getConfigurationSection("Stats").getInt("Count.Join150Times"); //8
        iJoin200Times = playerData.getConfigurationSection("Stats").getInt("Count.Join200Times"); //9
        iReachAKSOf20 = playerData.getConfigurationSection("Stats").getInt("Count.ReachAKSOf20"); //10
        iReachGod = playerData.getConfigurationSection("Stats").getInt("Count.ReachGod"); //11
        iFindAllCheckpoints = playerData.getConfigurationSection("Stats").getInt("Count.HasAllCheckpoints"); //12
    }
}
