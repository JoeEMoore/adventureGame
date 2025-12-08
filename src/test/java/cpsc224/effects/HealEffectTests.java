package cpsc224.effects;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealEffectTests {

    @Test
    void createHealEffectCreatesCorrectEffect() {
        int turns = 2;
        double heal = 20;
        HealEffect e = new HealEffect(turns, heal);

        assertEquals(turns, e.getTurns());
        assertEquals(heal, e.getHealAmount());
        assertEquals("Heal", e.getName());
    }

    @Test
    void multiplyEffectCorrectlyMultipliesEffect() {
        double heal = 20;
        HealEffect e = new HealEffect(2, heal);
        e.multiplyEffect(1.5);

        assertEquals(heal * 1.5, e.getHealAmount());
    }

    @Test
    void applyCorrectlyAppliesEffect() {
        Creature rat = TestUtils.createTestRat();
        HealEffect e = new HealEffect(5, 2);
        rat.applyDamage(5, DamageType.Pure);
        double expected = Math.min(rat.getMaxHealth(), rat.getHealth() + e.getHealAmount());
        e.applyEffect(rat);

        assertEquals(expected, rat.getHealth());
    }
}
