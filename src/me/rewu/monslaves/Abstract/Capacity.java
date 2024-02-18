package me.rewu.monslaves.Abstract;

import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class Capacity {

    protected BukkitScheduler scheduler;

    protected Main main;

    protected Monslave monslave;

    protected Creature creature;

    public abstract CapacityInfos getInfos();

    public Capacity(Monslave monslave) {
        scheduler = Bukkit.getScheduler();
        main = Main.getInstance();

        this.monslave = monslave;
        this.creature = monslave.getCreature();
    }

    /* Events */

    public void FightStart() {}; // Called when the fight starts

    public void FightOver() {}; // Called when the fight is done

    public void Death() {}; // Called when the monslave dies

    public void Attack(EntityDamageByEntityEvent event) {}; // Called when the monslave attacks a living entity.

    public void Hit(EntityDamageByEntityEvent event) {}; // Called when the monslave is hit by an entity
}
