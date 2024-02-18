package me.rewu.monslaves.Abstract;

import me.rewu.monslaves.Arena;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Enums.MonslaveType;
import me.rewu.monslaves.Inventories.InventoryUtils;
import me.rewu.monslaves.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;

public class Monslave implements Listener {

    private MonslaveData data;
    private MonslaveInfos infos;

    private Arena arena;
    private Player master;
    private Creature creature;

    private Creature opponent;

    private ArrayList<Capacity> capacities = new ArrayList<>();

    // Getters & Setters

    public Creature getCreature() { return creature; }

    public Player getMaster() { return master; }

    public void setOpponent(Creature opponent) { this.opponent = opponent; }

    public Creature getOpponent() { return opponent; }

    public Monslave(Arena arena, Player master, MonslaveData data) {
        this.arena = arena;
        this.master = master;
        this.data = data;
        this.infos = Main.getMonslaveInfosFromType(data.getType());

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public Creature Summon(Location location) {
        MonslaveType type = data.getType();

        MonslaveInfos infos = Main.getMonslaveInfosFromType(type);

        this.creature = (Creature) location.getWorld().spawnEntity(location, infos.getEntityType());

        creature.setAI(false);
        creature.setCustomName(data.getName());

        // If spawned as a baby, makes it an adult
        if (creature instanceof Ageable ageable && !ageable.isAdult())
            ageable.setAdult();

        // Changing base attributes
        creature.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(infos.getMaxHealth());
        creature.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(infos.getBaseDamage());

        creature.setHealth(infos.getMaxHealth());

        // Instantiating capacities
        for (CapacityType capacityType : data.getCapacities())
            try { capacities.add((Capacity) Class.forName("me.rewu.monslaves.Capacities." + Main.getClassNameFromEnum(capacityType)).getDeclaredConstructor(Monslave.class).newInstance(this)); } catch (Exception e) { e.printStackTrace(); continue; }

        // Sorting by priority order
        capacities.sort(Comparator.comparingInt(c -> c.getInfos().getPriority()));

        return creature;
    }

    // Starts fighting
    public void Unleash() {
        creature.setAI(true);
        creature.setTarget(opponent);

        capacities.forEach(c -> c.FightStart());
    }

    // Fight over
    public void Over() {
        creature.remove();
        capacities.forEach(c -> c.FightOver());
    }

    // Events

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() != creature) return;

        capacities.forEach(c -> c.Death());

        arena.endFight();
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() == creature) {

            if (!(event.getEntity() instanceof LivingEntity entity))
                return;

            capacities.forEach(c -> c.Attack(event));

        } else if (event.getEntity() == creature) {

            capacities.forEach(c -> c.Hit(event));

        }
    }

}
