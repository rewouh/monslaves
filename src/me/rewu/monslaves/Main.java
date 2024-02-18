package me.rewu.monslaves;

import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.MonslaveInfos;
import me.rewu.monslaves.Commands.HeadCommand;
import me.rewu.monslaves.Data.PlayerData;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Enums.MonslaveType;
import me.rewu.monslaves.Inventories.InventoryEvents;
import me.rewu.monslaves.Static.CapacitiesInfos;
import me.rewu.monslaves.Static.MonslavesInfos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        // Events

        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryEvents(), this);

        // Commands

        HeadCommand headCommand = new HeadCommand();

        getCommand("mons").setExecutor(headCommand);

        headCommand.registerMonslavesCommands();

        // Reload compatibility
        for (Player player : Bukkit.getOnlinePlayers())
            PlayerData.loadData(player);

        // TO REMOVE LATER

        World world = Bukkit.getWorld("world");

        Location[] locations = {
                new Location(Bukkit.getWorld("world"), 69, -59, 1),
                new Location(Bukkit.getWorld("world"), 69, -59, 69),
                new Location(Bukkit.getWorld("world"), 69, -50, 61),
                new Location(Bukkit.getWorld("world"), 69, -50, 10),
                new Location(Bukkit.getWorld("world"), 86, -50, 35)
        };

        Arena arena = Arena.registerArena("Colosseum",  locations);
    }

    public static MonslaveInfos getMonslaveInfosFromType(MonslaveType type) {
        try { return (MonslaveInfos) MonslavesInfos.class.getField(type.name() + "_INFOS").get(null); } catch (Exception e) { return null; }
    }

    public static CapacityInfos getCapacityInfosFromType(CapacityType type) {
        try { return (CapacityInfos) CapacitiesInfos.class.getField(type.name() + "_INFOS").get(null); } catch (Exception e) { return null; }
    }

    public static String getClassNameFromEnum(CapacityType type) {
        String model = type.name().toLowerCase();
        String className = Character.toUpperCase(model.charAt(0)) + "";

        for (int i=1; i<model.length(); i++)
            className += model.charAt(i) == '_' ? Character.toUpperCase(model.charAt(++i)) : model.charAt(i);

        return className;
    }
}
