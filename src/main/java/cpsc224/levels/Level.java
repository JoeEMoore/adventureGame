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
        setCurrentRoom(currentRoom);
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

        int row = currentRoom.getRow();
        int col = currentRoom.getCol();

        rooms[row][col].setExplored(true);

        if (row < rooms.length - 1 && rooms[row + 1][col] != null)
            rooms[row + 1][col].setDiscovered(true);
        if (row > 0 && rooms[row - 1][col] != null)
            rooms[row - 1][col].setDiscovered(true);
        if (col < rooms[row].length - 1 && rooms[row][col + 1] != null)
            rooms[row][col + 1].setDiscovered(true);
        if (col > 0 && rooms[row][col - 1] != null)
            rooms[row][col - 1].setDiscovered(true);
    }
}
