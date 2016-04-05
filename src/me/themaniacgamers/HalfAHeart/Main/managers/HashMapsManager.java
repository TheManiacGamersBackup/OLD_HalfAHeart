package me.themaniacgamers.HalfAHeart.Main.managers;

import me.themaniacgamers.HalfAHeart.Main.utils.PlayerData;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Corey on 4/4/2016.
 */
public class HashMapsManager {
    private HashMapsManager() {
    }

    public static HashMap<UUID, PlayerData> loadedPlayer = new HashMap<UUID, PlayerData>();

    static HashMapsManager instance = new HashMapsManager();

    public static HashMapsManager getInstance() {
        return instance;
    }


}
