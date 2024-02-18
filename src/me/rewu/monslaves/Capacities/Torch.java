package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Main;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.*;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;

public class Torch extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.TORCH_INFOS; }

    public Torch(Monslave monslave) { super(monslave); }

    private long secondsBetweenBursts = 15;

    private int schedulerId = -1;

    @Override
    public void FightStart() {
        BurstTimer();
    }

    private void BurstTimer() {
        schedulerId = scheduler.scheduleSyncDelayedTask(main, this::Burst, 20 * secondsBetweenBursts);
    }

    private void Burst() {
        Location location = creature.getLocation();

        location.getWorld().spawnParticle(Particle.FLAME, location,100, 0, 0, 0, 0.4);

        location.getWorld().getNearbyEntities(location, 5, 5, 5).forEach(entity -> {
            if (entity != creature)
                entity.setFireTicks(20 * 5);
        });

        BurstTimer();
    }

    @Override
    public void FightOver() {
        scheduler.cancelTask(schedulerId);
    }
}
