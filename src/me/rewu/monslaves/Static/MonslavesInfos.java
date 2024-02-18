package me.rewu.monslaves.Static;

import me.rewu.monslaves.Abstract.MonslaveInfos;
import me.rewu.monslaves.Enums.MonslaveType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class MonslavesInfos {

    public static MonslaveInfos ZOMBIE_INFOS = new MonslaveInfos(MonslaveType.ZOMBIE, EntityType.ZOMBIE, "Its lack of pain receptors and unrelenting nature make it a fearsome adversary that never tires or surrenders. Plus, its eerie presence will surely thrill and chill the audience.", 30f, 1f, CapacitiesInfos.INVASION_INFOS, Material.ZOMBIE_SPAWN_EGG, new ArrayList<>() { });

    public static MonslaveInfos WOLF_INFOS = new MonslaveInfos(MonslaveType.WOLF, EntityType.WOLF, "With its sharp teeth and powerful jaws, it can deliver devastating bites that can cripple its opponents. Its quick movements and agile leaps make it difficult to hit or avoid, and its pack mentality means it can coordinate with other wolves to take down even the strongest of foes.", 15f, 2f, CapacitiesInfos.INVASION_INFOS, Material.WOLF_SPAWN_EGG, new ArrayList<>() { });

    public static MonslaveInfos SILVERFISH_INFOS = new MonslaveInfos(MonslaveType.SILVERFISH, EntityType.SILVERFISH, "Its lightning-fast movements and ability to crawl up walls and ceilings make it a difficult target to hit and its sharp mandibles can deliver a nasty bite.", 20f, 1.3f, CapacitiesInfos.INVASION_INFOS, Material.SILVERFISH_SPAWN_EGG, new ArrayList<>() { });

}
