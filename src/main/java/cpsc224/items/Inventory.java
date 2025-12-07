package cpsc224.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;

/**
 * An inventory for creatures to store items.
 */
public class Inventory {

    private int maxWeapons;
    private int maxConsumables;

    private List<Weapon> weapons = new ArrayList<Weapon>();
    private List<Consumable> consumables = new ArrayList<Consumable>();

    /**
     * Creates an inventory with max one weapon and max zero consumables.
     */
    public Inventory() {
        maxWeapons = 1;
        maxConsumables = 0;
    }

    /**
     * Creates an inventory with specified max weapons and consumables.
     * @param maxWeapons the max number of weapons
     * @param maxConsumables the max number of consumables
     */
    public Inventory(int maxWeapons, int maxConsumables) {
        this.maxWeapons = maxWeapons;
        this.maxConsumables = maxConsumables;
    }

    /**
     * Gets the max number of weapons.
     * @return the max number of weapons
     */
    public int getMaxWeapons(){
        return maxWeapons;
    }

    /**
     * Gets the max number of consumables.
     * @return the max number of consumables
     */
    public int getMaxConsumables(){
        return maxConsumables;
    }

    /**
     * Increases the max number of weapons by 1
     */
    public void incrementMaxWeapons() {
        maxWeapons++;
    }

    /**
     * Increases the max number of consumables by 1
     */
    public void incrementMaxConsumables() {
        maxConsumables++;
    }

    /**
     * Gets all the weapons.
     * @return the weapons as a collection
     */
    public Collection<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Gets all the consumables.
     * @return the consumables as a collection
     */
    public Collection<Consumable> getConsumables() {
        return consumables;
    }

    /**
     * Gets the weapon in the specified slot.
     * @param slot the slot indexed at 0
     * @return the weapon or null if slot is empty
     */
    public Weapon getWeapon(int slot) {
        Weapon w = null;

        try {
            w = weapons.get(slot);
        } catch (IndexOutOfBoundsException e) { }

        return w;
    }

    /**
     * Gets the consumable in the specified slot.
     * @param slot the slot indexed at 0
     * @return the consumable or null if slot is empty
     */
    public Consumable getConsumable(int slot) {
        Consumable c = null;

        try {
            c = consumables.get(slot);
        } catch (IndexOutOfBoundsException e) { }

        return c;
    }

    /**
     * Sets the weapon in the specified slot.
     * @param slot the slot indexed at 0
     * @param newWeapon the weapon to put in the slot
     * @return the weapon already in the slot or null if empty
     */
    public Weapon setWeapon(int slot, Weapon newWeapon) {
        // don't allow adding weapons that will exceed max amount
        if (slot >= maxWeapons)
            return newWeapon;
        
        // Check if there is a weapon in the current slot
        Weapon oldWeapon = null;
        try {
            oldWeapon = weapons.get(slot);
            weapons.set(slot, newWeapon);
        } catch (IndexOutOfBoundsException e) {
            weapons.add(newWeapon);
        }

        return oldWeapon;
    }

    /**
     * Sets the consumable in the specified slot.
     * @param slot the slot indexed at 0
     * @param newConsumable the consumable to put in the slot
     * @return the consumable already in the slot or null if empty
     */
    public Consumable setConsumable(int slot, Consumable newConsumable) {
        // don't allow adding consumables that will exceed max amount
        if (slot >= maxConsumables)
            return newConsumable;
        
        // Check if there is a consumable in the current slot
        Consumable oldConsumable = null;
        try {
            oldConsumable = consumables.get(slot);
            consumables.set(slot, newConsumable);
        } catch (IndexOutOfBoundsException e) {
            consumables.add(newConsumable);
        }

        return oldConsumable;
    }

    /**
     * Adds an item to an empty slot if there is one.
     * @param item the item to add
     * @return true if the item was added
     */
    public boolean addItem(Item item) {
        if (item instanceof Weapon w) {
            weapons.add(w);
            if (weapons.size() > maxWeapons) {
                weapons.remove(w);
                return false;
            }
        } else if (item instanceof Consumable c) {
            consumables.add(c);
            if (consumables.size() > maxConsumables) {
                consumables.remove(c);
                return false;
            }
        }
        return true;
    }
}
