package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class Retreat extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.RETREAT_INFOS; }

    public Retreat(Monslave monslave) {
        super(monslave);
    }

    @Override
    public void Hit(EntityDamageByEntityEvent event) {

        double healthBefore = creature.getHealth() / creature.getMaxHealth();
        double healthAfter = (creature.getHealth() - event.getFinalDamage()) / creature.getMaxHealth();

        if (healthBefore < 0.3 || healthAfter > 0.3)
            return;

        Location location = creature.getLocation();

        Vector launchDirection = location.getDirection().multiply(-3);

        creature.setVelocity(launchDirection);
    }
}
