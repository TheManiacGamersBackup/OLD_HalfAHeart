package me.themaniacgamers.HalfAHeart.Main.listeners;

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
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by TheManiacGamers on 4/6/2016.
 */
public class PlayerStats implements Listener {

    Main plugin;

    public static HashMap<UUID, Integer> kills = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> deaths = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> killstreak = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> balance = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> bounty = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> level = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> xptonxtlevel = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> checkpoints = new HashMap<UUID, Integer>();
    public static HashMap<UUID, Integer> highestks = new HashMap<UUID, Integer>();

    StringsManager strings = StringsManager.getInstance();

    public PlayerStats(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                final Player p = e.getPlayer();
                final UUID playerUUID = p.getUniqueId();
                File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
                File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
                final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
                int pKills = playerData.getConfigurationSection("Stats").getInt("Kills");
                int pDeaths = playerData.getConfigurationSection("Stats").getInt("Deaths");
                int pHighestKS = playerData.getConfigurationSection("Stats").getInt("HighestKS");
                int pBalance = playerData.getConfigurationSection("Options").getInt("Balance");
                int pLevel = playerData.getConfigurationSection("Stats").getInt("Level");
                int pXpToNxtLvl = playerData.getConfigurationSection("Stats").getInt("XPtoNxtLvl");
                int pBounty = playerData.getConfigurationSection("Stats").getInt("Bounty");
                int pCheckpoints = playerData.getConfigurationSection("Stats").getInt("Checkpoints");
                int pKillstreak = playerData.getConfigurationSection("Stats").getInt("Killstreak");
                kills.put(p.getUniqueId(), pKills);
                deaths.put(p.getUniqueId(), pDeaths);
                balance.put(p.getUniqueId(), pBalance);
                bounty.put(p.getUniqueId(), pBounty);
                level.put(p.getUniqueId(), pLevel);
                xptonxtlevel.put(p.getUniqueId(), pXpToNxtLvl);
                checkpoints.put(p.getUniqueId(), pCheckpoints);
                killstreak.put(p.getUniqueId(), pKillstreak);
                highestks.put(p.getUniqueId(), pHighestKS);

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

                        Score playerBalance = objective.getScore(ChatColor.GRAY + "Money: " + ChatColor.WHITE + "$" + balance.get(playerUUID));
                        playerBalance.setScore(16);

                        Score playerLevel = objective.getScore(ChatColor.GRAY + "Level: " + ChatColor.WHITE + level.get(playerUUID));
                        playerLevel.setScore(15);

                        Score playertoNxtLvl = objective.getScore(ChatColor.GRAY + "Exp To Next Lvl: " + ChatColor.WHITE + xptonxtlevel.get(playerUUID));
                        playertoNxtLvl.setScore(14);

                        Score playerKills = objective.getScore(ChatColor.GRAY + "Kills: " + kills.get(playerUUID));
                        playerKills.setScore(13);

                        Score playerDeaths = objective.getScore(ChatColor.GRAY + "Deaths: " + deaths.get(playerUUID));
                        playerDeaths.setScore(12);

//            if (playerData.getConfigurationSection("Stats").getInt("Deaths") == 0) {
//                Score playerKDR1 = objective.getScore(ChatColor.GRAY + "KDR: " + ChatColor.WHITE + playerData.getConfigurationSection("Stats").getInt("Kills"));
//                playerKDR1.setScore(11);
//            } else {
//
//                Score playerKDR = objective.getScore(ChatColor.GRAY + "KDR: " + ChatColor.WHITE + toKD(kills, deaths));
//                playerKDR.setScore(11);
//            }
                        Score playerHighestKS = objective.getScore(ChatColor.GRAY + "Highest KS: " + ChatColor.WHITE + highestks.get(playerUUID));
                        playerHighestKS.setScore(10);

                        Score playerCheckpoints = objective.getScore(ChatColor.GRAY + "Checkpoints: " + ChatColor.WHITE + checkpoints.get(playerUUID));
                        playerCheckpoints.setScore(9);

                        Score playerBounty = objective.getScore(ChatColor.GRAY + "Bounty On Head: " + ChatColor.WHITE + bounty.get(playerUUID));
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
            }
        }, 60L);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getPlayer().getKiller();
        Player player = e.getEntity();
        if (e.getEntity().getKiller().getType() == EntityType.PLAYER) {
            // ADDING / REMOVING STATS FROM PLAYER / KILLER!
            kills.put(killer.getUniqueId(), kills.get(killer.getUniqueId()) + 1);
            deaths.put(player.getUniqueId(), deaths.get(player.getUniqueId()) + 1);
            killstreak.put(killer.getUniqueId(), killstreak.get(killer.getUniqueId()) + 1);
            killstreak.put(player.getUniqueId(), 0);
            balance.put(killer.getUniqueId(), balance.get(killer.getUniqueId()) + 5);
            xptonxtlevel.put(killer.getUniqueId(), xptonxtlevel.get(killer.getUniqueId()) + 5);
            killer.setLevel(killer.getLevel() + 5);

            if (highestks.get(killer.getUniqueId()) <= killstreak.get(killer.getUniqueId())) {
                highestks.put(killer.getUniqueId(), highestks.get(killer.getUniqueId()) + 1);
            }

            Player p = (Player) e.getEntity();
            Player k = (Player) e.getEntity().getKiller();
            if (killstreak.get(k.getUniqueId()) == 7) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
                p.addPotionEffect(PotionEffectType.REGENERATION.createEffect(100, 3));
            }
            if (killstreak.get(k.getUniqueId()) == 14) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 21) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 28) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 35) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 42) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 49) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 56) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 63) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 70) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 77) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 84) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 91) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 98) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 105) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 112) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 119) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 126) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 133) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 140) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            }
            if (killstreak.get(k.getUniqueId()) == 10) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 10!");
            }
            if (killstreak.get(k.getUniqueId()) == 20) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 20!");
            }
            if (killstreak.get(k.getUniqueId()) == 30) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 30!");
            }
            if (killstreak.get(k.getUniqueId()) == 40) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 40!");
            }
            if (killstreak.get(k.getUniqueId()) == 50) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 50!");
            }
            if (killstreak.get(k.getUniqueId()) == 60) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 60!");
            }
            if (killstreak.get(k.getUniqueId()) == 70) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 70!");
            }
            if (killstreak.get(k.getUniqueId()) == 80) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 80!");
            }
            if (killstreak.get(k.getUniqueId()) == 90) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 90!");
            }
            if (killstreak.get(k.getUniqueId()) == 100) {
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 100!");
            }
        }
    }


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID playerUUID = p.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        playerData.getConfigurationSection("Stats").set("Kills", kills.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("Deaths", deaths.get(p.getUniqueId()));
        playerData.getConfigurationSection("Options").set("Balance", balance.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("Bounty", bounty.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("Level", level.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("XPtoNxtLvl", xptonxtlevel.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("Checkpoints", checkpoints.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("Killstreak", killstreak.get(p.getUniqueId()));
        playerData.getConfigurationSection("Stats").set("HighestKS", highestks.get(p.getUniqueId()));
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(ChatColor.RED + "Couldn't save " + p.getName() + "'s stats file!");
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
