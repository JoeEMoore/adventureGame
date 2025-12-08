package cpsc224.effects;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DamageEffectTests {

    @Test
    void createDamageEffectCreatesCorrectEffect() {
        int turns = 2;
        double damage = 20;
        DamageEffect e = new DamageEffect(turns, damage);

        assertEquals(turns, e.getTurns());
        assertEquals(damage, e.getDamageAmount());
        assertEquals("Damage", e.getName());
    }

    @Test
    void multiplyEffectCorrectlyMultipliesEffect() {
        double damage = 20;
        DamageEffect e = new DamageEffect(2, damage);
        e.multiplyEffect(1.5);

        assertEquals(damage * 1.5, e.getDamageAmount());
    }

    @Test
    void applyCorrectlyAppliesEffect() {
        Creature rat = TestUtils.createTestRat();
        DamageEffect e = new DamageEffect(5, 2);
        e.applyEffect(rat);
        double expected = Math.max(rat.getMaxHealth() - e.getDamageAmount(), 0);

        assertEquals(expected, rat.getHealth());
    }
}
