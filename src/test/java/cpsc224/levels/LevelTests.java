package cpsc224.levels;

import cpsc224.pools.DefaultCreaturePool;
import cpsc224.pools.DefaultItemPool;
import cpsc224.pools.ShopConsumablePool;
import cpsc224.levels.rooms.shop.DefaultShopInitializer;
import cpsc224.levels.rooms.shop.ShopInitializer;
import cpsc224.pools.ShopWeaponPool;
import cpsc224.levels.rooms.Room;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LevelTests {

    @Test
    void createLevelCreatesCorrectLevel() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        Level level = new Level(new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit));

        assertEquals(5, level.getRoomLength());
        assertEquals(12, level.getNumRooms()); // 2 extra created by DefaultLevelInitializer for boss and shop
    }

    @Test
    void getRoomReturnsCorrectRoom() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        Level level = new Level(new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit));
        Coordinate coord = new Coordinate(1, 2);
        Room[][] rooms = level.getRooms();

        assertEquals(rooms[1][2], level.getRoom(coord));
    }

    @Test
    void setCurrentPositionCorrectlySetsPosition() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        Level level = new Level(new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit));
        level.setCurrentPosition(new Coordinate(2, 4));

        assertEquals(new Coordinate(2, 4), level.getCurrentPosition());
    }

    @Test
    void setCurrentPositionExploresRoom() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        Level level = new Level(new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit));
        level.setCurrentPosition(new Coordinate(2, 4));

        Room room = level.getRoom(level.getCurrentPosition());
        assertTrue(room == null || room.isExplored());
    }

    @Test
    void setCurrentPositionDiscoversAdjacentRooms() {
        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        Level level = new Level(new DefaultLevelInitializer(10, 5, new DefaultCreaturePool(), new DefaultItemPool(), shopInit));
        level.setCurrentPosition(new Coordinate(2, 3));

        Room room1 = level.getRoom(new Coordinate(1, 3));
        assertTrue(room1 == null || room1.isDiscovered());

        Room room2 = level.getRoom(new Coordinate(3, 3));
        assertTrue(room2 == null || room2.isDiscovered());

        Room room3 = level.getRoom(new Coordinate(2, 2));
        assertTrue(room3 == null || room3.isDiscovered());

        Room room4 = level.getRoom(new Coordinate(2, 4));
        assertTrue(room4 == null || room4.isDiscovered());
    }
}