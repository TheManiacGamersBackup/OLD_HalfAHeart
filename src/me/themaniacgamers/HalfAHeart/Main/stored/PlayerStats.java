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

    public int kills, deaths, killstreak, balance, level, bounty, xptonxtlevel, checkpoints, highestks;
    public String group;
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
    }
}
