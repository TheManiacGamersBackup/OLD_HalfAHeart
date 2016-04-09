package me.themaniacgamers.HalfAHeart.Main.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;

/**
 * Created by Corey on 4/3/2016.
 */
public class PlayerData {

    public static HashMap<UUID, PlayerData> loadedPlayer = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pKills = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pDeaths = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pBounty = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pBalance = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pXPtoLvl = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pLvl = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pKDR = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pHighestKS = new HashMap<UUID, PlayerData>();
    public static HashMap<UUID, PlayerData> pCheckpoints = new HashMap<UUID, PlayerData>();
    public ConfigsManager configs = ConfigsManager.getInstance();
    Main plugin;
    private int bounty;
    private int kills;
    private int deaths;
    private int balance;
    private int XPtoLvl;
    private int Lvl;
    private int KDR;
    private int highestKS;
    //blah
    private int checkpoints;

    public PlayerData(UUID id) {
        //read all stats from file
        //bounty = whateverfromfile
        //configs manager to get from UUID.yml
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("Kills") == null) {
            kills = configs.getPlayersFile().getConfigurationSection("Stats").getInt("Kills");
        } else {
            configs.getPlayersFile().set("Stats.Kills", 0);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("Deaths") == null) {
            deaths = configs.getPlayersFile().getConfigurationSection("Stats").getInt("Deaths");
        } else {
            configs.getPlayersFile().set("Stats.Deaths", 0);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("Bounty") == null) {
            bounty = configs.getPlayersFile().getConfigurationSection("Stats").getInt("Bounty");
        } else {
            configs.getPlayersFile().set("Stats.Bounty", 0);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("Balance") == null) {
            kills = configs.getPlayersFile().getConfigurationSection("Stats").getInt("Balance");
        } else {
            configs.getPlayersFile().set("Stats.Balance", 100);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("Level") == null) {
            deaths = configs.getPlayersFile().getConfigurationSection("Stats").getInt("Level");
        } else {
            configs.getPlayersFile().set("Stats.Level", 0);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("XpToNxtLvl") == null) {
            bounty = configs.getPlayersFile().getConfigurationSection("Stats").getInt("XpToNxtLvl");
        } else {
            configs.getPlayersFile().set("Stats.XpToNxtLvl", 0);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("KDR") == null) {
            bounty = configs.getPlayersFile().getConfigurationSection("Stats").getInt("KDR");
        } else {
            configs.getPlayersFile().set("Stats.KDR", 1);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("HighestKS") == null) {
            bounty = configs.getPlayersFile().getConfigurationSection("Stats").getInt("KighestKS");
        } else {
            configs.getPlayersFile().set("Stats.HighestKS", 0);
        }
        if (configs.getPlayersFile().getConfigurationSection("Stats").get("Checkpoints") == null) {
            bounty = configs.getPlayersFile().getConfigurationSection("Stats").getInt("Checkpoints");
        } else {
            configs.getPlayersFile().set("Stats.Checkpoints", 0);
        }
    }

    public int getBounty() {
        return bounty;
    }

    public void setBounty(int bounty) {
        this.bounty = bounty;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getXPtoLvl() {
        return XPtoLvl;
    }

    public void setXPtoLvl(int XPtoLvl) {
        this.XPtoLvl = XPtoLvl;
    }

    public int getLvl() {
        return Lvl;
    }

    public void setLvl(int Lvl) {
        this.Lvl = Lvl;
    }

    public int getKDR() {
        return KDR;
    }

    public void setKDR(int KDR) {
        this.KDR = KDR;
    }

    public int getHighestKS() {
        return highestKS;
    }

    public void setHighestKS(int highestKS) {
        this.highestKS = highestKS;
    }

    public int getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(int checkpoints) {
        this.checkpoints = checkpoints;
    }

    public void addBounty(int amount) {
        bounty = bounty + amount;
    }

    public void addKills(Player k, int amount) {
        kills = kills + amount;
    }

    public void addDeaths(int amount) {
        deaths = deaths + amount;
    }

    public void addBalance(int amount) {
        balance = balance + amount;
    }

    public void addXPtoLvl(int amount) {
        XPtoLvl = XPtoLvl + amount;
    }

    public void addLvl(int amount) {
        Lvl = Lvl + amount;
    }

    public void addKDR(int amount) {
        KDR = KDR + amount;
    }

    public void addHighestKS(int amount) {
        highestKS = highestKS + amount;
    }

    public void addCheckpoints(int amount) {
        checkpoints = checkpoints + amount;
    }

    public void saveData() {
        //stuff access configs Manager
        //export all current player stats to file through configs manager
        //Player p = (Player) Bukkit.getOnlinePlayers();
        //Player player = p.getServer().getPlayer(p.getName());
        //UUID playerUUID = player.getUniqueId();
        //File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        //File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        //String pName = (ChatColor.BLUE + "" + ChatColor.BOLD + p.getName() + ChatColor.AQUA);
        //Player pl = p.getPlayer();
        configs.getPlayersFile().getConfigurationSection("Stats").set("Kills", pKills);
        configs.getPlayersFile().getConfigurationSection("Stats").set("Deaths", pDeaths);
        configs.getPlayersFile().getConfigurationSection("Stats").set("Bounty", pBounty);
        configs.saveOnPlayersToConfig();
    }

}
