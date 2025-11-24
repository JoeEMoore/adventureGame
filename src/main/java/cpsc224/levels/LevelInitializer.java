package cpsc224.levels;

import cpsc224.levels.rooms.Room;

public interface LevelInitializer {

    Room[][] initializeLevel(int roomLength, int numRooms);

    Coordinate getStartRoom();
}
