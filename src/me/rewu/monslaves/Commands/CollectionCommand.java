package me.rewu.monslaves.Commands;

import me.rewu.monslaves.Inventories.InventoryUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CollectionCommand extends MonslavesCommand {

    private String NOT_PLAYER = "You must be a player to use this command.";

    private String COLLECTION_OPENED = "Here is your monslaves collection.";

    @Override
    public void Execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(NOT_PLAYER);

            return;
        }

        Player player = (Player) sender;

        Inventory inventory = InventoryUtils.createCollectionInventory(player);

        player.openInventory(inventory);

        player.sendMessage(COLLECTION_OPENED);
    }

}
