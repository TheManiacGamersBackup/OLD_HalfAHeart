package us.riftmc.htgan.halfAHeart.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.themaniacgamers.HalfAHeart.Main;


public class PlayerDataCreator {

	public static void firstjoin(Player player){
		File pfile = new File(Main.getInstance().getDataFolder() + File.separator + "PlayerData" + File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(pfile);
		fc.set("Playername", player.getName().toString());
		int zero = 0;
		fc.set("Statistics.Total Kills", zero);
		fc.set("Statistics.Total Deaths", zero);
		fc.set("Statistics.Longest Killstreak", zero);
		fc.set("Statistics.Experience", zero);
		fc.set("Statistics.Level", zero);
		fc.set("Statistics.Bounty", zero);
		fc.set("Statistics.Claims", zero);
		fc.set("Statistics.Chats", 30);
		fc.set("Statistics.Bullseye", zero);
		fc.set("Statistics.Grace", zero);
		fc.set("Statistics.Grenades", zero);
		fc.set("Checkpoints.Fortune", false);
		fc.set("Checkpoints.Fisher's Paradise", false);
		fc.set("Checkpoints.Gilbert's Cave", false);
		fc.set("Checkpoints.Frog Pond", false);
		fc.set("Checkpoints.Gordan's", false);
		fc.set("Checkpoints.Hair Pin Overlook", false);
		fc.set("Checkpoints.Hero's Climb", false);
		fc.set("Checkpoints.Hero's Peak", false);
		fc.set("Checkpoints.Wow Such Skills!", false);
		fc.set("Checkpoints.Hospital", false);
		fc.set("Checkpoints.Leaky Barrel", false);
		fc.set("Checkpoints.Leap of Faith", false);
		fc.set("Checkpoints.Lexington's", false);
		fc.set("Checkpoints.Liberty", false);
		fc.set("Checkpoints.Library", false);
		fc.set("Checkpoints.Lighthouse", false);
		fc.set("Checkpoints.Lockwood Complex", false);
		fc.set("Checkpoints.Mayor's Attic", false);
		fc.set("Checkpoints.Military Base", false);
		fc.set("Checkpoints.Mining Guild", false);
		fc.set("Checkpoints.Norman's Point", false);
		fc.set("Checkpoints.Norton's", false);
		fc.set("Checkpoints.The Willow", false);
		fc.set("Checkpoints.Roderick's Inn", false);
		fc.set("Checkpoints.Salvager", false);
		fc.set("Checkpoints.Scholar's Shade", false);
		fc.set("Checkpoints.Snappy Shark", false);
		fc.set("Checkpoints.Traitor's Escape", false);
		fc.set("Checkpoints.The Arch", false);
		fc.set("Checkpoints.The Dock", false);
		fc.set("Checkpoints.The Fountain", false);
		fc.set("Checkpoints.The Shire", false);
		fc.set("Checkpoints.The Vines", false);
		fc.set("Checkpoints.Trading Company", false);
		fc.set("Checkpoints.Underpass", false);
		fc.set("Checkpoints.Warehouse", false);
		fc.set("Checkpoints.Vine Yard", false);
		fc.set("Checkpoints.Watch Tower", false);
		fc.set("Checkpoints.Arch Guild", false);
		fc.set("Checkpoints.Angel Falls", false);
		fc.set("Checkpoints.CakeBadger", false);
		fc.set("Checkpoints.Catacombs", false);
		fc.set("Checkpoints.Cave of the Dead", false);
		fc.set("Checkpoints.Crow's Nest", false);
		fc.set("Checkpoints.Dragonite", false);
		try {
			fc.save(pfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
}
