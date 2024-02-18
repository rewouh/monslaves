package me.rewu.monslaves;

import me.rewu.monslaves.Data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;

public class Events implements Listener {

    PluginManager manager = Bukkit.getPluginManager();

    /* -------------- TO MOVE LATER ---------------- */

    NamespacedKey key = new NamespacedKey(Main.getInstance(), "isMonslave");

    private boolean IsMonslave(Entity entity) {
        return entity.getPersistentDataContainer().has(key, PersistentDataType.BYTE);
    }

    private void SetMonslave(Entity entity) {
        entity.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
    }

    /* -------------------------------------------- */

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerData.loadData(event.getPlayer());
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL)
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityBurn(EntityCombustEvent event) {
        if (event instanceof EntityCombustByEntityEvent || event instanceof EntityCombustByBlockEvent)
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        event.getDrops().clear();
    }

    @EventHandler
    public void onTargetChange(EntityTargetEvent event) { event.setCancelled(true); }

}
