package cpsc224.levels;

import java.util.HashSet;
import java.util.Random;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.CreaturePool;
import cpsc224.levels.rooms.BossRoom;
import cpsc224.levels.rooms.Room;
import cpsc224.levels.rooms.ShopRoom;

public class DefaultLevelInitializer extends LevelInitializer {

    private Room[][] rooms;
    private Coordinate startRoom;
    private HashSet<Coordinate> validPositions = new HashSet<>();

    Random rand = new Random();

    public DefaultLevelInitializer(int numRooms, int roomLength, CreaturePool creaturePool) {
        super(numRooms, roomLength, creaturePool);
    }

    @Override
    public Room[][] initializeLevel() {
        rooms = new Room[roomLength][roomLength];
        validPositions.add(new Coordinate(rand.nextInt(roomLength), rand.nextInt(roomLength)));

        addRoom(new BossRoom());

        for (int i = 0; i < numRooms - 1; i++) {
            Room room = new Room();
            room.setCreature(CreatureFactory.createRat());
            addRoom(room);
        }
        
        startRoom = addRoom(new Room()); // second last room is empty start room

        addRoom(new ShopRoom());

        return rooms;
    }

    @Override
    public Coordinate getStartRoom() {
        return startRoom;
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
