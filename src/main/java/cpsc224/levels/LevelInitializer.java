package cpsc224.levels;

import cpsc224.creatures.Creature;
import cpsc224.items.Item;
import cpsc224.levels.rooms.shop.ShopInitializer;
import cpsc224.levels.rooms.Room;
import cpsc224.pools.BossCreaturePool;
import cpsc224.pools.Pool;

public abstract class LevelInitializer {

    protected int numRooms;
    protected int roomLength;
    protected Pool<Creature> creaturePool;
    protected Pool<Item> roomPool;
    protected ShopInitializer shopInitializer;
    protected Pool<Creature> bossPool;

    /**
     * Creates a level initializer,
     * @param numRooms the number of rooms to generate not including the boss room or shop
     * @param roomLength the length of the level in rooms
     * @param creaturePool the pool of creatures that can spawn in rooms
     * @param roomItemPool the pool of items that can generate in normal rooms
     * @param shopInitializer the shop generator
     */
    public LevelInitializer(int numRooms, int roomLength, Pool<Creature> creaturePool, Pool<Item> roomItemPool, Pool<Creature> bossPool, ShopInitializer shopInitializer) {
        this.numRooms = numRooms;
        this.roomLength = roomLength;
        this.creaturePool = creaturePool;
        this.roomPool = roomItemPool;
        this.shopInitializer = shopInitializer;
        this.bossPool = bossPool;
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
