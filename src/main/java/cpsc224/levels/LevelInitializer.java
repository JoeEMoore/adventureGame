package cpsc224.levels;

import cpsc224.creatures.CreaturePool;
import cpsc224.levels.rooms.Room;

public abstract class LevelInitializer {

    protected int numRooms;
    protected int roomLength;
    protected CreaturePool creaturePool;

    public LevelInitializer(int numRooms, int roomLength, CreaturePool creaturePool) {
        this.numRooms = numRooms;
        this.roomLength = roomLength;
        this.creaturePool = creaturePool;
    }

    public abstract Room[][] initializeLevel();

    public abstract Coordinate getStartRoom();
}
