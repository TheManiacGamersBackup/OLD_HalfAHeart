package me.themaniacgamers.HalfAHeart.managers;

import com.sk89q.minecraft.util.commands.ChatColor;

/**
 * Created by Corey on 4/3/2016.
 */
public class StringsManager {
    static StringsManager instance = new StringsManager();
    //STRINGS LIST
    public String developer = "TheManiacGamers";
    public String testers = "ILavaYou" + " TheManiacGamers" + " Rookie1200";
    public String hah = (ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA);
    public String hahTab = (ChatColor.AQUA + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA);
    public String join = (ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "+" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY);
    public String hahPrefix = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart" + ChatColor.AQUA + " ]=-");
    public String hahPrefixRanks = (ChatColor.AQUA + "-=[ " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart Ranks" + ChatColor.AQUA + " ]=-");
    public String errorLogPrefix = ("[HalfAHeart] [Error]");
    public String hahPvP = (ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart PvP");
    public String logPrefix = ("[HalfAHeart]");
    public String errorCreatePfile = (" Could not create the player file for");
    public String welcomeBack = (ChatColor.AQUA + "Welcome back to " + ChatColor.BLUE + "" + ChatColor.BOLD + "Half A Heart PvP" + ChatColor.AQUA + ", " + ChatColor.BLUE + "" + ChatColor.BOLD);
    public String aquaExclamation = (ChatColor.AQUA + "!");
    public String createdFile = (" Created the player file for ");
    public String loadedFile = (" Loaded the player file for ");
    public String firstJoinp1 = (ChatColor.BLUE + "" + ChatColor.BOLD + "Welcome" + ChatColor.AQUA + ", "); //pName;
    public String firstJoinp2 = (ChatColor.AQUA + "" + ChatColor.BOLD + " to" + ChatColor.GOLD + "" + ChatColor.BOLD + " RiftMC.us " + hahPvP + aquaExclamation);
    public String defaultMsgs = (ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[" + ChatColor.GREEN + "" + ChatColor.BOLD + "Server" + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "] " + ChatColor.GREEN);
    public String scoreboardTitle = (ChatColor.GOLD + "" + ChatColor.BOLD + "[" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Half" + ChatColor.RED + "" + ChatColor.BOLD + "A" + ChatColor.DARK_RED + "" + ChatColor.BOLD + "Heart" + ChatColor.GOLD + "" + ChatColor.BOLD + "]");
    public String thugKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Thug " + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "500 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String soldierKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Soldier" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "1500 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String hustlerKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Hustler" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "3000 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String bossKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Boss" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "4500 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String facilitatorKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Facilitator" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "6000 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String kingpinKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Kingpin" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "8000 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String publicenemyKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "Public Enemy" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "10000 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");
    public String godKills = (ChatColor.GREEN + "" + ChatColor.BOLD + "To reach " + ChatColor.AQUA + "" + ChatColor.BOLD + "God" + ChatColor.GREEN + "" + ChatColor.BOLD + ", you need to have " + ChatColor.AQUA + "" + ChatColor.BOLD + "15000 kills" + ChatColor.GREEN + "" + ChatColor.BOLD + "!");

    public static StringsManager getInstance() {
        return instance;
    }
}
