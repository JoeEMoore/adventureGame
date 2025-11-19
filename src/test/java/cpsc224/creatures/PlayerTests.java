package cpsc224.creatures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cpsc224.items.Inventory;

public class PlayerTests {

    @Test
    void createPlayerCreatesCorrectPlayer() {
        CreatureModifiers modifiers = new CreatureModifiers(1, 0.1, new LinkedList<>());
        Inventory inv = new Inventory();
        Player p = new Player("Player", 100, modifiers, inv);

        assertEquals("Player", p.getName());
        assertEquals(100, p.getMaxHealth());
        assertEquals(100, p.getHealth());
        assertEquals(modifiers, p.getBaseModifiers());
        assertEquals(inv, p.getInventory());
    }

    @Test
    void addGoldAddsCorrectAmountOfGold() {
        Player p = CreatureFactory.createPlayer();
        assertEquals(0, p.getGold());
    
        p.addGold(7);
        assertEquals(7, p.getGold());
    }

}
