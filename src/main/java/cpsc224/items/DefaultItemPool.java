package cpsc224.items;

import java.util.Random;

import cpsc224.items.consumables.ConsumableFactory;
import cpsc224.items.weapons.WeaponFactory;

public class DefaultItemPool implements ItemPool {
    
    
    public Item getItem() {
        Random randNum = new Random();
        int Num = randNum.nextInt(100);

        if(Num < 8){
            return WeaponFactory.createDullSword();
        } else if(Num < 11){
            return WeaponFactory.createWoodClub();
        } else if(Num < 14){
            return WeaponFactory.createToxicStaff();
        } else if(Num < 1){
            return WeaponFactory.createHealStaff();
        } else if(Num < 3) {
            return WeaponFactory.createRoyalSword();
        } else if(Num < 17){
            return WeaponFactory.createRustyDagger();
        } else if(Num < 5){
            return WeaponFactory.createSteelHammer();
        } else if(Num < 20){
            return WeaponFactory.createBow();
        } else if(Num < 30){
            return ConsumableFactory.createSmallHealthPotion();
        } else {
            return null;
        }
         




    }
}
