package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Static.CapacitiesInfos;

public class Backstab extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.BACKSTAB_INFOS; }

    public Backstab(Monslave monslave) { super(monslave); }

    private long secondsBetweenBackstabs = 20;

    private int schedulerId = -1;

    @Override
    public void FightStart() {
        BackstabTimer();
    }

    private void BackstabTimer() {
        schedulerId = scheduler.scheduleSyncDelayedTask(main, this::Backstab, 20 * secondsBetweenBackstabs);
    }

    private void Backstab() {
        creature.teleport(monslave.getOpponent());

        BackstabTimer();
    }

    @Override
    public void FightOver() {
        scheduler.cancelTask(schedulerId);
    }
}
