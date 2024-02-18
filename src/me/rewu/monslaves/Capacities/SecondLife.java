package me.rewu.monslaves.Capacities;

import me.rewu.monslaves.Abstract.Capacity;
import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Static.CapacitiesInfos;
import org.bukkit.Material;

public class SecondLife extends Capacity {

    @Override
    public CapacityInfos getInfos() { return CapacitiesInfos.SECOND_LIFE_INFOS; }

    public SecondLife(Monslave monslave) {
        super(monslave);
    }
}
