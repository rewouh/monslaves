package me.rewu.monslaves.Abstract;

import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import org.bukkit.Material;

public class CapacityInfos {

    private CapacityType TYPE;

    private String DESCRIPTION;
    private CapacityGenre GENRE;
    private Integer PRIORITY;
    private Material ICON_MATERIAL;

    public CapacityInfos(CapacityType type, String description, CapacityGenre genre, Integer priority, Material iconMaterial) {
        this.TYPE = type;
        this.DESCRIPTION = description;
        this.GENRE = genre;
        this.PRIORITY = priority;
        this.ICON_MATERIAL = iconMaterial;
    }

    public CapacityType getType() { return TYPE; }

    public String getDescription() { return DESCRIPTION; }

    public CapacityGenre getGenre() { return GENRE; }

    public Integer getPriority() { return PRIORITY; }

    public Material getIconMaterial() { return ICON_MATERIAL; }
}
