package me.themaniacgamers.HalfAHeart.Main.listeners;

import com.sk89q.minecraft.util.commands.ChatColor;
import me.themaniacgamers.HalfAHeart.Main.Main;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.stored.PlayerStats;
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
import java.util.UUID;

/**
 * Created by Corey on 4/2/2016.
 */
public class PlayerDeath implements Listener {
    Main plugin;
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();

    public PlayerDeath(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (!(e.getEntity().getType() == EntityType.PLAYER) && (!(e.getEntity().getKiller().getType() == EntityType.PLAYER))) {
            return;
            // entity is not a player
        }

        Player p = e.getEntity().getPlayer();
        e.setDeathMessage(null);
        e.setKeepLevel(true);
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
            if (k.getMaxHealth() != 4D) {
                BountifulAPI.sendTitle(k.getPlayer(), 20, 20, 20, ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "III" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Blood Thirst" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.MAGIC + "III", ChatColor.GREEN + "+ 1 heart!");
                k.setMaxHealth(4D);
                k.setHealth(4D);
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
            PlayerStats playerStats = Main.playerStats.get(p.getUniqueId());
            PlayerStats killerStats = Main.playerStats.get(k.getUniqueId());
            if (playerStats.bounty > 0) {
                killerStats.balance += 1000;
                playerStats.bounty = 0;
                Bukkit.broadcastMessage(strings.defaultMsgs + k.getDisplayName() + ChatColor.GOLD + "" + ChatColor.BOLD + " claimed the $1000 bounty on " + p.getDisplayName() + ChatColor.GOLD + "" + ChatColor.BOLD + "!");
                return;
            }
            BountifulAPI.sendTitle(p.getPlayer(), 20, 20, 20, ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "II" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "K.O" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "" + ChatColor.STRIKETHROUGH + "II", " ");
            k.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "You killed " + p.getDisplayName() + ChatColor.GREEN + "!");
            k.sendMessage(strings.defaultMsgs + ChatColor.GREEN + "You gained $5 and 5 strength, as well as 5 levels!");
            p.sendMessage(strings.defaultMsgs + ChatColor.RED + "You were killed by " + k.getDisplayName());
        } else {
            Bukkit.broadcastMessage("UNKNOWN ERROR! RELOADING SERVER!");
            Player a = (Player) Bukkit.getOnlinePlayers();
            a.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "Server Reloading!");
            Bukkit.getServer().reload();
        }
    }
}