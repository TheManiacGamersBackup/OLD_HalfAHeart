package me.themaniacgamers.HalfAHeart.Main.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.sk89q.minecraft.util.commands.ChatColor;

import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.stored.PlayerStats;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;

/**
 * Created by Corey on 4/9/2016.
 */
public class PlayerAchListener implements Listener {
    Main plugin;

    public PlayerAchListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getName().equals("TheManiacGamers")) {
            if (e.getEntity().getKiller() != null) {
                Player k = (Player) e.getEntity().getPlayer().getKiller();
                PlayerStats pStats = Main.playerStats.get(k.getUniqueId());
                if (!(pStats.KillTheManiacGamers == "true")) {
                    Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "ACHIEVEMENTS" + ChatColor.RESET +
                            ChatColor.DARK_GRAY + "»");
                    Bukkit.broadcastMessage("    " + k.getDisplayName() + ChatColor.GRAY + " " +
                            "unlocked the achievement: " + ChatColor.RED + "Kill TheManiacGamers");
                    BountifulAPI.sendTitle(k.getPlayer(), 20, 20, 20, ChatColor.RED + "" + ChatColor.BOLD + "ACHIEVEMENTS »", ChatColor.GREEN + "" + ChatColor.BOLD + "Unlocked - The-Maniac-Gamers!");
                    Firework fw = (Firework) k.getWorld().spawnEntity(k.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    Random r = new Random();

                    int rt = r.nextInt(4) + 1;
                    FireworkEffect.Type type = FireworkEffect.Type.BALL;
                    if (rt == 1) type = FireworkEffect.Type.BALL;
                    if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
                    if (rt == 3) type = FireworkEffect.Type.BURST;
                    if (rt == 4) type = FireworkEffect.Type.CREEPER;
                    if (rt == 5) type = FireworkEffect.Type.STAR;

                    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA).withFade(Color.BLUE).with(type).trail(r.nextBoolean()).build();

                    //Then apply the effect to the meta
                    fwm.addEffect(effect);

                    //Generate some random power and set it
                    int rp = r.nextInt(2) + 1;
                    fwm.setPower(rp);

                    //Then apply this to our rocket
                    fw.setFireworkMeta(fwm);
                    PlayerStats pAchs = Main.playerStats.get(k.getUniqueId());
                    pAchs.KillTheManiacGamers = "true";
                    pAchs.achievementsam += 1;
                }
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        Player player = (Player) e.getPlayer();
        if (player.getName() == "TheManiacGamers") {
            PlayerStats pAchs = Main.playerStats.get(player.getUniqueId());
            if (pAchs.KillTheManiacGamers == "false") {
                pAchs.achievementsam += 1;
                pAchs.KillTheManiacGamers = "true";
                player.sendMessage(ChatColor.GREEN + "Seeing you are TheManiacGamers, and you cannot kill yourself, I will give you the achievement 'Kill TheManiacGamers' for free!");
            }
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                final Player pl = e.getPlayer();
                final PlayerStats stats = new PlayerStats(pl, plugin);
                Main.playerStats.put(pl.getUniqueId(), stats);
                PlayerStats pAchs = Main.playerStats.get(pl.getUniqueId());
                if (pAchs.Join50Times.equals("true")) {
                    // do nothing, player already has achievement.
                    return;
                }
                if (pAchs.iJoin50Times == 49) {
                    pAchs.iJoin50Times += 1;
                    Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "ACHIEVEMENTS" + ChatColor.RESET +
                            ChatColor.DARK_GRAY + "»");
                    Bukkit.broadcastMessage("    " + pl.getDisplayName() + ChatColor.GRAY + " " +
                            "unlocked the achievement: " + ChatColor.RED + "Join 50 Times!");
                    BountifulAPI.sendTitle(pl.getPlayer(), 20, 20, 20, ChatColor.RED + "" + ChatColor.BOLD + "ACHIEVEMENTS »", ChatColor.GREEN + "" + ChatColor.BOLD + "Unlocked - Join 50 Times!");
                    Firework fw = (Firework) pl.getWorld().spawnEntity(pl.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    Random r = new Random();

                    int rt = r.nextInt(4) + 1;
                    FireworkEffect.Type type = FireworkEffect.Type.BALL;
                    if (rt == 1) type = FireworkEffect.Type.BALL;
                    if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
                    if (rt == 3) type = FireworkEffect.Type.BURST;
                    if (rt == 4) type = FireworkEffect.Type.CREEPER;
                    if (rt == 5) type = FireworkEffect.Type.STAR;

                    FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.AQUA).withFade(Color.BLUE).with(type).trail(r.nextBoolean()).build();

                    //Then apply the effect to the meta
                    fwm.addEffect(effect);

                    //Generate some random power and set it
                    int rp = r.nextInt(2) + 1;
                    fwm.setPower(rp);

                    //Then apply this to our rocket
                    fw.setFireworkMeta(fwm);
                    pAchs.Join50Times = "true";
                    pAchs.achievementsam += 1;
                    return;
                }
                pAchs.iJoin50Times += 1;
            }
        }, 40L);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = (Player) e.getPlayer();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + e.getPlayer().getUniqueId() + ".yml");
        PlayerStats pAchs = Main.playerStats.get(player.getUniqueId());
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        playerData.getConfigurationSection("Stats").set("Achievements.KillTheManiacGamers", pAchs.KillTheManiacGamers);
        playerData.getConfigurationSection("Stats").set("Achievements.KillHtgan", pAchs.KillHtgan);
        playerData.getConfigurationSection("Stats").set("Achievements.Reach10Kills", pAchs.Reach10Kills);
        playerData.getConfigurationSection("Stats").set("Achievements.Reach50Kills", pAchs.Reach50Kills);
        playerData.getConfigurationSection("Stats").set("Achievements.ReachThug", pAchs.ReachThug);
        playerData.getConfigurationSection("Stats").set("Achievements.Join50Times", pAchs.Join50Times);
        playerData.getConfigurationSection("Stats").set("Achievements.Join100Times", pAchs.Join100Times);
        playerData.getConfigurationSection("Stats").set("Achievements.Join150Times", pAchs.Join150Times);
        playerData.getConfigurationSection("Stats").set("Achievements.Join200Times", pAchs.Join200Times);
        playerData.getConfigurationSection("Stats").set("Achievements.ReachAKSOf20", pAchs.ReachAKSOf20);
        playerData.getConfigurationSection("Stats").set("Achievements.ReachGod", pAchs.ReachGod);
        playerData.getConfigurationSection("Stats").set("Achievements.HasAllCheckpoints", pAchs.FindAllCheckpoints);
        playerData.getConfigurationSection("Stats").set("Count.KillTheManiacGamers", pAchs.iKillTheManiacGamers);
        playerData.getConfigurationSection("Stats").set("Count.KillHtgan", pAchs.iKillHtgan);
        playerData.getConfigurationSection("Stats").set("Count.Reach10Kills", pAchs.iReach10Kills);
        playerData.getConfigurationSection("Stats").set("Count.Reach50Kills", pAchs.iReach50Kills);
        playerData.getConfigurationSection("Stats").set("Count.ReachThug", pAchs.iReachThug);
        playerData.getConfigurationSection("Stats").set("Count.Join50Times", pAchs.iJoin50Times);
        playerData.getConfigurationSection("Stats").set("Count.Join100Times", pAchs.iJoin100Times);
        playerData.getConfigurationSection("Stats").set("Count.Join150Times", pAchs.iJoin150Times);
        playerData.getConfigurationSection("Stats").set("Count.Join200Times", pAchs.iJoin200Times);
        playerData.getConfigurationSection("Stats").set("Count.ReachAKSOf20", pAchs.iReachAKSOf20);
        playerData.getConfigurationSection("Stats").set("Count.ReachGod", pAchs.iReachGod);
        playerData.getConfigurationSection("Stats").set("Count.HasAllCheckpoints", pAchs.iFindAllCheckpoints);
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(ChatColor.RED + "Couldn't save " + e.getPlayer().getName() + "'s stats file!");
        }
        Main.playerStats.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {
            // do nothing
        } else {
            if (e.getClickedInventory().getName().startsWith("Achievements")) {
                e.setCancelled(true);
            }
        }
    }
}