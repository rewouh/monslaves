package me.rewu.monslaves.CustomEvents;

import me.rewu.monslaves.Enums.MonslaveType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MonslaveTypeSelectedEvent extends Event {

    private Player SELECTOR;

    private MonslaveType SELECTED_TYPE;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    @Override public HandlerList getHandlers() { return HANDLERS_LIST; }
    public static HandlerList getHandlerList() { return HANDLERS_LIST; }

    public MonslaveTypeSelectedEvent(Player selector, MonslaveType selectedType) {
        this.SELECTOR = selector;
        this.SELECTED_TYPE = selectedType;
    }

    public Player getSelector() { return SELECTOR; }

    public MonslaveType getSelectedType() { return SELECTED_TYPE; }
}

