package cpsc224.levels;

import cpsc224.creatures.CreatureFactory;
import cpsc224.levels.rooms.Room;

public class Level {

    private final Room[][] rooms;
    private Coordinate currentPosition;
    private int numRooms;
    private int roomLength;

    /**
     * Creates a level using the given initializer.
     * @param initializer the level initializer
     */
    public Level(LevelInitializer initializer) {
        rooms = initializer.initializeLevel();
        currentPosition = initializer.getStartRoom();
        setCurrentPosition(currentPosition);

        numRooms = initializer.getNumRooms() + 2;
        roomLength = initializer.getRoomLength();
    }

    /**
     * Gets the number of rooms in the level
     * @return the number of rooms
     */
    public int getNumRooms() {
        return numRooms;
    }

    /**
     * Gets the length of the level in rooms
     * @return the length
     */
    public int getRoomLength() {
        return rooms.length;
    }

    /**
     * Gets the 2D array of rooms
     * @return the rooms
     */
    public Room[][] getRooms() {
        return rooms;
    }

    /**
     * Gets a room at a specific coordinate
     * @param coord the coordinate of the room
     * @return the room at the coordinate
     */
    public Room getRoom(Coordinate coord) {
        return rooms[coord.getRow()][coord.getCol()];
    }

    /**
     * gets the current position of the player
     * @return the current position
     */
    public Coordinate getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Sets the current position of the player. Updates what rooms are discovered and explored.
     * @param currentPosition the new position of the player
     */
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
