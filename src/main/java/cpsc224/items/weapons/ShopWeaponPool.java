package cpsc224.items.weapons;
import cpsc224.items.Item;
import cpsc224.items.ItemPool;

import java.util.*;
public class ShopWeaponPool implements ItemPool {

    @Override
    public Item getItem() {
        Random rand = new Random();
        int randValue = rand.nextInt(1000);

        if (randValue < 200) {
            return WeaponFactory.createSteelSword();
        } else if (randValue < 400) {
            return WeaponFactory.createCrudeBow();
        } else if (randValue < 600) {
            return WeaponFactory.createWoodClub();
        } else if (randValue < 700) {
            return WeaponFactory.createToxicStaff();
        } else if (randValue < 800) {
            return WeaponFactory.createRoyalSword();
        } else if (randValue < 900) {
            return WeaponFactory.createSteelHammer();
        } else {
            return WeaponFactory.createHealStaff();
        }
    }
}
