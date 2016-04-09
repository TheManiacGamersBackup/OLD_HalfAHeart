package us.riftmc.htgan.halfAHeart.utils;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import io.puharesource.mc.titlemanager.api.TitleObject;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Message {

	public static String prefix;
	
	public static void setPrefix(String prefix) {
		Message.prefix = prefix;
	}
	public static void sendMessage(Player player, String msg) {
		player.sendMessage(prefix + ChatColor.GREEN + "" + ChatColor.BOLD + msg);
	}
	public static void sendWarning(Player player, String msg) {
		player.sendMessage(prefix + ChatColor.RED + "" + ChatColor.BOLD + msg);
	}
	public static void noPerm(Player player) {
		player.sendMessage(prefix + ChatColor.RED + "You do not have permission to do this.");
	}
	public static void sendDeath(Player player, Player killer) {
		player.sendMessage(prefix + ChatColor.RED + "You were killed by " + killer.getDisplayName() + ChatColor.RED + "!");
		killer.sendMessage(prefix + ChatColor.GREEN + "You killed " + player.getDisplayName() + ChatColor.GREEN + "!");
	}
	public static void sendKill(Player killer) {
		killer.sendMessage(prefix + ChatColor.GREEN + "You gained $5 and 5 Strength!");		
	}
	public static void sendKillVip(Player killer) {
		killer.sendMessage(prefix + ChatColor.GREEN + "You gained $7 and 7 Strength!");
	}
	public static void sendTitle(Player player, String title, int fadeIn, int stay, int fadeOut) {
		title = title.replace("&", "§");
		title = title.replace("_", " ");
		new TitleObject(title, TitleObject.TitleType.TITLE).setFadeIn(fadeIn).setStay(stay).setFadeOut(fadeOut).send(player);
	}
	public static void sendAction(Player player, String message) {
		message = message.replace("&", "§");
		message = message.replace("_", " ");
		new ActionbarTitleObject(message).send(player);
	}
	public static void bloodThirst(final Player player) {
		final String message = "§4§l§k!!!§4§lBlood Thirst§k!!!";
		new TitleObject(message, TitleObject.TitleType.TITLE).setFadeIn(8).setStay(5).setFadeOut(8).send(player);
	}
	public static void KO(final Player player) {
		final String message = "§4§l§k!!!§4§lK.O.§k!!!";
		new TitleObject(message, TitleObject.TitleType.TITLE).setFadeIn(20).setStay(40).setFadeOut(20).send(player);
	}
	public static void broadcastKS(final Player player, int killstreak) {
		final String message = "§4§l has reached kill streak of ";
		Bukkit.getServer().broadcastMessage(player.getDisplayName() + message + killstreak);
	}
	public static void rage(final Player victim){
		final String message = "§4§l§k!!!§4§lRAGE§k!!!";
		new TitleObject(message, TitleObject.TitleType.SUBTITLE).setFadeIn(10).setStay(15).setFadeOut(5).send(victim);
	}
	public static void rampage(final Player killer){
		final String message = "§4§l§k!!!§4§lRAMPAGE§k!!!";
		new TitleObject(message, TitleObject.TitleType.SUBTITLE).setFadeIn(10).setStay(15).setFadeOut(5).send(killer);
	}
}
