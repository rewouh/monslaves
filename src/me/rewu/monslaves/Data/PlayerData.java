package me.rewu.monslaves.Data;

import me.rewu.monslaves.Abstract.MonslaveData;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData {

    private static HashMap<UUID, ArrayList<MonslaveData>> playersMonslaves = new HashMap<>();

    public static void loadData(Player player) { playersMonslaves.put(player.getUniqueId(), new ArrayList<>()); }

    public static void addMonslave(UUID uuid, MonslaveData monslave) { playersMonslaves.get(uuid).add(monslave); }

    public static void addMonslave(Player player, MonslaveData monslave) { addMonslave(player.getUniqueId(), monslave); }

    public static ArrayList<MonslaveData> getMonslaves(Player player) { return playersMonslaves.get(player.getUniqueId()); }
}
