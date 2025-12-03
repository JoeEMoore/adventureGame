package cpsc224.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import cpsc224.creatures.DefaultCreaturePool;
import cpsc224.items.DefaultItemPool;
import cpsc224.levels.rooms.Room;

public class DefaultInitializerTests {

    @Test
    void initializeLevelCreatesCorrectNumberOfRooms() {
        LevelInitializer init = new DefaultLevelInitializer(5, 10, new DefaultCreaturePool(), new DefaultItemPool());
        Room[][] rooms = init.initializeLevel();

        int count = 0;
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (rooms[i][j] != null)
                    count++;
            }
        }

        // 10 rooms plus boss room plus shop
        assertEquals(12, count);
    }

    @Test
    void initializeLevelCreatesCorrectLengthRoomsArray() {
        LevelInitializer init = new DefaultLevelInitializer(5, 10, new DefaultCreaturePool(), new DefaultItemPool());
        Room[][] rooms = init.initializeLevel();

        assertEquals(5, rooms.length);
        for (int i = 0; i < rooms.length; i++)
            assertEquals(5, rooms[i].length);
    }

    @Test
    void getStartRoomNotNull() {
        LevelInitializer init = new DefaultLevelInitializer(5, 10, new DefaultCreaturePool(), new DefaultItemPool());
        Room[][] rooms = init.initializeLevel();
        assertNotNull(init.getStartRoom());
    }
}
