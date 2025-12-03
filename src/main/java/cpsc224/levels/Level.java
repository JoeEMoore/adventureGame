package cpsc224.levels;

import cpsc224.levels.rooms.Room;

public class Level {

    private final Room[][] rooms;
    private int numRooms;
    private Coordinate currentPosition;

    public Level(LevelInitializer initializer) {
        rooms = initializer.initializeLevel();
        currentPosition = initializer.getStartRoom();
        setCurrentPosition(currentPosition);
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

    public Room getRoom(Coordinate coord) {
        return rooms[coord.getRow()][coord.getCol()];
    }

    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Coordinate currentPosition) {
        this.currentPosition = currentPosition;

        int row = currentPosition.getRow();
        int col = currentPosition.getCol();

        if (rooms[row][col] != null)
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
