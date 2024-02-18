package me.rewu.monslaves.Abstract;

import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Enums.MonslaveType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class MonslaveInfos {

    private MonslaveType TYPE;

    private EntityType ENTITY_TYPE;
    private String DESCRIPTION;
    private Float MAX_HEALTH;
    private float BASE_DAMAGE;
    private CapacityInfos PASSIVE_INFOS;
    private Material ICON_MATERIAL;
    private ArrayList<CapacityType> FORBIDDEN_CAPACITIES;

    public MonslaveInfos(MonslaveType type, EntityType entityType, String description, Float maxHealth, Float baseDamage, CapacityInfos passiveInfos, Material iconMaterial, ArrayList<CapacityType> forbiddenCapacities) {
        this.TYPE = type;
        this.ENTITY_TYPE = entityType;
        this.DESCRIPTION = description;
        this.MAX_HEALTH = maxHealth;
        this.BASE_DAMAGE = baseDamage;
        this.PASSIVE_INFOS = passiveInfos;
        this.ICON_MATERIAL = iconMaterial;
        this.FORBIDDEN_CAPACITIES = forbiddenCapacities;
    }

    public MonslaveType getType() { return TYPE; }

    public EntityType getEntityType() { return ENTITY_TYPE; }

    public String getDescription() { return DESCRIPTION; }

    public Float getMaxHealth() { return MAX_HEALTH; }

    public Float getBaseDamage() { return BASE_DAMAGE; }

    public CapacityInfos getPassiveInfos() { return PASSIVE_INFOS; }

    public Material getIconMaterial() { return ICON_MATERIAL; }

    public ArrayList<CapacityType> getForbiddenCapacities() { return FORBIDDEN_CAPACITIES; }

}
