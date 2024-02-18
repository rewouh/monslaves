package me.rewu.monslaves;

import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.Abstract.MonslaveInfos;
import me.rewu.monslaves.CustomEvents.MonslaveFightOverEvent;
import me.rewu.monslaves.Enums.MonslaveType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Arena {

    // Static

    private static HashMap<String, Arena> arenas = new HashMap<>();

    public static Arena registerArena(String name, Location[] locations) {
        Arena arena = new Arena(name, locations[0], locations[1], locations[2], locations[3], locations[4]);

        arenas.put(name, arena);

        return arena;
    }

    public static Arena getArena(String name) {
        return arenas.get(name);
    }

    public static Arena getAvailableArena() {
        for (Arena arena: arenas.values())
            if (!arena.isBusy())
                return arena;

        return null;
    }

    public static boolean doesArenaExists(String name) { return arenas.containsKey(name); }

    // Local

    private String name;

    private Location firstSpawn;

    private Location secondSpawn;

    private Location firstMasterSpot;

    private Location secondMasterSpot;

    private Location spectatorsSpot;

    private Player firstMaster;

    private Player secondMaster;

    private Monslave firstFighter;

    private Monslave secondFighter;

    public ArrayList<Player> watchers = new ArrayList<>();

    private boolean busy = false;

    // Getters

    public boolean isBusy() { return busy; }

    public Monslave[] getFighters() { return new Monslave[] { firstFighter, secondFighter }; }

    public Player[] getMasters() { return new Player[] { firstMaster, secondMaster }; }

    public Arena(String name, Location fighter1, Location fighter2, Location master1, Location master2, Location spectators) {
        this.name = name;
        this.firstSpawn = fighter1;
        this.secondSpawn = fighter2;
        this.firstMasterSpot = master1;
        this.secondMasterSpot = master2;
        this.spectatorsSpot = spectators;
    }

    public void setupFight(Player master1, MonslaveData fighter1, Player master2, MonslaveData fighter2) {
        this.firstMaster = master1;
        this.secondMaster = master2;

        this.firstFighter = new Monslave(this, master1, fighter1);
        this.secondFighter = new Monslave(this, master2, fighter2);

        watchers = new ArrayList<>();
        watchers.add(master1);
        watchers.add(master2);

        Creature firstCreature = firstFighter.Summon(firstSpawn);
        Creature secondCreature = secondFighter.Summon(secondSpawn);

        firstFighter.setOpponent(secondCreature);
        secondFighter.setOpponent(firstCreature);

        master1.teleport(firstMasterSpot);
        master2.teleport(secondMasterSpot);

        for (int i=2; i<watchers.size(); i++)
            watchers.get(i).teleport(spectatorsSpot);

        Bukkit.broadcastMessage("\nA fight between " + fighter1.getName() + " (" + master1.getName() + ") and " + fighter2.getName() + " (" + master2.getName() + ") is about to start in " + name + " !");
        Bukkit.broadcastMessage("\nUse /mons spectate " + name + " to watch the fight!");

        busy = true;
    }

    public void startFight() {
        firstFighter.Unleash();
        secondFighter.Unleash();

        Bukkit.broadcastMessage("\nThe fight in " + name + " starts!");

        Announce(firstFighter.getCreature().getCustomName() + " VS " + secondFighter.getCreature().getCustomName(), "Fight!", 3);
    }

    public void endFight() {

        Player winner = firstFighter.getCreature().isDead() ? watchers.get(1) : watchers.get(0);

        String winnerMonslaveName = firstFighter.getCreature().getCustomName();

        Announce("Fight over!", winnerMonslaveName + " won!", 3);
        Bukkit.broadcastMessage("\n" + winnerMonslaveName + " won the fight in " + name + "!");

        firstFighter.Over();
        secondFighter.Over();

        Bukkit.getPluginManager().callEvent(new MonslaveFightOverEvent(this));

        resetData();

        busy = false;
    }

    private void resetData() {
        firstFighter = null;
        secondFighter = null;

        watchers = new ArrayList<>();
    }

    public void Announce(String title, String subtitle, int seconds) {
        watchers.forEach(player -> player.sendTitle(title, subtitle, 10, seconds * 20, 10));
    }

    public void addSpectator(Player player) {
        watchers.add(player);

        player.teleport(spectatorsSpot);
    }

    public boolean isWatching(Player player) {
        return watchers.contains(player);
    }

}
