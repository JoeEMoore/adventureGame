package cpsc224.pools;

import cpsc224.items.Item;
import cpsc224.items.consumables.ConsumableFactory;
import cpsc224.items.weapons.WeaponFactory;

public class DefaultItemPool extends Pool<Item> {

    public DefaultItemPool() {
        addObjectCreator(WeaponFactory::createSteelSword, 2);
        addObjectCreator(WeaponFactory::createLongBow, 2);
        addObjectCreator(WeaponFactory::createSteelMace, 2);
        addObjectCreator(WeaponFactory::createRoyalSword, 1.5);
        addObjectCreator(WeaponFactory::createCrossbow, 1.5);
        addObjectCreator(WeaponFactory::createSteelHammer, 1.5);
        addObjectCreator(WeaponFactory::createEnchantedStaff, 1.5);
        addObjectCreator(WeaponFactory::createToxicStaff, 1.5);
        addObjectCreator(WeaponFactory::createHealStaff, 1.5);

        addObjectCreator(ConsumableFactory::createSmallHealthPotion, 3);
        addObjectCreator(ConsumableFactory::createSmallDamagePotion, 3);
        addObjectCreator(ConsumableFactory::createMediumHealthPotion, 2.5);
        addObjectCreator(ConsumableFactory::createMediumDamagePotion, 2.5);
        addObjectCreator(ConsumableFactory::createLargeHealthPotion, 2);
        addObjectCreator(ConsumableFactory::createLargeDamagePotion, 2);
        addObjectCreator(ConsumableFactory::createWeaponRefillPotion, 8);
        addObjectCreator(ConsumableFactory::createStrengthPotion, 5);
        addObjectCreator(ConsumableFactory::createResistancePotion, 5);
    }
}
