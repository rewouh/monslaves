package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Main;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Predator extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.PREDATOR_INFOS; }

    public Predator(Monslave monslave) {
        super(monslave);
    }

    private long ticksBetweenPredate = 40;

    private int schedulerId = -1;

    private PotionEffect baseSpeedEffect = new PotionEffect(PotionEffectType.SPEED, 999999, 1, false, false ,false);

    @Override
    public void FightStart() {
        StartTimer();
    }

    @Override
    public void Attack(EntityDamageByEntityEvent event) {
        CancelTimer();

        if (creature.hasPotionEffect(PotionEffectType.SPEED))
            creature.removePotionEffect(PotionEffectType.SPEED);

        StartTimer();
    }

    @Override
    public void FightOver() {
        CancelTimer();
    }

    private void Predate() {
        PotionEffect potionEffect = creature.getPotionEffect(PotionEffectType.SPEED);

        potionEffect = potionEffect == null ? baseSpeedEffect : new PotionEffect(PotionEffectType.SPEED, 999999, potionEffect.getAmplifier() + 1, false, false, false);

        creature.addPotionEffect(potionEffect);

        StartTimer();
    }

    private void StartTimer() {
        schedulerId = scheduler.scheduleSyncDelayedTask(main, this::Predate, ticksBetweenPredate);
    }

    private void CancelTimer() {
        scheduler.cancelTask(schedulerId);
    }

}
