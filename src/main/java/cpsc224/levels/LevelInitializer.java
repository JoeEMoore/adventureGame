package cpsc224.levels;

import cpsc224.creatures.Creature;
import cpsc224.items.Item;
import cpsc224.levels.rooms.shop.ShopInitializer;
import cpsc224.levels.rooms.Room;
import cpsc224.pools.BossCreaturePool;
import cpsc224.pools.Pool;

public interface LevelInitializer {

    Room[][] initializeLevel();

    Coordinate getStartRoom();

    int getNumRooms();
}
