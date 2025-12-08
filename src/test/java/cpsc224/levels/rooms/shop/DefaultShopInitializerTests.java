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
        TestWeaponPool weaponPool = new TestWeaponPool(w);
        TestConsumablePool consumablePool = new TestConsumablePool(c);
        ShopInitializer init = new DefaultShopInitializer(weaponPool, consumablePool);

        for (ShopEntry e : init.generateWeaponEntries()) {
            assertEquals(w, e.getItem());
        }
    }

    @Test
    void generateConsumableEntriesGeneratesEntries() {
        Weapon w = TestUtils.createTestSword();
        Consumable c = TestUtils.createTestPoisonPotion();
        TestWeaponPool weaponPool = new TestWeaponPool(w);
        TestConsumablePool consumablePool = new TestConsumablePool(c);
        ShopInitializer init = new DefaultShopInitializer(weaponPool, consumablePool);

        for (ShopEntry e : init.generateConsumableEntries()) {
            assertEquals(c, e.getItem());
        }
    }
}
