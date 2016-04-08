package me.themaniacgamers.HalfAHeart.Main;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import me.themaniacgamers.HalfAHeart.Main.listeners.*;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.stored.PlayerStats;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by TheManiacGamers on 4/2/2016.
 */
public class Main extends JavaPlugin implements Listener {
    public static Main instance;
    public Main plugin;
    public Location spawn = null;
    protected ArrayList<Class> cmdClasses;
    public static HashMap<UUID, PlayerStats> playerStats;
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();
    private CommandsManager<CommandSender> commands;

    public static Main getInstance() {
        return instance;
    }

    public static void log(String message) {
        System.out.println("[HalfAHeart] " + message);
    }

//    public void onReload() {
//        if (Bukkit.getOnlinePlayers().size() != 0) {
//            for(Player player : Bukkit.getOnlinePlayers()) {
//                PlayerData.loadedPlayer.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
//            }
//        }
//    }

    public void onEnable() {
        plugin = this;
        configs.setup(plugin);
        PluginManager pm = getServer().getPluginManager();
        registerCommandClass(Commands.class);
        registerCommands();
        pm.registerEvents(new PlayerChat(this), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new PlayerFall(this), this);
        pm.registerEvents(new PlayerFood(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerLeave(this), this);
        pm.registerEvents(new PlayerMovement(this), this);
        pm.registerEvents(new PlayerAttacked(this), this);
        pm.registerEvents(new PlayerStatsListener(this), this);
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        if (!(dataBase.exists())) {
            try {
                if (dataBase.createNewFile()) ; //...?
            } catch (IOException ex) {
                ex.printStackTrace();

                //lol a yaml file isn't a database
                log("COULD NOT CREATE THE DATABASE FILE! CREATE IT MANUALLY. plugins/HalfAHeart/PlayerDatabase/");
                Bukkit.shutdown();
            }
        }
        final org.bukkit.configuration.file.FileConfiguration config = this.getConfig();
        spawn = new Location(this.getServer().getWorld(config.getString("Spawn.World")),
                config.getDouble("Spawn.X"), config.getDouble("Spawn.Y"), config.getDouble("Spawn.Z"),
                (float) config.getDouble("Spawn.Y"), (float) config.getDouble("Spawn.Pitch"));
        log("Enabled successfully!");
        /*optional if statement
        if(Bukkit.getOnlinePlayers().size() != 0) {
            for(Player player :Bukkit.getOnlinePlayers()) {
                PlayerData.loadedPlayer.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
            }
        }*/
        playerStats = new HashMap<>();
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    BountifulAPI.sendTabTitle(a.getPlayer(), (ChatColor.GOLD + "" + ChatColor.BOLD + "RiftMC.us"), (strings.hahTab));
                }
            }
        }, 60L, 60L);
    }

    public void onDisable() {
        if (Bukkit.getOnlinePlayers().size() != 0) {
            for (Player found : Bukkit.getOnlinePlayers()) {
                final PlayerStats stats = new PlayerStats(found, plugin);
                File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
                File pFile = new File(dataBase, File.separator + found.getPlayer().getUniqueId() + ".yml");
                final FileConfiguration playerData = YamlConfiguration.loadConfiguration(pFile);
                playerData.getConfigurationSection("Stats").set("Kills", stats.kills);
                playerData.getConfigurationSection("Stats").set("Deaths", stats.deaths);
                playerData.getConfigurationSection("Options").set("Balance", stats.balance);
                playerData.getConfigurationSection("Options").set("Group", stats.group);
                playerData.getConfigurationSection("Stats").set("Bounty", stats.bounty);
                playerData.getConfigurationSection("Stats").set("Level", stats.level);
                playerData.getConfigurationSection("Stats").set("XPtoNxtLvl", stats.xptonxtlevel);
                playerData.getConfigurationSection("Stats").set("Checkpoints", stats.checkpoints);
                playerData.getConfigurationSection("Stats").set("Killstreak", stats.killstreak);
                playerData.getConfigurationSection("Stats").set("HighestKS", stats.highestks);
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {

            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {

                sender.sendMessage(ChatColor.RED + "You need to enter a number!");
            } else {
                sender.sendMessage(ChatColor.RED + "Error occurred, contact developer. [TheManiacGamers]");
                sender.sendMessage(ChatColor.RED + "Message: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    protected void registerCommandClass(Class cmdClass) {
        if (cmdClasses == null)
            cmdClasses = new ArrayList<Class>();

        cmdClasses.add(cmdClass);
    }

    protected void registerCommands() {
        if (cmdClasses == null || cmdClasses.size() < 1) {

            log("Could not register commands! Perhaps you registered no classes?");
            return;
        }

        // Register the commands that we want to use
        commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender player, String perm) {
                return getInstance().hasPerm(player, perm);
            }


        };
        commands.setInjector(new SimpleInjector(this));
        final CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, commands);

        for (Class cmdClass : cmdClasses)
            cmdRegister.register(cmdClass);
    }

    public boolean hasPerm(CommandSender sender, String perm) {
        return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
    }
}