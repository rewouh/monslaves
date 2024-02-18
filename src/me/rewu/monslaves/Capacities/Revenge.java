package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Revenge extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.REVENGE_INFOS; }

    public Revenge(Monslave monslave) {
        super(monslave);
    }

    private float damageMultiplier = 1.0f;

    @Override
    public void Hit(EntityDamageByEntityEvent event) {
        damageMultiplier *= 1.1f;
    }

    @Override
    public void Attack(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * damageMultiplier);

        damageMultiplier = 1.0f;
    }
}
