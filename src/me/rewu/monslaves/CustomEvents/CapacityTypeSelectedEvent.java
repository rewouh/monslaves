package me.rewu.monslaves.CustomEvents;

import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CapacityTypeSelectedEvent extends Event {

    private Player SELECTOR;

    private CapacityType SELECTED_CAPACITY;

    private CapacityGenre SELECTED_CAPACITY_GENRE;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    @Override public HandlerList getHandlers() { return HANDLERS_LIST; }
    public static HandlerList getHandlerList() { return HANDLERS_LIST; }


    public CapacityTypeSelectedEvent(Player selector, CapacityType selectedCapacity, CapacityGenre selectedCapacityGenre) {
        this.SELECTOR = selector;
        this.SELECTED_CAPACITY = selectedCapacity;
        this.SELECTED_CAPACITY_GENRE = selectedCapacityGenre;
    }

    public Player getSelector() { return SELECTOR; }

    public CapacityType getSelectedCapacity() { return SELECTED_CAPACITY; }

    public CapacityGenre getSelectedCapacityGenre() { return SELECTED_CAPACITY_GENRE; }
}
