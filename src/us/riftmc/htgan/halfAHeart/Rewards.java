package us.riftmc.htgan.halfAHeart;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.themaniacgamers.HalfAHeart.Main;

public class Rewards {
	public static boolean balance(Player player, double cost) {
		if (Main.econ.hasAccount(player)
				&& Main.econ.getBalance(player) >= cost) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void withdraw(Player player, double cost) {
			Main.econ.withdrawPlayer(player, cost);
			return;
	}

	public static boolean checkXPL(Player player) {
			if ((player.getLevel() < 1)) {
				return false;
			}
			return true;
	}
	
	public static void giveXPL(Player player, int amount) {
		player.giveExpLevels(amount);
		return;
	}

	public static void rewardXP(Player player, int amount) {
		player.giveExp(amount);
		return;
	}

	public static void rewardMoney(Player player, double amount) {
		if (Main.econ.hasAccount(player)) {
			Main.econ.depositPlayer(player, amount);
		} else {
			Bukkit.getServer()
					.broadcastMessage(
							"Something has gone horribly wrong! Specified player does not have an economy account!");
			return;
		}
	}
}
