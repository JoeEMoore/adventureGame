package cpsc224.items.shop;

import java.util.ArrayList;
import java.util.List;


import cpsc224.items.consumables.Consumable;
import cpsc224.items.consumables.ConsumablePool;
import cpsc224.items.weapons.Weapon;
import cpsc224.items.weapons.WeaponPool;

public class ShopGenerator {
    private final WeaponPool weaponPool;
    private final ConsumablePool consumablePool;

    public ShopGenerator(){
        this.weaponPool = new WeaponPool();
        this.consumablePool = new ConsumablePool();

    }

    public List<ShopEntry> generateWeaponEntries(){
        List<ShopEntry> entries = new ArrayList<>();
        List<Weapon> weapons = weaponPool.getRandomWeapons(5);
       
        for (Weapon w : weapons) {
            entries.add(new ShopEntry(w, 1));
        }
        return entries;
    }

    public List<ShopEntry> generateConsumableEntries(){
        List<ShopEntry> entries = new ArrayList<>();

        List<Consumable> consumables = consumablePool.getRandomConsumables(5);

        for (Consumable c : consumables){
            entries.add(new ShopEntry(c, 5));
        }
        return entries;
    }
}
