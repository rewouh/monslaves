package me.rewu.monslaves.Commands;

import me.rewu.monslaves.Arena;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand extends MonslavesCommand {

    private String NOT_PLAYER = "You must be a player to use this command.";
    private String WRONG_USAGE = "Wrong usage: /mons spectate <arena>";
    private String WRONG_ARENA = "Arena $ARENA$ does not exist.";
    private String ALREADY_SPECTATING = "You are already spectating the fight in $ARENA$!";
    private String SPECTATING = "You are now spectating the fight in $ARENA$.";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(NOT_PLAYER);

            return;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage(WRONG_USAGE);

            return;
        }

        String arenaName = args[1];

        if (!Arena.doesArenaExists(arenaName)) {
            player.sendMessage(WRONG_ARENA.replace("$ARENA$", args[1]));

            return;
        }

        Arena arena = Arena.getArena(args[1]);

        if (arena.isWatching(player)) {
            player.sendMessage(ALREADY_SPECTATING.replace("$ARENA$", arenaName));

            return;
        }

        arena.addSpectator(player);

        player.sendMessage(SPECTATING.replace("$ARENA$", arenaName));
    }
}
