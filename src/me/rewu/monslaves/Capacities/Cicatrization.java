package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Main;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Cicatrization extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.CICATRIZATION_INFOS; }

    public Cicatrization(Monslave monslave) {
        super(monslave);
    }

    private long secondsBeforeRegen = 5;

    private long ticksBetweenRegen = 10;

    private int schedulerId = -1;

    @Override
    public void FightStart() {
        StartRegen();
    }

    @Override
    public void Hit(EntityDamageByEntityEvent event) {
        scheduler.cancelTask(schedulerId);

        StartRegen();
    }

    private void StartRegen() {
        schedulerId = scheduler.scheduleSyncDelayedTask(main, this::Regen, 20 * secondsBeforeRegen);
    }

    private void Regen() {
        double newHealth = creature.getHealth() + 0.5;

        if (newHealth > creature.getMaxHealth())
            newHealth = creature.getMaxHealth();

        creature.setHealth(newHealth);

        Location location = creature.getLocation();

        location.setY(location.getY() + 1);

        location.getWorld().spawnParticle(Particle.HEART, location, 5, 1, 1, 1, 0.1);

        schedulerId = scheduler.scheduleSyncDelayedTask(main, this::Regen, ticksBetweenRegen);
    }

    @Override
    public void FightOver() {
        scheduler.cancelTask(schedulerId);
    }
}
