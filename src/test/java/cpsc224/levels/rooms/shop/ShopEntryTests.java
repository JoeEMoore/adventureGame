package cpsc224.levels.rooms.shop;

import cpsc224.TestUtils;
import cpsc224.items.Item;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.Pool;
import cpsc224.pools.ShopConsumablePool;
import cpsc224.pools.ShopWeaponPool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopEntryTests {

    @Test
    void createShopEntryCreatesCorrectEntry() {
        Item i = TestUtils.createTestPoisonPotion();
        int price = 20;
        int quantity = 3;
        ShopEntry shopEntry = new ShopEntry(i, quantity, price);

        assertEquals(i, shopEntry.getItem());
        assertEquals(price, shopEntry.getPrice());
        assertEquals(quantity, shopEntry.getQuantity());
    }

    @Test
    void decrementQuantityDecrementsQuantityIfQuantityGreaterThanZero() {
        Item i = TestUtils.createTestPoisonPotion();
        int price = 20;
        int quantity = 3;
        ShopEntry shopEntry = new ShopEntry(i, quantity, price);
        shopEntry.decreaseQuantity();

        assertEquals(quantity - 1, shopEntry.getQuantity());
    }

    @Test
    void decrementQuantityDoesNotDecrementsQuantityIfQuantityEqualToZero() {
        Item i = TestUtils.createTestPoisonPotion();
        int price = 20;
        int quantity = 0;
        ShopEntry shopEntry = new ShopEntry(i, quantity, price);
        shopEntry.decreaseQuantity();

        assertEquals(0, shopEntry.getQuantity());
    }
}
