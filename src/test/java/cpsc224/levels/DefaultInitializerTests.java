package cpsc224.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cpsc224.items.consumables.ShopConsumablePool;
import cpsc224.items.shop.DefaultShopInitializer;
import cpsc224.items.shop.ShopInitializer;
import cpsc224.items.weapons.ShopWeaponPool;
import org.junit.jupiter.api.Test;

import cpsc224.creatures.DefaultCreaturePool;
import cpsc224.items.DefaultItemPool;
import cpsc224.levels.rooms.Room;

public class DefaultInitializerTests {

    @Test
    void initializeLevelCreatesCorrectNumberOfRooms() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        LevelInitializer init = new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit);
        Room[][] rooms = init.initializeLevel();

        int count = 0;
        for (Room[] room : rooms) {
            for (Room value : room) {
                if (value != null)
                    count++;
            }
        }

        // 10 rooms plus boss room plus shop
        assertEquals(12, count);
    }

    @Test
    void initializeLevelCreatesCorrectLengthRoomsArray() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        LevelInitializer init = new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit);
        Room[][] rooms = init.initializeLevel();

        assertEquals(5, rooms.length);
        for (Room[] room : rooms)
            assertEquals(5, room.length);
    }

    @Test
    void getStartRoomNotNull() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        LevelInitializer init = new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit);
        Room[][] rooms = init.initializeLevel();
        assertNotNull(init.getStartRoom());
    }
}
