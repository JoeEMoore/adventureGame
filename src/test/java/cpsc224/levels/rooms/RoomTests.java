package cpsc224.levels.rooms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.items.Item;

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
}
