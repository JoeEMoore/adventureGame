package cpsc224.levels;

import cpsc224.levels.rooms.Room;

public class TestLevelInitializer implements LevelInitializer {

    Room[][] rooms;

    @Override
    public Room[][] initializeLevel() {
        rooms = new Room[1][1];
        rooms[0][0] = new Room();
        return rooms;
    }

    @Override
    public Coordinate getStartRoom() {
        return new Coordinate(0,0);
    }

    @Override
    public int getNumRooms() {
        return 1;
    }
}
