package me.themaniacgamers.HalfAHeart.Main;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import me.themaniacgamers.HalfAHeart.Main.listeners.*;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import me.themaniacgamers.HalfAHeart.Main.utils.BountifulAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by TheManiacGamers on 4/2/2016.
 */
public class Main extends JavaPlugin implements Listener {
    protected ArrayList<Class> cmdClasses;
    public Main plugin;
    private CommandsManager<CommandSender> commands;
    public static Main instance;
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();
    public Location spawn = null;

    public static Main getInstance() {
        return instance;
    }

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
        pm.registerEvents(new PlayerStats(this), this);
        File dataBase = new File(plugin.getDataFolder(), File.separator + "PlayerDatabase");
        if (!(dataBase.exists())) {
            try {
                dataBase.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                log("COULD NOT CREATE THE DATABASE FILE! CREATE IT MANUALLY. plugins/HalfAHeart/PlayerDatabase/");
                Bukkit.shutdown();
            }
        }
        final org.bukkit.configuration.file.FileConfiguration config = this.getConfig();
        spawn = new Location(this.getServer().getWorld(config.getString("Spawn.World")),
                config.getDouble("Spawn.X"), config.getDouble("Spawn.Y"), config.getDouble("Spawn.Z"),
                (float) config.getDouble("Spawn.Y"), (float) config.getDouble("Spawn.Pitch"));
        log("Was enabled successfully!");
        //optional if statement
        //if(Bukkit.getOnlinePlayers().size() != 0) {
        //    for(Player player :Bukkit.getOnlinePlayers()) {
        //        PlayerData.loadedPlayer.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
        //    }
        //}
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            public void run() {
                for (Player a : Bukkit.getOnlinePlayers()) {
                    BountifulAPI.sendTabTitle(a.getPlayer(), (ChatColor.GOLD + "" + ChatColor.BOLD + "RiftMC.us"), (strings.hahTab));
                }
            }
        }, 60L, 60L);
    }

//    public void onReload() {
//        if (Bukkit.getOnlinePlayers().size() != 0) {
//            for(Player player : Bukkit.getOnlinePlayers()) {
//                PlayerData.loadedPlayer.put(player.getUniqueId(), new PlayerData(player.getUniqueId()));
//            }
//        }
//    }

    // FROM HERE ONWARDS IT'S ALL THE SK89Q COMMAND REGISTRATION//ETC//
// FROM HERE ONWARDS IT'S ALL THE SK89Q COMMAND REGISTRATION//ETC//
// FROM HERE ONWARDS IT'S ALL THE SK89Q COMMAND REGISTRATION//ETC//
// FROM HERE ONWARDS IT'S ALL THE SK89Q COMMAND REGISTRATION//ETC//
// FROM HERE ONWARDS IT'S ALL THE SK89Q COMMAND REGISTRATION//ETC//
// FROM HERE ONWARDS IT'S ALL THE SK89Q COMMAND REGISTRATION//ETC//
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

    public static void log(String message) {
        System.out.println("[HalfAHeart] " + message);

    }

    public boolean hasPerm(CommandSender sender, String perm) {
        return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
    }

    public void onDisable() {
        if (Bukkit.getOnlinePlayers().size() != 0) {
            Player a = (Player) Bukkit.getOnlinePlayers();
            a.kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "Server is being stopped! Call back shortly. If the server hasn't come back online for a bit, contact the owner!");
        }
    }
}