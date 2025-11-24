package cpsc224.levels;

public class Level {

    private final Room[][] rooms;
    private int numRooms;
    private Coordinate startRoom;

    public Level(Room[][] rooms, int numRooms, Coordinate startRoom) {
        this.rooms = rooms;
        this.numRooms = numRooms;
        this.startRoom = startRoom;
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
