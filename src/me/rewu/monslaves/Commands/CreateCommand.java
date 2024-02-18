package me.rewu.monslaves.Commands;

import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.CustomEvents.CapacityTypeSelectedEvent;
import me.rewu.monslaves.CustomEvents.MonslaveTypeSelectedEvent;
import me.rewu.monslaves.Data.PlayerData;
import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Inventories.InventoryUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class CreateCommand extends MonslavesCommand implements Listener {

    private HashMap<Player, MonslaveData> monslavesBeingCreated = new HashMap<>();

    private String NOT_PLAYER = "You must be a player to use this command.";

    private String CREATION_STARTED = "Monslave creation engaged.";
    private String TYPE_SELECTED = "Creature selected: $TYPE$";

    private String CAPACITY_SELECTED = "Capacity selected: $CAPACITY$ ($NUMBER_SELECTED$/$MAX_NUMBER_SELECTED$)";

    private String CAPACITY_REMOVED = "Capacity removed: $CAPACITY$ ($NUMBER_SELECTED$/$MAX_NUMBER_SELECTED$)";

    private String TYPE_NAME = "Type a name for your monslave:";

    private String MONSLAVE_CREATED = "$NAME$ joins your fighters, type /mons collection to check it out!";

    private Integer maxPassives = 2;
    private Integer maxActives = 3;

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(NOT_PLAYER);

            return;
        }

        Player player = (Player) sender;

        if (monslavesBeingCreated.containsKey(player))
            monslavesBeingCreated.remove(player);

        monslavesBeingCreated.put(player, new MonslaveData());

        player.openInventory(InventoryUtils.MONSLAVES_SELECTION_INVENTORY);

        player.sendMessage(CREATION_STARTED);
    }

    @EventHandler
    public void onSelectMonslaveType(MonslaveTypeSelectedEvent event) {
        Player player = event.getSelector();

        if (!monslavesBeingCreated.containsKey(player))
            return;

        MonslaveData monslaveData = monslavesBeingCreated.get(player);

        monslaveData.setType(event.getSelectedType());

        player.sendMessage(TYPE_SELECTED.replace("$TYPE$", InventoryUtils.normalizeEnumName(event.getSelectedType())));

        player.openInventory(InventoryUtils.PASSIVES_SELECTION_INVENTORY);
    }

    @EventHandler
    public void onSelectCapacityType(CapacityTypeSelectedEvent event) {
        Player player = event.getSelector();

        if (!monslavesBeingCreated.containsKey(player))
            return;

        MonslaveData monslaveData = monslavesBeingCreated.get(player);

        CapacityType selectedCapacity = event.getSelectedCapacity();

        Integer currSelected = monslaveData.getCapacities().size(), maxSelected;

        if (event.getSelectedCapacityGenre() == CapacityGenre.PASSIVE)
            maxSelected = maxPassives;
        else {
            currSelected -= maxPassives;
            maxSelected = maxActives;
        }

        if (monslaveData.hasCapacity(selectedCapacity)) {
            monslaveData.removeCapacity(selectedCapacity);

            currSelected -= 1;

            player.sendMessage(CAPACITY_REMOVED.replace("$CAPACITY$", InventoryUtils.normalizeEnumName(selectedCapacity)).replace("$NUMBER_SELECTED$", currSelected.toString()).replace("$MAX_NUMBER_SELECTED$", maxSelected.toString()));

            return;
        }

        monslaveData.addCapacity(selectedCapacity);

        currSelected += 1;

        player.sendMessage(CAPACITY_SELECTED.replace("$CAPACITY$", InventoryUtils.normalizeEnumName(selectedCapacity)).replace("$NUMBER_SELECTED$", currSelected.toString()).replace("$MAX_NUMBER_SELECTED$", maxSelected.toString()));

        if (currSelected != maxSelected)
            return;

        if (event.getSelectedCapacityGenre() == CapacityGenre.PASSIVE)
            player.openInventory(InventoryUtils.ACTIVES_SELECTION_INVENTORY);
        else {
            player.closeInventory();

            player.sendMessage(TYPE_NAME);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!monslavesBeingCreated.containsKey(player))
            return;

        MonslaveData monslaveData = monslavesBeingCreated.get(player);

        event.setCancelled(true);

        monslaveData.setName(event.getMessage());

        PlayerData.addMonslave(player, monslaveData);

        player.sendMessage(MONSLAVE_CREATED.replace("$NAME$", monslaveData.getName()));

        monslavesBeingCreated.remove(player);
    }

}
