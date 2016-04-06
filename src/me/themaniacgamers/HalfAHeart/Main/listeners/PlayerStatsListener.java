package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.stored.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class PlayerStatsListener implements Listener {
    Main plugin;
    StringsManager strings = StringsManager.getInstance();

    public PlayerStatsListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player pl = event.getPlayer();
        final PlayerStats stats = new PlayerStats(pl, plugin);
        Main.playerStats.put(pl.getUniqueId(), stats);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new BukkitRunnable() {
            public void run() {
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                final Scoreboard board = manager.getNewScoreboard();
                final Objective objective = board.registerNewObjective("test", "dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(strings.scoreboardTitle);

                Score separator = objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "+-------------------+");
                separator.setScore(18);

                Score playerName = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">" + ChatColor.RED + "" + ChatColor.BOLD + " " + pl.getName() + ":");
                playerName.setScore(17);

                Score playerBalance = objective.getScore(ChatColor.GRAY + "Money: " + ChatColor.WHITE + "$" + stats.balance);
                playerBalance.setScore(16);

                Score playerLevel = objective.getScore(ChatColor.GRAY + "Level: " + ChatColor.WHITE + stats.level);
                playerLevel.setScore(15);

                Score playertoNxtLvl = objective.getScore(ChatColor.GRAY + "Exp To Next Lvl: " + ChatColor.WHITE + stats.xptonxtlevel);
                playertoNxtLvl.setScore(14);

                Score playerKills = objective.getScore(ChatColor.GRAY + "Kills: " + stats.kills);
                playerKills.setScore(13);

                Score playerDeaths = objective.getScore(ChatColor.GRAY + "Deaths: " + stats.deaths);
                playerDeaths.setScore(12);

                Score playerKDR = objective.getScore(ChatColor.GRAY + "KDR: " + ChatColor.WHITE + toKD(stats.kills, stats.deaths));
                playerKDR.setScore(11);

                Score playerHighestKS = objective.getScore(ChatColor.GRAY + "Highest KS: " + ChatColor.WHITE + stats.highestks);
                playerHighestKS.setScore(10);

                Score playerCheckpoints = objective.getScore(ChatColor.GRAY + "Checkpoints: " + ChatColor.WHITE + stats.checkpoints);
                playerCheckpoints.setScore(9);

                Score playerBounty = objective.getScore(ChatColor.GRAY + "Bounty On Head: " + ChatColor.WHITE + stats.bounty);
                playerBounty.setScore(8);

                Score emptyLine = objective.getScore("                            ");
                emptyLine.setScore(7);

                Score voteParty = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + "> " + ChatColor.YELLOW + "" + ChatColor.BOLD + "VoteParty:");
                voteParty.setScore(6);

                Score votesNeeded = objective.getScore(ChatColor.GRAY + "Votes Needed: " + ChatColor.WHITE + "Unknown");
                votesNeeded.setScore(5);

                pl.setScoreboard(board);
            }
        }, 0, 20 * 3);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getPlayer().getKiller();
        Player player = e.getEntity();
        PlayerStats kStats = Main.playerStats.get(killer.getUniqueId());
        PlayerStats pStats = Main.playerStats.get(player.getUniqueId());
        if (e.getEntity().getKiller().getType() == EntityType.PLAYER) {
            kStats.kills++;
            pStats.deaths--;
            kStats.killstreak++;
            pStats.killstreak = 0;
            kStats.balance += 5;
            kStats.xptonxtlevel += 5;

            //The fuck? Raise their level by 5 every time? Your code below:
            //killer.setLevel(killer.getLevel() + 5);
            //Fixed version is: kStats.level += 5;

            if (kStats.highestks < kStats.killstreak)
                kStats.highestks++;

            if (kStats.killstreak % 7 == 0) {
                Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
                Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
                if (kStats.killstreak == 7)
                    player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(100, 3));
            }
            if (kStats.killstreak % 10 == 0)
                Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " has reached a killstreak of 10!");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + e.getPlayer().getUniqueId() + ".yml");
        PlayerStats stats = Main.playerStats.get(e.getPlayer().getUniqueId());
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        playerData.getConfigurationSection("Stats").set("Kills", stats.kills);
        playerData.getConfigurationSection("Stats").set("Deaths", stats.deaths);
        playerData.getConfigurationSection("Options").set("Balance", stats.balance);
        playerData.getConfigurationSection("Stats").set("Bounty", stats.bounty);
        playerData.getConfigurationSection("Stats").set("Level", stats.level);
        playerData.getConfigurationSection("Stats").set("XPtoNxtLvl", stats.xptonxtlevel);
        playerData.getConfigurationSection("Stats").set("Checkpoints", stats.checkpoints);
        playerData.getConfigurationSection("Stats").set("Killstreak", stats.killstreak);
        playerData.getConfigurationSection("Stats").set("HighestKS", stats.highestks);
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(ChatColor.RED + "Couldn't save " + e.getPlayer().getName() + "'s stats file!");
        }
        Main.playerStats.remove(e.getPlayer().getUniqueId());
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
