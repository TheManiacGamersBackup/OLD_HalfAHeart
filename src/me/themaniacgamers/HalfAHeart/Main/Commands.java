package me.themaniacgamers.HalfAHeart.Main;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Corey on 4/2/2016.
 */
public class Commands {

    Main plugin;
    ConfigsManager configs = ConfigsManager.getInstance();

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    private String hahPrefix = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA + " ]=-");
    private String hahSuffix = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA + " ]=-");
    private String infoPrefix = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Information" + ChatColor.AQUA + " ]=-");

    @Command(aliases = "halfaheart", min = 0, max = 1, desc = "Base command for half a heart!", usage = "")
    public void onHah(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 0) {
                if (p.hasPermission("Hah.Version") || (p.hasPermission("Hah.*"))) {
                    sender.sendMessage(infoPrefix);
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Version: " + ChatColor.AQUA + " 0.2.001");
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Author: " + ChatColor.AQUA + " [TheManiacGamers]");
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Testers: " + ChatColor.AQUA + " [Khry]");
                    sender.sendMessage(hahSuffix);
                    return;
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have enough permissions to use this command! Contact the server owner if you think this is a mistake!");
                }
            }
            if (args.argsLength() == 1) {
                if (args.getString(0).equalsIgnoreCase("grenade")) {
                    ItemStack grenade = new ItemStack(Material.EGG);
                    ItemMeta grenadeItemMeta = grenade.getItemMeta();
                    String[] lores = {ChatColor.BLUE + "" + ChatColor.BOLD + "Throw this grenade at a player and blow them up!"};
                    grenadeItemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Grenade");
                    grenadeItemMeta.setLore(Arrays.asList(lores));
                    grenade.setItemMeta(grenadeItemMeta);
                    p.getInventory().addItem(grenade);
                    return;
                }
            }
            if (args.getString(0).equalsIgnoreCase("grenadeL")) {
                ItemStack grenadeL = new ItemStack(Material.EGG);
                ItemMeta grenadeLItemMeta = grenadeL.getItemMeta();
                String[] lores = {ChatColor.BLUE + "" + ChatColor.BOLD + "Throw this grenade at a player and blow them up!"};
                grenadeLItemMeta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Large Grenade");
                grenadeLItemMeta.setLore(Arrays.asList(lores));
                grenadeL.setItemMeta(grenadeLItemMeta);
                p.getInventory().addItem(grenadeL);
            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect Usage! Do /hah help for the help page!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        }

    }

    @Command(aliases = "setspawn", min = 0, max = 0, desc = "Set the spawn location for the server!", usage = "")
    public void onSetSpawn(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("Hah.Setspawn")) {
                Player p = (Player) sender;
                Location spawnLoc = p.getLocation();
                configs.getConfig().set("Spawn.World", spawnLoc.getWorld().getName());
                configs.getConfig().set("Spawn.X", spawnLoc.getX());
                configs.getConfig().set("Spawn.Y", spawnLoc.getY());
                configs.getConfig().set("Spawn.Z", spawnLoc.getZ());
                configs.getConfig().set("Spawn.Yaw", spawnLoc.getYaw());
                configs.getConfig().set("Spawn.Pitch", spawnLoc.getPitch());
                configs.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Spawn has been set to your location!");
            }
        }
    }

    @Command(aliases = "spawn", min = 0, max = 0, desc = "Set the spawn location for the server!", usage = "")
    public void onSpawn(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("Hah.Spawn") || sender.hasPermission("Hah.*") || sender.hasPermission("Hah.Donator")) {
                Player p = (Player) sender;
                String world = configs.getConfig().getString("Spawn.World");
                double x = configs.getConfig().getDouble("Spawn.X");
                double y = configs.getConfig().getDouble("Spawn.Y");
                double z = configs.getConfig().getDouble("Spawn.Z");
                double yaw = configs.getConfig().getDouble("Spawn.Yaw");
                double pitch = configs.getConfig().getDouble("Spawn.Pitch");
                Location spawn = new Location(p.getWorld(), x, y, z, (float) yaw, (float) pitch);
                p.teleport(spawn);
                // RETURN NOT NEEDED
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have enough permissions to execute this command!");
                // RETURN NOT NEEDED
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
            // RETURN NOT NEEDED
        }
    }

    @Command(aliases = "set", min = 1, max = 1, desc = "Set the checkpoints for the server!", usage = "<name>")
    public void onSetCheckpoints(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("Hah.Set")) {
                Player p = (Player) sender;
                if (args.argsLength() == 1) {
                    configs.getCheckpoints().set("Checkpoints." + args.getString(0) + ".World", p.getLocation().getWorld().getName());
                    configs.getCheckpoints().set("Checkpoints." + args.getString(0) + ".X", p.getLocation().getX());
                    configs.getCheckpoints().set("Checkpoints." + args.getString(0) + ".Y", p.getLocation().getY());
                    configs.getCheckpoints().set("Checkpoints." + args.getString(0) + ".Z", p.getLocation().getZ());
                    configs.getCheckpoints().set("Checkpoints." + args.getString(0) + ".Yaw", p.getLocation().getYaw());
                    configs.getCheckpoints().set("Checkpoints." + args.getString(0) + ".Pitch", p.getLocation().getPitch());
                    configs.saveCheckpoints();
                    p.sendMessage(ChatColor.GREEN + "You set the checkpoint: " + ChatColor.BOLD + "" + args.getString(0) + ChatColor.GREEN + "!");
                } else {
                    p.sendMessage(ChatColor.RED + "Incorrect Usage! Do /set <name>");
                    // RETURN NOT NEEDED
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have enough permission to execute this command!");
                // RETURN NOT NEEDED
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
            // RETURN NOT NEEDED
        }
    }

    @Command(aliases = "checkpoint", min = 1, max = 1, desc = "Teleports you to the specified checkpoint!", usage = "<name>")
    public void onCheckpointTP(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (sender.hasPermission("Hah.Checkpoints")) {
                if (args.argsLength() == 1) {
                    if (configs.getCheckpoints().getConfigurationSection("Checkpoints." + args.getString(0)) == null) {
                        p.sendMessage(ChatColor.RED + "Checkpoint " + args.getString(0) + " does not exist!");
                        return;
                    }
                    String world = configs.getCheckpoints().getString("Checkpoints." + args.getString(0) + ".World");
                    double x = configs.getCheckpoints().getDouble("Checkpoints." + args.getString(0) + ".X");
                    double y = configs.getCheckpoints().getDouble("Checkpoints." + args.getString(0) + ".Y");
                    double z = configs.getCheckpoints().getDouble("Checkpoints." + args.getString(0) + ".Z");
                    double yaw = configs.getCheckpoints().getDouble("Checkpoints." + args.getString(0) + ".Yaw");
                    double pitch = configs.getCheckpoints().getDouble("Checkpoints." + args.getString(0) + ".Pitch");
                    Location checkpoint = new Location(p.getWorld(), x, y, z, (float) yaw, (float) pitch);
                    p.teleport(checkpoint);
                    p.sendMessage(ChatColor.GREEN + "You were teleported to the " + ChatColor.BOLD + "" + args.getString(0) + ChatColor.GREEN + " checkpoint!");
                    // RETURN NOT NEEDED
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have enough permissions to execute this command!");
                // RETURN NOT NEEDED
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
            // RETURN NOT NEEDED
        }
    }

    @Command(aliases = "del", min = 0, max = 2, desc = "Set the checkpoints for the server!", usage = "<name>")
    public void onDelCheckpoints(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 1) {
                configs.getCheckpoints().set("Checkpoints." + args.getString(0), null);
                configs.saveCheckpoints();
                p.sendMessage(ChatColor.GREEN + "You deleted the checkpoint:  " + args.getString(0) + "!");
            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect Command! Do /del <name>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
        }
    }

    @Command(aliases = "bounty", min = 2, max = 2, desc = "Set a bounty on the desired player!", usage = "<username>")
    public void onBounty(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 2) {
                Player t = Bukkit.getPlayer(args.getString(0));
                if (!t.isOnline()) {
                    p.sendMessage(ChatColor.RED + "That player is not online!");
                } else {
                    addBounty(p, args.getString(1));
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "You need to specify a player! Do /bounty <username>");
            }
        }
    }

    public void addBounty(Player p, String count) {
        UUID playerUUID = p.getUniqueId();
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        File pFile = new File(dataBase, File.separator + playerUUID + ".yml");
        String pName = (ChatColor.BLUE + "" + ChatColor.BOLD + p.getName() + ChatColor.AQUA);
        final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
        int i = playerData.getConfigurationSection("Stats").getInt("Bounty");
        String a = count;
        playerData.set("Stats.Bounty", a + i);
        try {
            playerData.save(pFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.broadcastMessage(p.getName() + "'s file could not be saved! Error! Error!");
        }

    }
}