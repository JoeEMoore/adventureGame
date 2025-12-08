package cpsc224.effects;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;
import cpsc224.items.Inventory;
import cpsc224.items.weapons.Weapon;
import cpsc224.items.weapons.moves.Move;
import org.junit.jupiter.api.Test;

import java.sql.Ref;

import static org.junit.jupiter.api.Assertions.*;

public class RefillEffectTests {

    @Test
    void createRefillEffectCreatesCorrectEffect() {
        int turns = 1;
        RefillEffect e = new RefillEffect(turns);

        assertEquals(turns, e.getTurns());
        assertEquals("Refill Moves", e.getName());
    }

    @Test
    void multiplyEffectThrowsAnException() {
        RefillEffect e = new RefillEffect(1);

        assertThrows(UnsupportedOperationException.class, () -> e.multiplyEffect(2));
    }

    @Test
    void applyCorrectlyAppliesEffect() {
        Creature player = TestUtils.createTestPlayer();
        Inventory inv = player.getInventory();
        Weapon w = TestUtils.createTestToxicStaff();
        Weapon w2 = TestUtils.createTestToxicStaff();
        Move m = w.getMove();
        Move m2 = w2.getMove();
        inv.setWeapon(1, w);
        inv.setWeapon(2, w2);
        m.decrementUses();
        m2.decrementUses();
        assertTrue(m.getUses() < m.getMaxUses());
        assertTrue(m2.getUses() < m2.getMaxUses());
        new RefillEffect(1).applyEffect(player);

        assertEquals(m.getUses(), m.getMaxUses());
        assertEquals(m2.getUses(), m2.getMaxUses());
    }
}
