package cpsc224.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.weapons.Weapon;

public class Inventory {

    private int maxWeapons;
    private int maxConsumables;

    private List<Weapon> weapons = new ArrayList<Weapon>();
    private List<Consumable> consumables = new ArrayList<Consumable>();

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

<<<<<<< HEAD
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
        // don't allow adding consumables that will exceed max amount
        if (slot >= maxWeapons)
            return newWeapon;
        
        // Check if there is a weapon in the current slot
        Weapon oldWeapon = null;
        try {
            oldWeapon = weapons.get(slot);
        } catch (IndexOutOfBoundsException e) { }

        weapons.set(slot, newWeapon);
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
        } catch (IndexOutOfBoundsException e) { }

        consumables.set(slot, newConsumable);
        return oldConsumable;
=======
    public void increaseMaxInventory(int increaseAmt){
        this.maxInventory+= increaseAmt;
    }

    public void decreaseMaxInventory(int decreaseAmt){
        this.maxInventory-= decreaseAmt;
    }

    public void increaseMaxConsumables(int increaseAmt){
        this.maxConsumables+= increaseAmt;
    }

    public void decreaseMaxConsumables(int decreaseAmt){
        this.maxConsumables-= decreaseAmt;
>>>>>>> 2ade4f7e9026f2a6a46b8cf5744db3959f88ce34
    }

}
