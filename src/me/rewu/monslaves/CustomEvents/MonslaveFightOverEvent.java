package me.rewu.monslaves.CustomEvents;

import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MonslaveFightOverEvent extends Event {

    private Arena ARENA;

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    @Override public HandlerList getHandlers() { return HANDLERS_LIST; }
    public static HandlerList getHandlerList() { return HANDLERS_LIST; }

    public MonslaveFightOverEvent(Arena arena) {
        this.ARENA = arena;
    }

    public Arena getArena() { return ARENA; }
}
