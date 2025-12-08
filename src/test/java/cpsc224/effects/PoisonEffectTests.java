package cpsc224.effects;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoisonEffectTests {

    @Test
    void createPoisonEffectCreatesCorrectEffect() {
        int turns = 2;
        PoisonEffect e = new PoisonEffect(turns);

        assertEquals(turns, e.getTurns());
        assertEquals("Poison", e.getName());
    }

    @Test
    void multiplyEffectCorrectlyMultipliesEffect() {
        int turns = 2;
        PoisonEffect e = new PoisonEffect(2);
        e.multiplyEffect(1.5);

        assertEquals(turns * 1.5, e.getTurns());
    }

    @Test
    void applyCorrectlyAppliesEffect() {
        Creature rat = TestUtils.createTestRat();
        PoisonEffect e = new PoisonEffect(2);
        e.applyEffect(rat);
        double expected = rat.getHealth() - rat.getMaxHealth() * PoisonEffect.PERCENT_DAMAGE;
        e.applyEffect(rat);

        assertEquals(expected, rat.getHealth());
    }
}
