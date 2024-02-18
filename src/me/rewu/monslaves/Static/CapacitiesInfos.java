package me.rewu.monslaves.Static;

import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import org.bukkit.Material;

public class CapacitiesInfos {

    public static CapacityInfos INVASION_INFOS = new CapacityInfos(CapacityType.INVASION, "Summons a helping baby zombie every 10 seconds.", CapacityGenre.INNATE, 1, Material.ZOMBIE_SPAWN_EGG);

    // PASSIVES

    public static CapacityInfos SECOND_LIFE_INFOS = new CapacityInfos(CapacityType.SECOND_LIFE, "Upon death, resuscitates with 30% of its max health.", CapacityGenre.PASSIVE, 1, Material.TOTEM_OF_UNDYING);

    public static CapacityInfos REVENGE_INFOS = new CapacityInfos(CapacityType.REVENGE, "Every hit taken increases the monslave's next attack damages.", CapacityGenre.PASSIVE, 1, Material.REDSTONE);

    public static CapacityInfos CICATRIZATION_INFOS = new CapacityInfos(CapacityType.CICATRIZATION, "After 5 seconds without taking damage, the monslave slowly regains its health.", CapacityGenre.PASSIVE, 1, Material.APPLE);

    public static CapacityInfos PREDATOR_INFOS = new CapacityInfos(CapacityType.PREDATOR, "Every 2 seconds without hitting the opponent, the monslave gains 1 level of speed.", CapacityGenre.PASSIVE, 1, Material.NETHERITE_BOOTS);

    // ACTIVES

    public static CapacityInfos TORCH_INFOS = new CapacityInfos(CapacityType.TORCH, "Every 15 seconds, bursts a fire aura around itself, damaging and burning every close opponent.", CapacityGenre.ACTIVE, 6, Material.TORCH);

    public static CapacityInfos RETREAT_INFOS = new CapacityInfos(CapacityType.RETREAT, "When its health goes below 30%, the monslave leaps back.", CapacityGenre.ACTIVE, 7, Material.FEATHER);

    public static CapacityInfos BACKSTAB_INFOS = new CapacityInfos(CapacityType.BACKSTAB, "Every 15 seconds, teleports behind the opponents dealing 150% damages on its next attack.", CapacityGenre.ACTIVE, 1, Material.ENDER_PEARL);

    public static CapacityInfos CHARGE_INFOS = new CapacityInfos(CapacityType.CHARGE, "At the beginning of the fight and after reaching 50% of its max health, the monslave charges its opponent.", CapacityGenre.ACTIVE, 1, Material.GOAT_HORN);

}
