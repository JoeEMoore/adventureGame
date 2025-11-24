package cpsc224.levels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;

public class DefaultLevelInitializer implements LevelInitializer {

    private Room[][] rooms;
    private HashSet<Coordinate> validPositions = new HashSet<>();
    private int numRooms;
    private int roomLength;
    Random rand;

    public DefaultLevelInitializer(int roomLength, int numRooms, long seed) {
        rooms = new Room[roomLength][roomLength];

        this.roomLength = roomLength;
        this.numRooms = numRooms;
        rand = new Random(seed);
    }

    @Override
    public Level initializeLevel() {
        
        validPositions.add(new Coordinate(rand.nextInt(roomLength), rand.nextInt(roomLength)));

        addRoom(new BossRoom());

        for (int i = 0; i < numRooms; i++) {
            Room room = new Room();
            room.addCreature(CreatureFactory.createGoblin());
            addRoom(room);
        }

        addRoom(new ShopRoom());

        return new Level(rooms);
    }

    private void addRoom(Room room) {
        Coordinate roomCoord = randomValidPosition();
        rooms[roomCoord.getRow()][roomCoord.getCol()] = room;
        updateValidPositions(roomCoord.getRow(), roomCoord.getCol());
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
        if (newRow > 0 && rooms[newRow - 1][newCol] == null)
            validPositions.add(new Coordinate(newRow - 1, newCol));

        if (newRow < numRooms - 1 && rooms[newRow + 1][newCol] == null)
            validPositions.add(new Coordinate(newRow + 1, newCol));

        if (newCol > 0 && rooms[newRow][newCol - 1] == null)
            validPositions.add(new Coordinate(newRow, newCol - 1));

        if (newCol < numRooms - 1 && rooms[newRow][newCol + 1] == null)
            validPositions.add(new Coordinate(newRow, newCol + 1));
    }
}
