package cpsc224.items.consumables;
import cpsc224.items.Item;
import cpsc224.items.ItemPool;
import cpsc224.items.weapons.WeaponFactory;

import java.util.*;
public class ShopConsumablePool implements ItemPool {

    @Override
    public Item getItem() {
        Random rand = new Random();
        int randValue = rand.nextInt(1000);

        if(randValue < 100) {
            return ConsumableFactory.createSmallHealthPotion();
        } else if (randValue < 200) {
            return ConsumableFactory.createSmallDamagePotion();
        } else if (randValue < 300) {
            return ConsumableFactory.createMediumHealthPotion();
        } else if (randValue < 400) {
            return ConsumableFactory.createMediumDamagePotion();
        } else if (randValue < 475) {
            return ConsumableFactory.createLargeHealthPotion();
        } else if (randValue < 550) {
            return ConsumableFactory.createLargeDamagePotion();
        } else if (randValue < 700) {
            return ConsumableFactory.createWeaponRefillPotion();
        } else if (randValue < 850) {
            return ConsumableFactory.createStrengthPotion();
        } else {
            return ConsumableFactory.createResistancePotion();
        }
    }
}
