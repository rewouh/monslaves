package me.rewu.monslaves.Commands;

import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.Arena;
import me.rewu.monslaves.CustomEvents.MonslaveFightOverEvent;
import me.rewu.monslaves.CustomEvents.MonslaveSelectedEvent;
import me.rewu.monslaves.Inventories.InventoryUtils;
import me.rewu.monslaves.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class ClashCommand extends MonslavesCommand implements Listener {

    private String NOT_PLAYER = "You must be a player to use this command.";

    private String WRONG_USAGE = "Wrong usage: /mons clash <player>";

    private String PLAYER_NOT_FOUND = "Player $PLAYER$ not found.";

    private String TARGET_BUSY = "This player is busy, wait a moment and try again.";

    private String BUSY = "You can't do that for now, wait a moment and try again.";

    private String ALREADY_REQUESTED = "You already requested a clash with $PLAYER$.";

    private String REQUEST_SENT = "You challenged $PLAYER$ to a clash!";

    private String REQUEST_RECEIVED = "You have been challenged to a clash by $PLAYER$! \nUse /mons clash $PLAYER$ to accept the challenge.";

    private String REQUEST_ACCEPTED_CLASHED = "You accepted $CLASHER$'s clash request, select your fighter.";

    private String REQUEST_ACCEPTED_CLASHER = "$CLASHED$ accepted your clash request, select your fighter.";

    private String NO_ARENA_AVAILABLE = "No arena available, your clash with $OPPONENT$ has been cancelled.";

    private HashMap<Player, ArrayList<Player>> sentRequests = new HashMap<>();

    private ArrayList<Player> playersSelecting = new ArrayList<>();

    private HashMap<Player, MonslaveData> playersChoices = new HashMap<>();

    private ArrayList<Player> playersClashing = new ArrayList<>();

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(NOT_PLAYER);

            return;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            sender.sendMessage(WRONG_USAGE);

            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(PLAYER_NOT_FOUND.replace("$PLAYER$", args[1]));

            return;
        }

        if (sentRequests.containsKey(player) && sentRequests.get(player).contains(target)) { // Accepting clash request

            sentRequests.get(player).remove(target);

            playersSelecting.add(target);
            playersSelecting.add(player);

            playersChoices.put(player, null);
            playersChoices.put(target, null);

            player.openInventory(InventoryUtils.createCollectionInventory(player));
            target.openInventory(InventoryUtils.createCollectionInventory(target));

            player.sendMessage(REQUEST_ACCEPTED_CLASHED.replace("$CLASHER$", target.getName()));
            target.sendMessage(REQUEST_ACCEPTED_CLASHER.replace("$CLASHED$", player.getName()));

        } else {
            if (!sentRequests.containsKey(target))
                sentRequests.put(target, new ArrayList<>());

            ArrayList<Player> targetRequests = sentRequests.get(target);

            if (targetRequests.contains(player)) {
                sender.sendMessage(ALREADY_REQUESTED.replace("$PLAYER$", target.getName()));

                return;
            }

            if (playersSelecting.contains(player) || playersClashing.contains(player)) {
                sender.sendMessage(BUSY);

                return;
            }

            if (playersSelecting.contains(target) || playersClashing.contains(target)) {
                sender.sendMessage(TARGET_BUSY);

                return;
            }

            targetRequests.add(player);

            player.sendMessage(REQUEST_SENT.replace("$PLAYER$", target.getName()));
            target.sendMessage(REQUEST_RECEIVED.replace("$PLAYER$", player.getName()));
        }
    }

    @EventHandler
    public void onMonslaveSelect(MonslaveSelectedEvent event) {
        Player selector = event.getSelector();

        if (!playersSelecting.contains(selector))
            return;

        playersChoices.put(selector, event.getSelected());

        int index = playersSelecting.indexOf(selector);

        Player opponent = playersSelecting.get(index % 2 == 0 ? index : index - 1);

        if (playersChoices.get(opponent) == null)
            return;

        MonslaveData selectorChoice = playersChoices.get(selector), opponentChoice = playersChoices.get(opponent);

        playersChoices.remove(selector, opponent);

        playersSelecting.remove(selector);
        playersSelecting.remove(opponent);

        Arena arena = Arena.getAvailableArena();

        if (arena == null) {
            selector.sendMessage(NO_ARENA_AVAILABLE.replace("$OPPONENT$", opponent.getName()));
            opponent.sendMessage(NO_ARENA_AVAILABLE.replace("$OPPONENT$", selector.getName()));

            return;
        }

        playersClashing.add(selector);
        playersClashing.add(opponent);

        arena.setupFight(selector, selectorChoice, opponent, opponentChoice);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> arena.startFight(), 20 * 5);
    }

    @EventHandler
    public void onFightOver(MonslaveFightOverEvent event) {
        for (Player player : event.getArena().getMasters())
            playersClashing.remove(player);
    }

}
