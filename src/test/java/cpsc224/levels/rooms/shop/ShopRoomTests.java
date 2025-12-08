package cpsc224.levels.rooms.shop;

import cpsc224.pools.ShopConsumablePool;
import cpsc224.pools.ShopWeaponPool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShopRoomTests {

    @Test
    void createShopCreatesCorrectShop() {
        ShopInitializer init = new TestShopInitializer();
        ShopRoom s = new ShopRoom(init);

        assertEquals(init.generateWeaponEntries(), s.getWeaponEntries());
        assertEquals(init.generateConsumableEntries(), s.getConsumableEntries());
    }
}
