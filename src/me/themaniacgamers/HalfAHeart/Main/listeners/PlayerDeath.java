package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerDeath implements Listener {
    Main plugin;

    public PlayerDeath(Main plugin) {
        this.plugin = plugin;
    }

    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!(e.getEntity().getType() == EntityType.PLAYER) && (!(e.getEntity().getKiller().getType() == EntityType.PLAYER))) {
            return;
            // entity is not a player
        }

        Player p = e.getEntity().getPlayer();
        e.setDeathMessage(null);
        e.setKeepInventory(true);
        Player k = e.getEntity().getKiller();
        UUID playerUUID = p.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        String pName = (ChatColor.BLUE + "" + ChatColor.BOLD + p.getName() + ChatColor.AQUA);
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        UUID killerUUID = p.getUniqueId();
        File kFile = new File(dataBase, File.separator + killerUUID + ".yml");
        final FileConfiguration killerData = YamlConfiguration.loadConfiguration(kFile);
        if (k.hasPermission("Hah.Donator")) {
            if (k.getMaxHealth() != 2) {
                BountifulAPI.sendTitle(k.getPlayer(), 20, 20, 20, ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "III" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Blood Thirst" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "III", ChatColor.GREEN + "+ 1 heart!");
                k.setMaxHealth(2D);
                k.setHealth(2D);
                k.addPotionEffect(PotionEffectType.REGENERATION.createEffect(20, 2));
            }
        }
        if (p.hasPermission("Hah.Donator")) {
            p.setMaxHealth(2D);
            p.setHealth(2D);
            p.setFoodLevel(20);
            p.setFireTicks(0);
            p.getPlayer().performCommand("spawn");
            for (PotionEffect effect : p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        }
        if (p.getKiller() != null) {
            if (k.getPlayer().getName().equals(p.getName())) {
                p.sendMessage(strings.defaultMsgs + "You've committed suicide!");
                return;
            }
            if (p.getLevel() == 0) {
                return;
            } else {
                p.setLevel(p.getLevel() - 25);
                k.setLevel(k.getLevel() + 50);
            }
            BountifulAPI.sendActionBar(p.getPlayer(), ChatColor.RED + "" + ChatColor.BOLD + "You were killed by " + ChatColor.DARK_RED + "" + ChatColor.BOLD + k.getName() + ChatColor.RED + "" + ChatColor.BOLD + "!");
            BountifulAPI.sendActionBar(k.getPlayer(), ChatColor.GREEN + "" + ChatColor.BOLD + "You killed " + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + p.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
            p.sendMessage(strings.defaultMsgs + ChatColor.RED + "" + ChatColor.BOLD + "You were killed by " + ChatColor.DARK_RED + "" + ChatColor.BOLD + k.getName() + ChatColor.RED + "" + ChatColor.BOLD + "!");
            k.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "" + ChatColor.BOLD + "You killed " + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + p.getName() + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
            addKill(k, 1);
            addDeath(p, 1);
            try {
                playerData.save(pFile);
                killerData.save(kFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
//            PlayerData Bounty = PlayerData.pBounty.get(p.getUniqueId());

//            try {
//                Deaths.addDeaths(1);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                p.sendMessage(ChatColor.RED + "Deaths Problem");
//                return;
//            }
//            try {
//                Bounty.addBounty(1.0);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                p.sendMessage(ChatColor.RED + "Bounty Problem");
//                return;
//            }
        } else {
            Bukkit.broadcastMessage("UNKNOWN ERROR! RELOADING SERVER!");
            Player a = (Player) Bukkit.getOnlinePlayers();
            a.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "Server Reloading!");
            Bukkit.getServer().reload();
        }
    }
    // NATURAL DEATH

    public void addKill(Player p, double count) {
        UUID playerUUID = p.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        String pName = (ChatColor.BLUE + "" + ChatColor.BOLD + p.getName() + ChatColor.AQUA);
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        double i = playerData.getConfigurationSection("Stats").getDouble("Kills");
        double a = count;
        playerData.set("Stats.Kills", a + i);
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(p.getName() + "'s file could not be saved! Error! Error!");
        }

    }


    public void addDeath(Player p, double count) {
        UUID playerUUID = p.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        double i = playerData.getConfigurationSection("Stats").getDouble("Deaths");
        double a = count;
        playerData.set("Stats.Deaths", a + i);
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(p.getName() + "'s file could not be saved! Error! Error!");
        }

    }
}