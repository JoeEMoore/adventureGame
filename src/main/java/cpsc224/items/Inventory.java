package cpsc224.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;

public class Inventory {

    private int maxWeapons;
    private int maxConsumables;

    private List<Weapon> weapons = new ArrayList<Weapon>();
    private List<Consumable> consumables = new ArrayList<Consumable>();

    public Inventory() {
        maxWeapons = 1;
        maxConsumables = 0;
    }

    public Inventory(int maxWeapons, int maxConsumables) {
        this.maxWeapons = maxWeapons;
        this.maxConsumables = maxConsumables;
    }

    public int getMaxWeapons(){
        return maxWeapons;
    }

    public int getMaxConsumables(){
        return maxConsumables;
    }

    public void incrementMaxWeapons() {
        maxWeapons++;
    }

    public void incrementMaxConsumables() {
        maxConsumables++;
    }

    public Collection<Weapon> getWeapons() {
        return weapons;
    }

    public Collection<Consumable> getConsumables() {
        return consumables;
    }

    public Weapon getWeapon(int slot) {
        Weapon w = null;

        try {
            w = weapons.get(slot);
        } catch (IndexOutOfBoundsException e) { }

        return w;
    }

    public Consumable getConsumable(int slot) {
        Consumable c = null;

        try {
            c = consumables.get(slot);
        } catch (IndexOutOfBoundsException e) { }

        return c;
    }

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

}
