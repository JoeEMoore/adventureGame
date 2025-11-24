package cpsc224.levels;

public class Level {

    private final Room[][] rooms;
    private int numRooms;
    private Coordinate startRoom;

    public Level(int roomLength, int numRooms, LevelInitializer initializer) {
        this.numRooms = numRooms;

        rooms = initializer.initializeLevel(roomLength, numRooms);
        startRoom = initializer.getStartRoom();
    }

    public int getRoomLength() {
        return rooms.length;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public Room[][] getRooms() {
        return rooms;
    }

    public Coordinate getStartRoom() {
        return startRoom;
    }
}
