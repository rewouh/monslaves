package me.rewu.monslaves.CustomEvents;

import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.Enums.MonslaveType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MonslaveSelectedEvent extends Event {

    private Player SELECTOR;

    private MonslaveData SELECTED;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    @Override public HandlerList getHandlers() { return HANDLERS_LIST; }
    public static HandlerList getHandlerList() { return HANDLERS_LIST; }

    public MonslaveSelectedEvent(Player selector, MonslaveData selected) {
        this.SELECTOR = selector;
        this.SELECTED = selected;
    }

    public Player getSelector() { return SELECTOR; }

    public MonslaveData getSelected() { return SELECTED; }
}
