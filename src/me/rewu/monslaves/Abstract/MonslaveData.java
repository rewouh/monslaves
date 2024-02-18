package me.rewu.monslaves.Abstract;

import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Enums.MonslaveType;

import java.util.ArrayList;

public class MonslaveData {

    private String NAME;

    private MonslaveType TYPE;

    private ArrayList<CapacityType> CAPACITIES;

    public MonslaveData() {
        CAPACITIES = new ArrayList<>();
    }

    public void setName(String name) { this.NAME = name; }

    public String getName() { return NAME; }

    public void setType(MonslaveType type) { this.TYPE = type; }

    public void addCapacity(CapacityType capacity) { CAPACITIES.add(capacity); }

    public void removeCapacity(CapacityType capacity) { CAPACITIES.remove(capacity); }

    public boolean hasCapacity(CapacityType capacity) { return CAPACITIES.contains(capacity); }

    public MonslaveType getType() { return TYPE; }

    public ArrayList<CapacityType> getCapacities() { return CAPACITIES; }
}
