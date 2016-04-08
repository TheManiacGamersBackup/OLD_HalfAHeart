package me.themaniacgamers.HalfAHeart.Main;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandNumberFormatException;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.stored.PlayerStats;
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
import java.util.Arrays;

/**
 * Created by Corey on 4/2/2016.
 */
public class Commands {

    Main plugin;
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();
    private String hah = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA + " ]=-");
    private String infoPrefix = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Information" + ChatColor.AQUA + " ]=-");

    public Commands(Main plugin) {
        this.plugin = plugin;
    }

    @Command(aliases = "halfaheart", max = 1, desc = "Base command for half a heart!")
    public void onHah(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 0) {
                if (p.hasPermission("Hah.Version") || (p.hasPermission("Hah.*"))) {
                    sender.sendMessage(infoPrefix);
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Version: " + ChatColor.AQUA + " 0.2");
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Author: " + ChatColor.AQUA + " [TheManiacGamers]");
                    sender.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Testers: " + ChatColor.AQUA + " [Rookie1200], [Khry]");
                    sender.sendMessage(hah);
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

    @Command(aliases = "setspawn", max = 0, desc = "Set the spawn location for the server!")
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

    @Command(aliases = "spawn", max = 0, desc = "Set the spawn location for the server!")
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

    @Command(aliases = "del", max = 2, desc = "Set the checkpoints for the server!", usage = "<name>")
    public void onDelCheckpoints(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 1) {
                configs.getCheckpoints().set("Checkpoints." + args.getString(0), null);
                configs.saveCheckpoints();
                p.sendMessage(ChatColor.GREEN + "You were deleted the " + ChatColor.BOLD + "" + args.getString(0) + ChatColor.GREEN + "!");
            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect Command! Do /del <name>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
        }
    }

    @Command(aliases = "bounty", min = 1, max = 2, desc = "Set a bounty of 1000 on the desired player!", usage = "<username> <amount>")
    public void onBounty(CommandContext args, CommandSender sender) throws CommandNumberFormatException {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 1) {
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Sorry, but bounty can only go up in 1000! Do /bounty <username>");
                return;
            }
            if (args.argsLength() == 2) {
                Player t = Bukkit.getPlayer(args.getString(0));
                if (!t.isOnline()) {
                    p.sendMessage(ChatColor.RED + "That player is not online!");
                } else {
                    PlayerStats senderStats = Main.playerStats.get(p.getUniqueId());
                    PlayerStats targetStats = Main.playerStats.get(t.getUniqueId());
                    if (senderStats.balance >= 1) {
                        if (args.getInteger(1) <= senderStats.balance) {
                            targetStats.bounty += args.getInteger(1);
                            senderStats.balance -= args.getInteger(1);
                            Bukkit.broadcastMessage(strings.defaultMsgs + t.getDisplayName() + ChatColor.YELLOW + "" + ChatColor.BOLD + " has now got a bounty of " + ChatColor.GOLD + "" + ChatColor.BOLD + targetStats.bounty);
                        } else {
                            sender.sendMessage(strings.defaultMsgs + ChatColor.RED + "You do not have " + args.getInteger(1) + " to bounty this player. Try a different amount!");
                        }
                    } else {
                        sender.sendMessage(strings.defaultMsgs + ChatColor.RED + "You need 1 dollar to be able to put a bounty on a player!");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + "You need to specify a player! Do /bounty <username>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
        }
    }

    @Command(aliases = "money", min = 1, max = 2, desc = "Gives a player an amount of money!", usage = "<username> <amount>")
    public void onMoneyGive(CommandContext args, CommandSender sender) throws CommandNumberFormatException {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.argsLength() == 2) {
                if (p.hasPermission("Hah.Money")) {
                    Player t = Bukkit.getPlayer(args.getString(0));
                    if (t.isOnline()) {
                        Main.playerStats.get(t.getUniqueId()).balance += args.getInteger(1);
                        t.sendMessage(p.getDisplayName() + ChatColor.GREEN + "" + ChatColor.BOLD + " has given you " + ChatColor.BLUE + "" + ChatColor.BOLD + args.getInteger(1) + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
                        p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You gave " + t.getDisplayName() + ChatColor.BLUE + " " + ChatColor.BOLD + args.getInteger(1) + ChatColor.GREEN + ChatColor.BOLD + "!");
                    } else {
                        sender.sendMessage(ChatColor.RED + "That player is not online!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You do not have enough permissions to execute this command!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Incorrect Usage! Do /money <username> <amount>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command!");
        }
    }

    @Command(aliases = "stop", max = 0, desc = "Kicks all online players then stops the server")
    public void onServerStop(CommandContext args, CommandSender sender) {
        if (sender.hasPermission("Hah.Stop")) {
            if (Bukkit.getOnlinePlayers().size() != 0) {
                for (final Player found : Bukkit.getOnlinePlayers()) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Bukkit.broadcastMessage("[Server] Server being stopped now!");
                            Bukkit.shutdown();
                        }
                    }, 40L);
                }
            } else {
                Bukkit.broadcastMessage("[Server] Shutting down!");
                Bukkit.shutdown();
            }

        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
        }
    }

    @Command(aliases = "ranks", max = 0, desc = "Shows the ranking system for Half A Heart!")
    public void onRanks(CommandContext args, CommandSender sender) {
        if (sender.hasPermission("Hah.Ranks")) {
            sender.sendMessage(strings.hahPrefixRanks);
            sender.sendMessage(strings.thugKills);
            sender.sendMessage(strings.soldierKills);
            sender.sendMessage(strings.hustlerKills);
            sender.sendMessage(strings.bossKills);
            sender.sendMessage(strings.facilitatorKills);
            sender.sendMessage(strings.kingpinKills);
            sender.sendMessage(strings.publicenemyKills);
            sender.sendMessage(strings.godKills);
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
        }
    }
}
