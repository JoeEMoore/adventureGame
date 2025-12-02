package cpsc224.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import cpsc224.levels.rooms.Room;

public class DefaultInitializerTests {

    @Test
    void initializeLevelCreatesCorrectNumberOfRooms() {
        LevelInitializer init = new DefaultLevelInitializer();
        Room[][] rooms = init.initializeLevel(5, 10);

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
        LevelInitializer init = new DefaultLevelInitializer();
        Room[][] rooms = init.initializeLevel(5, 10);

        assertEquals(5, rooms.length);
        for (int i = 0; i < rooms.length; i++)
            assertEquals(5, rooms[i].length);
    }

    @Test
    void getStartRoomNotNull() {
        LevelInitializer init = new DefaultLevelInitializer();
        init.initializeLevel(5, 10);
        assertNotNull(init.getStartRoom());
    }
}
