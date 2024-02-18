package me.rewu.monslaves.Inventories;

import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.CustomEvents.CapacityTypeSelectedEvent;
import me.rewu.monslaves.CustomEvents.MonslaveSelectedEvent;
import me.rewu.monslaves.CustomEvents.MonslaveTypeSelectedEvent;
import me.rewu.monslaves.Data.PlayerData;
import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Enums.MonslaveType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == InventoryUtils.MONSLAVES_SELECTION_INVENTORY) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE)
                return;

            MonslaveType type = MonslaveType.valueOf(InventoryUtils.reverseEnumNameNormalization(clickedItem.getItemMeta().getDisplayName().substring(2)));

            MonslaveTypeSelectedEvent selectedEvent = new MonslaveTypeSelectedEvent((Player) event.getWhoClicked(), type);

            Bukkit.getPluginManager().callEvent(selectedEvent);
        } else if (event.getClickedInventory() == InventoryUtils.ACTIVES_SELECTION_INVENTORY || event.getClickedInventory() == InventoryUtils.PASSIVES_SELECTION_INVENTORY) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE)
                return;

            CapacityType type = CapacityType.valueOf(InventoryUtils.reverseEnumNameNormalization(clickedItem.getItemMeta().getDisplayName().substring(2)));

            CapacityGenre genre = event.getClickedInventory() == InventoryUtils.ACTIVES_SELECTION_INVENTORY ? CapacityGenre.ACTIVE : CapacityGenre.PASSIVE;

            CapacityTypeSelectedEvent selectedEvent = new CapacityTypeSelectedEvent((Player) event.getWhoClicked(), type, genre);

            Bukkit.getPluginManager().callEvent(selectedEvent);
        } else if (event.getClickedInventory().getSize() == 9 && event.getView().getTitle().contains("'s Monslaves")) {
            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null)
                return;

            Player player = (Player) event.getWhoClicked();

            MonslaveData selected = PlayerData.getMonslaves(player).get(event.getSlot());

            MonslaveSelectedEvent selectedEvent = new MonslaveSelectedEvent(player, selected);

            Bukkit.getPluginManager().callEvent(selectedEvent);
        }
    }
}
