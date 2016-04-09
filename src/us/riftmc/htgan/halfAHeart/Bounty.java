package us.riftmc.htgan.halfAHeart;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.themaniacgamers.HalfAHeart.Main;
import us.riftmc.htgan.halfAHeart.utils.Message;

public class Bounty {
	public static HashMap<UUID, Double> bounty = new HashMap<UUID, Double>();
	public void setBounty(Player player, Player target, Double reward) {
		if(Rewards.balance(player, reward)) {
			Rewards.withdraw(player, reward);
			if(bounty.get(target.getUniqueId()) == (double) 0) {
				bounty.put(target.getUniqueId(), reward);
				Bukkit.getServer().broadcastMessage("§e§lA bounty of $" + reward + " §e§lhas been placed on " + target.getDisplayName() + "§e§l!");
				return;
			}else {
				Double newReward = reward + bounty.get(target.getUniqueId());
				Bukkit.getServer().broadcastMessage("§e§lThere is now a $" + newReward + " §e§lbounty on " + target.getDisplayName() + "§e§l's head!");
				bounty.put(target.getUniqueId(), newReward);
				return;
			}
		}else {
			Message.sendWarning(player, "You can't afford that bounty!");
			return;
		}
	}
	public static void loadBounty(Player player) {
		File pfile = new File(Main.getInstance().getDataFolder() + File.separator + "players" + File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(pfile);		
		bounty.put(player.getUniqueId(), fc.getDouble("Statistics.Bounty"));		
		return;

	}
	public static void saveBounty(Player player) {
		File pfile = new File(Main.getInstance().getDataFolder() + File.separator + "players" + File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(pfile);
		fc.set("Statistics.Bounty", bounty.get(player.getUniqueId()));
		try {
			fc.save(pfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bounty.remove(player.getUniqueId());
		return;
	}
}