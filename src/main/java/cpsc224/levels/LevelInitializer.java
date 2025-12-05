package cpsc224.levels;

import cpsc224.creatures.CreaturePool;
import cpsc224.items.ItemPool;
import cpsc224.levels.rooms.Room;

public abstract class LevelInitializer {

    protected int numRooms;
    protected int roomLength;
    protected CreaturePool creaturePool;
    protected ItemPool roomPool;

    /**
     * Creates a level initializer,
     * @param numRooms the number of rooms to generate not including the boss room or shop
     * @param roomLength the length of the level in rooms
     * @param creaturePool the pool of creatures that can spawn in rooms
     * @param roomItemPool the pool of items that can generate in normal rooms
     */
    public LevelInitializer(int numRooms, int roomLength, CreaturePool creaturePool, ItemPool roomItemPool) {
        this.numRooms = numRooms;
        this.roomLength = roomLength;
        this.creaturePool = creaturePool;
        this.roomPool = roomItemPool;
    }

    /**
     * Gets the number of rooms not counting the shop or boss room.
     * @return number of rooms
     */
    public int getNumRooms() {
        return numRooms;
    }

    /**
     * Gets the length of the level in rooms.
     * @return the room length of the level
     */
    public int getRoomLength() {
        return roomLength;
    }

    public abstract Room[][] initializeLevel();

    public abstract Coordinate getStartRoom();
}
