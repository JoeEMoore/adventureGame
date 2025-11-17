package cpsc224.creatures;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cpsc224.items.Inventory;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {
    
    @Test
    public void PlayerGetsAndAddsGold() {
        CreatureModifiers cm = new CreatureModifiers(10, 0.8, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        Inventory inv = new Inventory(4, 4);
        Player p1 = new Player("player1", 100, cm, inv);
        
        assertEquals(p1.getGold(), 0);
        p1.addGold(5);
        assertEquals(p1.getGold(), 5);     
        
        
    }

}
