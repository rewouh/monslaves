package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class Charge extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.CICATRIZATION_INFOS; }

    public Charge(Monslave monslave) {
        super(monslave);
    }

    private long secondsBeforeCharge = 10;

    private int schedulerId = -1;

    @Override
    public void FightStart() {
        schedulerId = scheduler.scheduleSyncDelayedTask(main, this::Charge, 20 * secondsBeforeCharge);
    }

    private void Charge() {
        Location location = creature.getLocation();

        Vector launchDirection = location.getDirection().multiply(5);

        creature.setVelocity(launchDirection);

        ChargeHitboxTimer(0);
    }

    private void ChargeHitboxTimer(int i) {
        schedulerId = scheduler.scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                ChargeHitbox(i);
            }
        }, 2);
    }

    private void ChargeHitbox(int i) {

        if (i == 10)
            return;

        Location location = creature.getLocation();

        World world = location.getWorld();

        world.spawnParticle(Particle.SMOKE_LARGE, location, 10, 1, 1, 1, 0.1);

        for (Entity entity : world.getNearbyEntities(location, 3, 3, 3)) {
            if (entity != creature && (entity instanceof LivingEntity living)) {
                living.damage(3);

                world.spawnParticle(Particle.EXPLOSION_LARGE, location, 1);

                return;
            }
        }

        ChargeHitboxTimer(i+1);
    }
}
