package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerJoin implements Listener {
    Main plugin;

    public PlayerJoin(Main plugin) {
        this.plugin = plugin;
    }

    public HashMap<Player, String> stats = new HashMap<Player, String>();
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();

    double kills;
    double deaths;

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        if (p.hasPermission("Hah.Donator") || p.isOp()) {
            e.getPlayer().setMaxHealth(2D);
            e.getPlayer().setHealth(2D);
        } else {
            e.getPlayer().setMaxHealth(1D);
            e.getPlayer().setHealth(1D);
        }
        e.getPlayer().performCommand("spawn");

        PlayerData Bounty = PlayerData.pBounty.get(p.getUniqueId());
        PlayerData.pBounty.put(p.getUniqueId(), Bounty);
        PlayerData Kills = PlayerData.pKills.get(p.getUniqueId());
        PlayerData.pKills.put(p.getUniqueId(), Kills);
        PlayerData Deaths = PlayerData.pDeaths.get(p.getUniqueId());
        PlayerData.pDeaths.put(p.getUniqueId(), Deaths);

//don't use this code ---
//        PlayerData joinedPlayer = new PlayerData(p.getUniqueId());
//        PlayerData.loadedPlayer.put(e.getPlayer().getUniqueId(), joinedPlayer);
//        PlayerData bountyVictim = PlayerData.loadedPlayer.get(e.getPlayer().getUniqueId());
//        bountyVictim.addBounty(+1000);
//don't use the above code
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
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {

                ScoreboardManager manager = Bukkit.getScoreboardManager();
                final Scoreboard board = manager.getNewScoreboard();
                final Objective objective = board.registerNewObjective("test", "dummy");

                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(strings.scoreboardTitle);

                Score separator = objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "+-------------------+");
                separator.setScore(18);

                Score playerName = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">" + ChatColor.RED + "" + ChatColor.BOLD + " " + p.getName() + ":");
                playerName.setScore(17);

                Score playerBalance = objective.getScore(ChatColor.GRAY + "Money: " + ChatColor.WHITE + playerData.getConfigurationSection("Options").getInt("Balance"));
                playerBalance.setScore(16);

                Score playerLevel = objective.getScore(ChatColor.GRAY + "Level: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Level"));
                playerLevel.setScore(15);

                Score playertoNxtLvl = objective.getScore(ChatColor.GRAY + "Exp To Next Lvl: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("XPtoNxtLvl"));
                playertoNxtLvl.setScore(14);

                Score playerKills = objective.getScore(ChatColor.GRAY + "Kills: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Kills"));
                playerKills.setScore(13);

                Score playerDeaths = objective.getScore(ChatColor.GRAY + "Deaths: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Deaths"));
                playerDeaths.setScore(12);

                if (playerData.getConfigurationSection("Stats").getInt("Deaths") == 0) {
                    Score playerKDR1 = objective.getScore(ChatColor.GRAY + "KDR: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Kills"));
                    playerKDR1.setScore(11);
                } else {

                    Score playerKDR = objective.getScore(ChatColor.GRAY + "KDR: " + ChatColor.WHITE + toKD(kills, deaths));
                    playerKDR.setScore(11);
                }
                Score playerHighestKS = objective.getScore(ChatColor.GRAY + "Highest KS: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("HighestKS"));
                playerHighestKS.setScore(10);

                Score playerCheckpoints = objective.getScore(ChatColor.GRAY + "Checkpoints: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Checkpoints"));
                playerCheckpoints.setScore(9);

                Score playerBounty = objective.getScore(ChatColor.GRAY + "Bounty On Head: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Bounty"));
                playerBounty.setScore(8);

                Score emptyLine = objective.getScore("                            ");
                emptyLine.setScore(7);

                Score voteParty = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + "> " + ChatColor.YELLOW + "" + ChatColor.BOLD + "VoteParty:");
                voteParty.setScore(6);

                Score votesNeeded = objective.getScore(ChatColor.GRAY + "Votes Needed: " + ChatColor.WHITE + "Unknown");
                votesNeeded.setScore(5);

                p.setScoreboard(board);
            }
        }, 0, 20 * 3);
        if (pFile.exists()) {
            p.sendMessage(strings.welcomeBack + p.getName() + strings.aquaExclamation);
            System.out.println(strings.logPrefix + strings.loadedFile + p.getUniqueId() + " (" + pName + ")!");
        } else {
            try {
                pFile.createNewFile();
                System.out.println(strings.hah + strings.createdFile + p.getUniqueId() + " (" + p.getName() + ")!");
                Bukkit.broadcastMessage(strings.firstJoinp1 + pName + strings.firstJoinp2);
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
                playerData.set("Options.Group", "Player");
                playerData.set("Options.Username", p.getName());
                playerData.set("Options.Balance", 100);
                playerData.set("Stats.XPtoNxtLvl", 100);
                playerData.set("Stats.Kills", 0.0);
                playerData.set("Stats.Deaths", 0.0);
                playerData.set("Stats.HighestKS", 0);
                playerData.set("Stats.Bounty", 0);
                playerData.set("Stats.Level", 0);
                playerData.set("Stats.Checkpoints", 0);
                try {
                    playerData.save(pFile);
                } catch (IOException ex) {
                    System.out.println("[HalfAHeart] [Error] COULD NOT SAVE THE PLAYER FILE!");
                }
            } catch (IOException ex) {
                //do nothing
            }
        }
    }

    private String toKD(double kills, double deaths) {
        if (deaths == 0) {
            return kills + "";
        }
        double kd = kills / deaths;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(kd);
    }
}