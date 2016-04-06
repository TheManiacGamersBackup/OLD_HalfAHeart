package me.themaniacgamers.HalfAHeart.Main.managers;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by TheManiacGamers on 4/1/2016.
 */
public class ConfigsManager {

    static ConfigsManager instance = new ConfigsManager();
    Main plugin;
    Plugin p;
    FileConfiguration config;
    File cFile;
    FileConfiguration checkpoints;
    File cpFile;
    FileConfiguration playersFile;
    File pFile;

    private ConfigsManager() {
    }

    public static ConfigsManager getInstance() {
        return instance;
    }

//inside Settings class {
//public Location getSpawn() {
//gets coords from file, create Location spawn = new Location blah blah
//return spawn;

    public void setup(Plugin p) {
        cFile = new File(p.getDataFolder(), "config.yml");
        config = p.getConfig();
        //config.options().copyDefaults(true);
        //saveConfig();

        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        Bukkit.getServer().getLogger().fine("[Half A Heart] Loaded the config.yml file successfully!");
        cpFile = new File(p.getDataFolder(), "checkpoints.yml");

        if (!cpFile.exists()) {
            try {
                cpFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create checkpoints.yml!");
            }
        }

        checkpoints = YamlConfiguration.loadConfiguration(cpFile);
        Bukkit.getServer().getLogger().fine("[Half A Heart] Loaded the checkpoints.yml file successfully!");
        Bukkit.getServer().getLogger().fine("[Half A Heart] Loaded all the config files successfully!");
    }

    public FileConfiguration getCheckpoints() {
        return checkpoints;
    }

    public void saveCheckpoints() {
        try {
            checkpoints.save(cpFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save checkpoints.yml!");
        }
    }

    public void reloadSneakCount() {
        checkpoints = YamlConfiguration.loadConfiguration(cpFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cFile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
        }
    }

    public void saveOnPlayersToConfig() {
        Player player = p.getServer().getPlayer(p.getName());
        UUID playerUUID = player.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        PlayerData Killer = PlayerData.pKills.get(player.getUniqueId());
        PlayerData Deaths = PlayerData.pDeaths.get(player.getUniqueId());
        PlayerData Bounty = PlayerData.pBounty.get(player.getUniqueId());
        try {
            Bounty.saveData();
            Killer.saveData();
            Deaths.saveData();
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cFile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }

    public FileConfiguration getPlayersFile() {
        return playersFile;
    }
}