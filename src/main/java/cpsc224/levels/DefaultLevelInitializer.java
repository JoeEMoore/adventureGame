package cpsc224.levels;

import java.util.HashSet;
import java.util.Random;

import cpsc224.creatures.Creature;
import cpsc224.items.Item;
import cpsc224.levels.rooms.shop.ShopInitializer;
import cpsc224.levels.rooms.BossRoom;
import cpsc224.levels.rooms.Room;
import cpsc224.levels.rooms.shop.ShopRoom;
import cpsc224.pools.Pool;

public class DefaultLevelInitializer implements LevelInitializer {

    private Room[][] rooms;
    private Coordinate startRoom;
    private HashSet<Coordinate> validPositions = new HashSet<>();
    protected int numRooms;
    protected int roomLength;
    protected Pool<Creature> creaturePool;
    protected Pool<Item> roomPool;
    protected ShopInitializer shopInitializer;
    protected Pool<Creature> bossPool;

    Random rand = new Random();

    /**
     * Creates a level initializer,
     * @param numRooms the number of rooms to generate not including the boss room or shop
     * @param roomLength the length of the level in rooms
     * @param creaturePool the pool of creatures that can spawn in rooms
     * @param roomItemPool the pool of items that can generate in normal rooms
     * @param shopInitializer the shop generator
     */
    public DefaultLevelInitializer(int numRooms, int roomLength, Pool<Creature> creaturePool, Pool<Item> roomItemPool, Pool<Creature> bossPool, ShopInitializer shopInitializer) {
        this.numRooms = numRooms;
        this.roomLength = roomLength;
        this.creaturePool = creaturePool;
        this.roomPool = roomItemPool;
        this.shopInitializer = shopInitializer;
        this.bossPool = bossPool;
    }

    /**
     * Creates rooms for a level. Includes the number of Rooms specified in the constructor
     * plus a ShopRoom and BossRoom.
     * @return the Rooms in a 2D array
     */
    @Override
    public Room[][] initializeLevel() {
        rooms = new Room[roomLength][roomLength];
        validPositions.add(new Coordinate(rand.nextInt(roomLength), rand.nextInt(roomLength)));

        Room bossRoom = new BossRoom();
        addRoom(bossRoom);
        bossRoom.setCreature(bossPool.createNew());

        for (int i = 0; i < numRooms - 1; i++) {
            Room room = new Room();

            // 80% chance to add creature
            if (rand.nextInt(100) < 80)
                room.setCreature(creaturePool.createNew());

            // 40% chance to add item
            if (rand.nextInt(100) < 40)
                room.addItem(roomPool.createNew());

            addRoom(room);
        }
        
        startRoom = addRoom(new Room()); // second last room is empty start room
        addRoom(new ShopRoom(shopInitializer));
        return rooms;
    }

    /**
     * Gets the coordinate of the Room in which the player starts.
     * @return the start Room position
     */
    @Override
    public Coordinate getStartRoom() {
        return startRoom;
    }

    /**
     * Gets the number of rooms not counting the shop or boss room.
     * @return number of rooms
     */
    @Override
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

    private Coordinate addRoom(Room room) {
        Coordinate roomCoord = randomValidPosition();
        rooms[roomCoord.getRow()][roomCoord.getCol()] = room;
        updateValidPositions(roomCoord.getRow(), roomCoord.getCol());
        return roomCoord;
    }

    private Coordinate randomValidPosition() {
        int size = validPositions.size();
        int randRoom = rand.nextInt(size);
        int i = 0;
        for(Coordinate coord : validPositions)
        {
            if (i == randRoom)
                return coord;
            i++;
        }
        return null;
    }

    private void updateValidPositions(int newRow, int newCol) {
        validPositions.remove(new Coordinate(newRow, newCol));

        if (newRow > 0 && rooms[newRow - 1][newCol] == null)
            validPositions.add(new Coordinate(newRow - 1, newCol));

        if (newRow < roomLength - 1 && rooms[newRow + 1][newCol] == null)
            validPositions.add(new Coordinate(newRow + 1, newCol));

        if (newCol > 0 && rooms[newRow][newCol - 1] == null)
            validPositions.add(new Coordinate(newRow, newCol - 1));

        if (newCol < roomLength - 1 && rooms[newRow][newCol + 1] == null)
            validPositions.add(new Coordinate(newRow, newCol + 1));

    }

}
