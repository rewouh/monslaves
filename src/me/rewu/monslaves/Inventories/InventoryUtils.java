package me.rewu.monslaves.Inventories;

import me.rewu.monslaves.Abstract.CapacityInfos;
import me.rewu.monslaves.Abstract.Monslave;
import me.rewu.monslaves.Abstract.MonslaveData;
import me.rewu.monslaves.Abstract.MonslaveInfos;
import me.rewu.monslaves.Data.PlayerData;
import me.rewu.monslaves.Enums.CapacityGenre;
import me.rewu.monslaves.Enums.CapacityType;
import me.rewu.monslaves.Enums.MonslaveType;
import me.rewu.monslaves.Main;
import me.rewu.monslaves.Static.CapacitiesInfos;
import me.rewu.monslaves.Static.MonslavesInfos;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class InventoryUtils {

    public static Inventory MONSLAVES_SELECTION_INVENTORY = createMonslaveSelectionInventory();

    public static Inventory PASSIVES_SELECTION_INVENTORY = createCapacitiesSelectionInventories()[0];

    public static Inventory ACTIVES_SELECTION_INVENTORY = createCapacitiesSelectionInventories()[1];

    public static Inventory createSelectionInventory(String title, ItemStack[] choices) {

        if (choices.length > 18)
            throw new IllegalArgumentException("Too many choices!");

        Inventory inventory = Bukkit.createInventory(null, choices.length > 9 ? 54 : 45, title);

        ItemStack glass = createItemStack(Material.GREEN_STAINED_GLASS_PANE, 1, "Placeholder", new ArrayList<>());

        for (int i=0; i<9; i++)
            inventory.setItem(i, glass);

        int slot = 18;

        for (ItemStack choice : choices) {
            inventory.setItem(slot, choice);

            slot++;

            if (slot == 25)
                slot += 2;
        }

        for (int i=36; i<45; i++)
            inventory.setItem(i, glass);

        return inventory;
    }

    public static Inventory createMonslaveSelectionInventory() {
        MonslaveType[] monslaves = MonslaveType.values();

        ItemStack[] choices = new ItemStack[monslaves.length];

        for (int i=0; i<monslaves.length; i++) {

            MonslaveType type = monslaves[i];

            MonslaveInfos infos = Main.getMonslaveInfosFromType(type);

            if (infos == null)
                continue;

            choices[i] = generateMonslaveSelectionItem(infos);
        }

        return createSelectionInventory("Choose a creature:", choices);
    }

    public static Inventory[] createCapacitiesSelectionInventories() {
        CapacityType[] capacities = CapacityType.values();

        ArrayList<CapacityInfos> passivesInfos = new ArrayList<>();
        ArrayList<CapacityInfos> activesInfos = new ArrayList<>();

        for (CapacityType capacity : capacities) {
            CapacityInfos infos = Main.getCapacityInfosFromType(capacity);

            if (infos == null)
                continue;

            if (infos.getGenre() == CapacityGenre.PASSIVE)
                passivesInfos.add(infos);
            else if (infos.getGenre() == CapacityGenre.ACTIVE)
                activesInfos.add(infos);
        }

        ItemStack[] passivesChoices = new ItemStack[passivesInfos.size()];
        ItemStack[] activesChoices = new ItemStack[activesInfos.size()];

        for (int i=0; i<passivesInfos.size(); i++)
            passivesChoices[i] = generateCapacitySelectionItem(passivesInfos.get(i));

        for (int i=0; i<activesInfos.size(); i++)
            activesChoices[i] = generateCapacitySelectionItem(activesInfos.get(i));

        return new Inventory[] {createSelectionInventory("Choose its passives:", passivesChoices), createSelectionInventory("Choose its skills:", activesChoices)};
    }

    public static Inventory createCollectionInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, player.getDisplayName() + "'s Monslaves");

        for (MonslaveData monslave : PlayerData.getMonslaves(player))
            inventory.addItem(generateMonslaveCollectionItem(monslave));

        return inventory;
    }

    private static ItemStack generateMonslaveCollectionItem(MonslaveData data) {

        MonslaveType type = data.getType();

        MonslaveInfos infos = Main.getMonslaveInfosFromType(type);

        if (infos == null)
            return null;

        ArrayList<String> lore = new ArrayList<>();

        lore.add("§7Capacities:");

        String line = "§7";

        for (CapacityType capacity : data.getCapacities())
            line += normalizeEnumName(capacity) + ", ";

        lore.add(line.substring(0, line.length() - 2));

        return createItemStack(infos.getIconMaterial(), 1, "§6" + data.getName() + " (" + normalizeEnumName(infos.getType()) + ")", lore);
    }

    private static ItemStack generateMonslaveSelectionItem(MonslaveInfos infos) {
        CapacityInfos passiveInfos = infos.getPassiveInfos();

        ArrayList<String> lore = new ArrayList<>();

        lore.add("§c♥ " + infos.getMaxHealth() + " §a⚔ " + infos.getBaseDamage());
        lore.add("");
        lore.add("§c" + normalizeEnumName(passiveInfos.getType()) + ":");
        lore.addAll(Arrays.asList(convertTextToLore(passiveInfos.getDescription())));
        lore.add("");
        lore.addAll(Arrays.asList(convertTextToLore(infos.getDescription())));
        lore.add("");
        lore.add("§eClick to choose this creature!");

        return createItemStack(infos.getIconMaterial(), 1, "§6" + normalizeEnumName(infos.getType()), lore);
    }

    private static ItemStack generateCapacitySelectionItem(CapacityInfos infos) {
        ArrayList<String> lore = new ArrayList<>();

        lore.addAll(Arrays.asList(convertTextToLore(infos.getDescription())));
        lore.add("");
        lore.add("§aAdvanced specifications:");
        lore.add("§7Priority: " + infos.getPriority());
        lore.add("");
        lore.add("§eClick to select this capacity!");

        return createItemStack(infos.getIconMaterial(), 1, "§6" + normalizeEnumName(infos.getType()), lore);
    }

    public static ItemStack createItemStack(Material material, int amount, String name, ArrayList<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(name);

        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    private static String[] convertTextToLore(String text) {
        String[] lore = text.split("(?<=\\G.{50})");

        for (int i=0; i<lore.length; i++)
            lore[i] = "§7" + (lore[i].startsWith(" ") ? lore[i].substring(1) : lore[i]);

        return lore;
    }

    public static String toCamelCase(String s) {
        s = s.toLowerCase();

        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String normalizeEnumName(Enum e) {
        return toCamelCase(e.name().replace("_", " "));
    }

    public static String reverseEnumNameNormalization(String name) {
        return name.toUpperCase().replace(" ", "_");
    }
}
