package cpsc224.items;

import java.util.Random;

import cpsc224.items.consumables.ConsumableFactory;
import cpsc224.items.weapons.WeaponFactory;

public class DefaultItemPool implements ItemPool {
    
    
    public Item getItem() {
        Random rand = new Random();
        int randValue = rand.nextInt(1000);

        if(randValue < 30){
            return WeaponFactory.createSteelSword();
        } else if(randValue < 60){
            return WeaponFactory.createWoodClub();
        } else if(randValue < 90){
            return WeaponFactory.createRustyDagger();
        } else if(randValue < 120){
            return WeaponFactory.createBow();
        } else if(randValue < 140) {
            return WeaponFactory.createRoyalSword();
        } else if(randValue < 160){
            return WeaponFactory.createToxicStaff();
        } else if(randValue < 180){
            return WeaponFactory.createSteelHammer();
        } else if(randValue < 200){
            return WeaponFactory.createHealStaff();
        } else if(randValue < 250){
            return ConsumableFactory.createSmallHealthPotion();
        } else if (randValue < 300) {
            return ConsumableFactory.createSmallDamagePotion();
        } else if (randValue < 320) {
            return ConsumableFactory.createMediumHealthPotion();
        } else if (randValue < 340) {
            return ConsumableFactory.createMediumDamagePotion();
        } else if (randValue < 350) {
            return ConsumableFactory.createLargeHealthPotion();
        } else if (randValue < 360) {
            return ConsumableFactory.createLargeDamagePotion();
        } else if (randValue < 400) {
            return ConsumableFactory.createWeaponRefillPotion();
        } else {
            return null;
        }
    }
}
