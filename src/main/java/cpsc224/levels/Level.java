package cpsc224.levels;

import cpsc224.levels.rooms.Room;

public class Level {

    private final Room[][] rooms;
    private int numRooms;
    private Coordinate currentRoom;

    public Level(int roomLength, int numRooms, LevelInitializer initializer) {
        this.numRooms = numRooms;

        rooms = initializer.initializeLevel(roomLength, numRooms);
        currentRoom = initializer.getStartRoom();
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

    public Coordinate getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Coordinate currentRoom) {
        this.currentRoom = currentRoom;
    }
}
