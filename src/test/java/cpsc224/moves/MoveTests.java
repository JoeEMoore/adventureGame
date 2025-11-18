package cpsc224.moves;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import cpsc224.TestUtils;
import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;

public class MoveTests {

    @Test
    void moveCreatesCorrectMove() {
        Move move = new Move("Name", 10, DamageType.Blunt, -1, 0.9, false);

        assertEquals("Name", move.getName());
        assertEquals(10, move.getDamage());
        assertEquals(DamageType.Blunt, move.getDamageType());
        assertEquals(-1, move.getMaxUses());
        assertEquals(false, move.targetsAllies());
    }

    @Test
    void decrementUsesDecreasesUsesByOneIfUsesIsGreaterThanZero() {
        Move move = TestUtils.createTestToxicBoltMove();
        move.decrementUses();

        assertEquals(4, move.getUses());
    }

    @Test
    void decrementUsesDoesNotDecreaseUsesIfUsesIsNotPositive() {
        Move move = TestUtils.createTestSlashMove();
        int previousUses = move.getUses();
        move.decrementUses();

        assertEquals(previousUses, move.getUses());
    }

    @Test
    void resetUsesResetsUsesToMaxUses() {
        Move move = TestUtils.createTestToxicBoltMove();
        move.decrementUses();
        move.decrementUses();
        move.decrementUses();
        move.resetUses();

        assertEquals(5, move.getUses());
    }

    @Test
    void createEffectsCreatesNewEffectsEachTime() {
        Move move = TestUtils.createTestToxicBoltMove();

        Collection<Effect> effects1 = move.createEffects();
        Collection<Effect> effects2 = move.createEffects();

        assertEquals(1, effects1.size());
        assertEquals(1, effects2.size());

        for (Effect e1 : effects1) {
            for (Effect e2 : effects2)
                assertNotEquals(e1, e2);
        }
    }

}
