package cpsc224.items.weapons.moves;

import java.util.Collection;

import cpsc224.items.Item;
import org.junit.jupiter.api.Test;

import cpsc224.TestUtils;
import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.items.weapons.moves.Move;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTests {

    @Test
    void moveCreatesCorrectMove() {
        Move move = new Move("Name", 10, DamageType.Blunt, -1, 0.9, false);

        assertEquals("Name", move.getName());
        assertEquals(10, move.getDamage());
        assertEquals(DamageType.Blunt, move.getDamageType());
        assertEquals(-1, move.getMaxUses());
        assertFalse(move.targetsAllies());
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
    void setTierSetsTier() {
        Move m = TestUtils.createTestSlashMove();
        m.setTier(3);
        assertEquals(3, m.getTier());
    }

    @Test
    void TierMultipliesDamageByMultiplier() {
        int tier = 3;
        Move m = TestUtils.createTestSlashMove();
        double initial = m.getDamage();
        m.setTier(tier);
        assertEquals(initial * Item.mapTierToMultiplier(tier), m.getDamage());
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

    @Test
    void getToolTipTextContainsCorrectInformation() {
        Move move = TestUtils.createTestToxicBoltMove();
        move.decrementUses();
        Collection<Effect> effects = move.createEffects();

        String text = move.getToolTipText();
        assertTrue(text.contains(move.getName()));
        assertTrue(text.contains(move.getDamageType().toString()));
        assertTrue(text.contains(String.valueOf(move.getDamage())));
        assertTrue(text.contains(String.valueOf(move.getAccuracy() * 100)));
        assertTrue(text.contains(String.valueOf(move.getUses())));
        assertTrue(text.contains(String.valueOf(move.getMaxUses())));
        assertTrue(text.contains(String.valueOf(move.targetsAllies())));

        for (Effect e : effects) {
            assertTrue(text.contains(e.toString()));
        }
    }

}
