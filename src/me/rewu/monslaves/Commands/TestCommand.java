package me.rewu.monslaves.Commands;

import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.Arena;
import me.rewu.monslaves.Data.PlayerData;
import me.rewu.monslaves.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand extends MonslavesCommand {

    private String NOT_PLAYER = "You must be a player to use this command.";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(NOT_PLAYER);

            return;
        }

        Player player = (Player) sender;

        MonslaveData data = PlayerData.getMonslaves(player).get(0);

        Arena arena = Arena.getArena("Colosseum");

        arena.setupFight(player, data, player, data);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> arena.startFight(), 20 * 5);
    }
}
