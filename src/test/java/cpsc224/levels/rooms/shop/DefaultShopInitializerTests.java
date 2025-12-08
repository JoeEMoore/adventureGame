package cpsc224.levels.rooms.shop;

import cpsc224.TestUtils;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.TestConsumablePool;
import cpsc224.pools.TestWeaponPool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultShopInitializerTests {

    @Test
    void generateWeaponEntriesGeneratesEntries() {
        Weapon w = TestUtils.createTestSword();
        Consumable c = TestUtils.createTestPoisonPotion();
        TestWeaponPool weaponPool = new TestWeaponPool(TestUtils::createTestSword);
        TestConsumablePool consumablePool = new TestConsumablePool(TestUtils::createTestPoisonPotion);
        ShopInitializer init = new DefaultShopInitializer(weaponPool, consumablePool, 3, 1, 3, 3);

        for (ShopEntry e : init.generateWeaponEntries()) {
            assertEquals(w.getName(), e.getItem().getName());
            assertEquals(w.getTier(), e.getItem().getTier());
        }
    }

    @Test
    void generateConsumableEntriesGeneratesEntries() {
        Weapon w = TestUtils.createTestSword();
        Consumable c = TestUtils.createTestPoisonPotion();
        TestWeaponPool weaponPool = new TestWeaponPool(TestUtils::createTestSword);
        TestConsumablePool consumablePool = new TestConsumablePool(TestUtils::createTestPoisonPotion);
        ShopInitializer init = new DefaultShopInitializer(weaponPool, consumablePool, 3, 1, 3, 3);

        for (ShopEntry e : init.generateConsumableEntries()) {
            assertEquals(c.getName(), e.getItem().getName());
            assertEquals(c.getTier(), e.getItem().getTier());
        }
    }
}
