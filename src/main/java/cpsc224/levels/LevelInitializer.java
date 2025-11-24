package cpsc224.levels;

public interface LevelInitializer {

    Room[][] initializeLevel(int roomLength, int numRooms);

    Coordinate getStartRoom();
}
