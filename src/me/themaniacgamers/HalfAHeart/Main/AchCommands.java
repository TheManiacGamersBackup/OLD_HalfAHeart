package me.themaniacgamers.HalfAHeart.Main;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import me.themaniacgamers.HalfAHeart.Main.managers.ConfigsManager;
import me.themaniacgamers.HalfAHeart.Main.managers.StringsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corey on 4/9/2016.
 */
public class AchCommands {

    Main plugin;
    ConfigsManager configs = ConfigsManager.getInstance();
    StringsManager strings = StringsManager.getInstance();
    public static Inventory achInv = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Achievements");

    //private String hah = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA + " ]=-");
    //private String infoPrefix = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Information" + ChatColor.AQUA + " ]=-");

    public AchCommands(Main plugin) {
        this.plugin = plugin;
    }


    @Command(aliases = {"ach", "a", "achievements"}, desc = "View your acheivements that you've got left to unlock, and have unlocked!")
    public void achCommand(CommandContext args, CommandSender sender) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            // ITEMSTACKS FOR THE LOCKED ITEMS
            ItemStack TheManiacGamersLO = new ItemStack(Material.BEACON, 1);
            ItemMeta TheManiacGamersLOMeta = TheManiacGamersLO.getItemMeta();
            TheManiacGamersLOMeta.setDisplayName(ChatColor.GRAY + "^^ " + ChatColor.RED + "" + ChatColor.BOLD + "The-Maniac-Gamers" + ChatColor.GRAY + ">>>");
            List<String> TheManiacGamersLOLore = new ArrayList<String>();
            TheManiacGamersLOLore.add(ChatColor.DARK_PURPLE + "Kill TheManiacGamers!");
            TheManiacGamersLOLore.add(ChatColor.BLUE + "LOCKED - Kill 'TheManiacGaners' to unlock-");
            TheManiacGamersLOLore.add(ChatColor.BLUE + "this achievement!");
            TheManiacGamersLOMeta.setLore(TheManiacGamersLOLore);
            TheManiacGamersLO.setItemMeta(TheManiacGamersLOMeta);

            // SETTING ITEMS TO INV
            achInv.setItem(13, new ItemStack(TheManiacGamersLO));
            if (Main.playerStats.get(p.getUniqueId()).KillTheManiacGamers.equals("true")) {
                ItemStack TheManiacGamersUN = new ItemStack(Material.BEACON, 1);
                ItemMeta TheManiacGamersUNMeta = TheManiacGamersUN.getItemMeta();
                TheManiacGamersUNMeta.setDisplayName(ChatColor.GRAY + "^^ " + ChatColor.RED + "" + ChatColor.BOLD + "The-Maniac-Gamers" + ChatColor.GRAY + ">>>");
                List<String> TheManiacGamersUNLore = new ArrayList<String>();
                TheManiacGamersUNLore.add(ChatColor.DARK_PURPLE + "Kill TheManiacGamers!");
                TheManiacGamersUNLore.add(ChatColor.BLUE + "UNLOCKED - You've killed TheManiacGamers!");
                TheManiacGamersUNMeta.setLore(TheManiacGamersUNLore);
                TheManiacGamersUN.setItemMeta(TheManiacGamersUNMeta); // SETTING THE ITEM META DETA FOR KILL THEMANIACGAMERS UNLOCKED!
                achInv.setItem(13, new ItemStack(TheManiacGamersUN)); // SETTING ITEM FOR UNLOCKED KILL THEMANIACGAMERS!
                sender.sendMessage(ChatColor.GREEN + "You have killed TheManiacGamers :)");
            }
            p.openInventory(achInv);
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
        }
    }
}
