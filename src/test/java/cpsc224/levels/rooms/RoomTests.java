package cpsc224.levels.rooms;

import org.junit.jupiter.api.Test;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.items.Item;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTests {

    @Test
    void createRoomCreatesCorrectRoom() {
        Creature c = TestUtils.createTestRat();
        Room room = new Room(c);

        assertEquals(c, room.getCreature());
    }

    @Test
    void setCreatureSetsCorrectCreature() {
        Creature c = TestUtils.createTestRat();
        Room room = new Room();
        room.setCreature(c);

        assertEquals(c, room.getCreature());
    }

    @Test
    void removeCreatureSetsCreatureToNull() {
        Creature c = TestUtils.createTestRat();
        Room room = new Room();
        room.setCreature(c);
        assertTrue(room.hasCreature());

        room.removeCreature();
        assertNull(room.getCreature());
    }

    @Test
    void hasCreatureReturnsTrueIfCreatureIsPresent() {
        Creature c = TestUtils.createTestRat();
        Room room = new Room();
        room.setCreature(c);

        assertTrue(room.hasCreature());
    }

    @Test
    void hasCreatureReturnsFalseIfCreatureIsNotPresent() {
        Room room = new Room();
        assertFalse(room.hasCreature());
    }

    @Test
    void addItemAddsItem() {
        Item sword = TestUtils.createTestSword();
        Item potion = TestUtils.createTestPoisonPotion();
        Room room = new Room();
        room.addItem(sword);
        room.addItem(potion);

        assertTrue(room.getItems().contains(sword));
        assertTrue(room.getItems().contains(potion));
    }

    @Test
    void removeItemRemovesItem() {
        Item sword = TestUtils.createTestSword();
        Room room = new Room();
        room.addItem(sword);
        assertTrue(room.getItems().contains(sword));

        room.removeItem(sword);
        assertFalse(room.getItems().contains(sword));
    }

    @Test
    void clearItemClearsItems() {
        Item sword = TestUtils.createTestSword();
        Item potion = TestUtils.createTestPoisonPotion();
        Room room = new Room();
        room.addItem(sword);
        room.addItem(potion);
        assertTrue(room.getItems().contains(sword));
        assertTrue(room.getItems().contains(potion));

        room.clearItems();
        assertFalse(room.getItems().contains(sword));
        assertFalse(room.getItems().contains(potion));
    }

    @Test
    void hasItemsTrueIfRoomHasItems() {
        Room room = new Room();
        room.addItem(TestUtils.createTestSword());
        room.addItem(TestUtils.createTestPoisonPotion());
        assertTrue(room.hasItems());
    }

    @Test
    void hasItemsFalseIfRoomHasNoItems() {
        Room room = new Room();
    }

    @Test
    void setDiscoveredSetsRoomToDiscovered() {
        Room room = new Room();
        assertFalse(room.isDiscovered());

        room.setDiscovered(true);
        assertTrue(room.isDiscovered());
    }

    @Test
    void setExploredSetsRoomToExploredAndDiscovered() {
        Room room = new Room();
        assertFalse(room.isExplored());
        assertFalse(room.isDiscovered());

        room.setExplored(true);
        assertTrue(room.isExplored());
        assertTrue(room.isDiscovered());
    }
}
