package cpsc224.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cpsc224.pools.*;
import cpsc224.levels.rooms.shop.DefaultShopInitializer;
import org.junit.jupiter.api.Test;

import cpsc224.levels.rooms.Room;

public class DefaultLevelInitializerTests {

    DefaultShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool(), 1, 1, 1, 1);

    @Test
    void initializeLevelCreatesCorrectNumberOfRooms() {
        LevelInitializer init = new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), new BossCreaturePool(), shopInit);
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
        LevelInitializer init = new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), new BossCreaturePool(), shopInit);
        Room[][] rooms = init.initializeLevel();

        assertEquals(5, rooms.length);
        for (Room[] room : rooms)
            assertEquals(5, room.length);
    }

    @Test
    void getStartRoomNotNull() {
        LevelInitializer init = new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), new BossCreaturePool(), shopInit);
        Room[][] rooms = init.initializeLevel();
        assertNotNull(init.getStartRoom());
    }
}
