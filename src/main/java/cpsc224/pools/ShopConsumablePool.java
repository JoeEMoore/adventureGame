package cpsc224.pools;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.consumables.ConsumableFactory;

public class ShopConsumablePool extends Pool<Consumable> {

    public ShopConsumablePool() {
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
