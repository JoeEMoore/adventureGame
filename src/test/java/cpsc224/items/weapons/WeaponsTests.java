package cpsc224.items.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cpsc224.items.weapons.moves.Move;
import cpsc224.items.weapons.moves.MoveFactory;

public class WeaponsTests {
    
    @Test
    public void GettingAllAttributes () {
        Move slash = MoveFactory.createSlashMove();

        Weapon sword = new Weapon("sword", 1, slash);
        Weapon LongBow = new Weapon("Long bow", 2, null);
        
        assertEquals(sword.getName(), "sword");
        assertEquals(sword.getTier(), 1);
        assertEquals(sword.getMove(), slash);

        assertEquals(LongBow.getName(), "Long bow");
        assertEquals(LongBow.getTier(), 2);
        assertEquals(LongBow.getMove(), null);

    }
}
