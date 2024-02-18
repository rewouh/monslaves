package me.rewu.monslaves.Enums;

public enum CapacityType {
    /* --- Entity passive ---*/
    INVASION, // Zombie - Summons a baby zombie every 10 seconds
    AIRY, // Skeleton - 15% chances to dodge every attack
    ARMORED, // Iron Golem - Slowness 1 permanent
    CHILL, // Snowman - Each snowball has a 5% chance to freeze the target for 3 seconds
    GROWTH, // Slime - Grows every 5 seconds, gaining health and attack
    EXECUTIONER, // Wither Skeleton - 20% chance of executing the opponent if it has less than 30% of its health

    /* --- Passive --- */
    SECOND_LIFE, // The monslave resuscitates upon death with 30% of its health
    PREDATOR, // Every second without hitting the opponent, the monslave gains 1 speed level
    REVENGE, // Every received attack increases the monslave's next attack damages (exponentially)
    CICATRIZATION, // After 5 seconds without receiving damage, the monslave slowly regains its health
    SNIPER, // Projectiles launched by the monslave go 2x faster
    VAMPIRISM, // The monslave regains 10% of every inflicted damages as health
    CURSE, // The monslave loses 60% of its max health but after 30 seconds its opponent dies
    BERSERKER, // When reaching below 15% of its health the monslave gains Force II, Speed II and Resistance I for 10 seconds
    VENGEFUL, // The monslave returns 5% of every received damage if the enemy is in close range
    TOXIC, // The monslave attacks have 5% chance to poison the enemy for 5 seconds
    COPIER, // The monslave has 10% chance of copying an opponent capacity when it uses one

    /* --- Active --- */
    TORCH, // Every 15 seconds the monslave bursts a fire aura around itself, burning every opponent
    RETREAT, // When the monslave reaches 10% of its max health, he makes a big leap back
    BACKSTAB, // Every 20 seconds the monslave teleports behind the opponent, dealing 1.5x damages on its next attack
    CHARGE, // At the start of the fight and when it reaches 50% of its health, the monslave charges violently its opponent
    SLEDGE_HAMMER, // Once in the fight (randomly), the monslave launches itself in the air with the opponent and crushes it on the ground
    REPELLER // When the opponent is too close, pushes it back (30 seconds cooldown)
}
