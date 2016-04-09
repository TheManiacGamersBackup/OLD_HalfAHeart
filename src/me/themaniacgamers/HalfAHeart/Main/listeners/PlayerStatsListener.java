package me.themaniacgamers.HalfAHeart.Main.listeners;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

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
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.sk89q.minecraft.util.commands.ChatColor;

import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.stored.PlayerStats;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;

public class PlayerStatsListener implements Listener {
    Main plugin;
    StringsManager strings = StringsManager.getInstance();

    public PlayerStatsListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                final Player pl = event.getPlayer();
                final PlayerStats stats = new PlayerStats(pl, plugin);
                Main.playerStats.put(pl.getUniqueId(), stats);
                Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    public void run() {
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        final Scoreboard board = manager.getNewScoreboard();
                        final Objective objective = board.registerNewObjective("test", "dummy");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                        objective.setDisplayName(strings.scoreboardTitle);

                        Score separator = objective.getScore(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "+-------------------+");
                        separator.setScore(19);

                        Score playerName = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">" + ChatColor.RED + "" + ChatColor.BOLD + " " + pl.getName() + ":");
                        playerName.setScore(18);

                        Score playerBalance = objective.getScore(ChatColor.GRAY + "Money: " + ChatColor.WHITE + "$" + stats.balance);
                        playerBalance.setScore(17);

                        Score playerLevel = objective.getScore(ChatColor.GRAY + "Level: " + ChatColor.WHITE + stats.level);
                        playerLevel.setScore(16);

                        Score playertoNxtLvl = objective.getScore(ChatColor.GRAY + "Exp To Next Lvl: " + ChatColor.WHITE + stats.xptonxtlevel);
                        playertoNxtLvl.setScore(15);

                        Score playerGroup = objective.getScore(ChatColor.GRAY + "Group: " + ChatColor.WHITE + stats.group);
                        playerGroup.setScore(14);

                        Score playerKills = objective.getScore(ChatColor.GRAY + "Kills: " + ChatColor.WHITE + stats.kills);
                        playerKills.setScore(13);

                        Score playerDeaths = objective.getScore(ChatColor.GRAY + "Deaths: " + ChatColor.WHITE + stats.deaths);
                        playerDeaths.setScore(12);

                        Score playerKDR = objective.getScore(ChatColor.GRAY + "KDR: " + ChatColor.WHITE + toKD(stats.kills, stats.deaths));
                        playerKDR.setScore(11);

                        Score playerHighestKS = objective.getScore(ChatColor.GRAY + "Highest KS: " + ChatColor.WHITE + stats.highestks);
                        playerHighestKS.setScore(10);

                        Score playerCheckpoints = objective.getScore(ChatColor.GRAY + "Checkpoints: " + ChatColor.WHITE + stats.checkpoints);
                        playerCheckpoints.setScore(9);

                        Score playerAchievements = objective.getScore(ChatColor.GRAY + "Achievements: " + ChatColor.WHITE + stats.achievementsam);
                        playerAchievements.setScore(8);

                        Score playerBounty = objective.getScore(ChatColor.GRAY + "Bounty On Head: " + ChatColor.WHITE + stats.bounty);
                        playerBounty.setScore(7);

                        Score emptyLine = objective.getScore("                            ");
                        emptyLine.setScore(6);

                        Score voteParty = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + "> " + ChatColor.YELLOW + "" + ChatColor.BOLD + "VoteParty:");
                        voteParty.setScore(5);

                        Score votesNeeded = objective.getScore(ChatColor.GRAY + "Votes Needed: " + ChatColor.WHITE + "Unknown");
                        votesNeeded.setScore(4);

                        pl.setScoreboard(board);

                    }
                }, 0, 20 * 3);
            }
        }, 40L);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!(e.getEntity().getType() == EntityType.PLAYER) && (!(e.getEntity().getKiller().getType() == EntityType.PLAYER))) {
            return;
            // cancel, not a player killing a player
        }
        Player killer = e.getEntity().getPlayer().getKiller();
        Player player = e.getEntity();
        PlayerStats pStats = Main.playerStats.get(player.getUniqueId());
        PlayerStats kStats = Main.playerStats.get(killer.getUniqueId());
        kStats.kills += 1; // kills
        pStats.deaths += 1; // deaths
        kStats.killstreak += 1; // kill streak
        pStats.killstreak = 0; // kill streak
        kStats.balance += 5; // money
        kStats.xptonxtlevel -= 5; // levels
        killer.setLevel(killer.getLevel() + 5); // strength

        if (kStats.xptonxtlevel == 0) {
            kStats.level += 1;
            kStats.xptonxtlevel += 100;
            killer.setLevel(killer.getLevel() + 50);
            kStats.balance += 100;
            BountifulAPI.sendTitle(killer, 20, 20, 20, ChatColor.GREEN + "" + ChatColor.BOLD + "" + ChatColor.BOLD + "Level Up!", ChatColor.GREEN + "You have reached level " + ChatColor.BOLD + "" + kStats.level + ChatColor.GREEN + ", congratulations!");
            killer.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "" + ChatColor.BOLD + "You have leveled up!");
            killer.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "" + ChatColor.BOLD + "You are now level " + kStats.level + "!");
            killer.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "" + ChatColor.BOLD + "You gained 1 level, 50 strength and 100 dollars!");
        }
        if (kStats.kills == 500) {
            //String user = killer.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Thug for reaching 500 kills!");
            kStats.group = "Thug";
        }
        if (kStats.kills == 1500) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Soldier for reaching 1500 kills!");
            kStats.group = "Soldier";
        }
        if (kStats.kills == 3000) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Hustler for reaching 3000 kills!");
            kStats.group = "Hustler";
        }
        if (kStats.kills == 4500) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Boss for reaching 4500 kills!");
            kStats.group = "Boss";
        }
        if (kStats.kills == 6000) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Facilitator for reaching 6000 kills!");
            kStats.group = "Facilitator";
        }
        if (kStats.kills == 8000) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Kingpin for reaching 8000 kills!");
            kStats.group = "Kingpin";
        }
        if (kStats.kills == 10000) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to Public Enemy for reaching 10000 kills!");
            kStats.group = "Public Enemy";
        }
        if (kStats.kills == 15000) {
            //String user = player.getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex promote " + killer.getName());
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has been promoted to God for reaching 15000 kills!");
            kStats.group = "God";
        }
        if (kStats.highestks < kStats.killstreak) {
            kStats.highestks += 1;
        }
        if (kStats.killstreak % 7 == 0) {
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.DARK_RED + " is dominating!");
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.DARK_RED + " is on a rampage!");
            killer.addPotionEffect(PotionEffectType.REGENERATION.createEffect(300, 3));
            killer.addPotionEffect(PotionEffectType.SPEED.createEffect(300, 3));
            killer.addPotionEffect(PotionEffectType.HEALTH_BOOST.createEffect(300, 3));
            killer.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(300, 3));
        }
        if (kStats.killstreak % 10 == 0) {
            Bukkit.broadcastMessage(strings.defaultMsgs + killer.getDisplayName() + ChatColor.DARK_RED + "" + ChatColor.BOLD + " is on a killstreak of " + kStats.killstreak + "!");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = (Player) e.getPlayer();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + e.getPlayer().getUniqueId() + ".yml");
        PlayerStats pStats = Main.playerStats.get(player.getUniqueId());
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        playerData.getConfigurationSection("Stats").set("Kills", pStats.kills);
        playerData.getConfigurationSection("Stats").set("Deaths", pStats.deaths);
        playerData.getConfigurationSection("Options").set("Balance", pStats.balance);
        playerData.getConfigurationSection("Options").set("Group", pStats.group);
        playerData.getConfigurationSection("Stats").set("Bounty", pStats.bounty);
        playerData.getConfigurationSection("Stats").set("Level", pStats.level);
        playerData.getConfigurationSection("Stats").set("XPtoNxtLvl", pStats.xptonxtlevel);
        playerData.getConfigurationSection("Stats").set("Checkpoints", pStats.checkpoints);
        playerData.getConfigurationSection("Stats").set("Killstreak", pStats.killstreak);
        playerData.getConfigurationSection("Stats").set("HighestKS", pStats.highestks);
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(ChatColor.RED + "Couldn't save " + e.getPlayer().getName() + "'s stats file!");
        }
        Main.playerStats.remove(e.getPlayer().getUniqueId());
    }

//    public void onGrenadeShooter(EntityDamageByEntityEvent e) {
//        if (e.getDamager() instanceof Projectile) {
//            if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
//                Projectile pr = (Projectile) e.getDamager();
//                if (pr.getShooter() instanceof Player) {
//                    Player killer = (Player) pr.getShooter();
//                    for (Entity nearby : pr.getNearbyEntities(5, 5, 5)) {
//                        if (!(nearby instanceof Player)) { // You can't damage entities
//                            continue;
//                        }
//                        Player nearbyp = (Player) nearby;
//                        PlayerStats pStats = Main.playerStats.get(nearbyp.getUniqueId());
//                        PlayerStats kStats = Main.playerStats.get(killer.getUniqueId());
//                        kStats.kills += 1;
//                        pStats.deaths += 1;
//                        kStats.killstreak += 1;
//                        pStats.killstreak = 0;
//                        kStats.balance += 5;
//                        kStats.xptonxtlevel -= 5;
//                        killer.setLevel(killer.getLevel() + 5);
//                        nearbyp.damage(10D);
//                        nearbyp.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You were blown up by a grenade!");
//                    }
//                }
//            }
//        }
//    }

    private String toKD(double kills, double deaths) {
        if (deaths == 0)
            return kills + "";
        return new DecimalFormat("#.##").format(kills / deaths);
    }
}
