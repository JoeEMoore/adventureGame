package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrengthEffectTests {

    @Test
    void createStrengthEffectCreatesCorrectEffect() {
        int turns = 2;
        double strength = 2;
        StrengthEffect e = new StrengthEffect(turns, strength);

        assertEquals(turns, e.getTurns());
        assertEquals(strength, e.getDamageMultiplier());
        assertEquals("Strength", e.getName());
    }

    @Test
    void multiplyEffectCorrectlyMultipliesEffect() {
        int turns = 2;
        double damage = 2;
        StrengthEffect e = new StrengthEffect(2, damage);
        e.multiplyEffect(1.5);

        assertEquals(damage * 1.5, e.getDamageMultiplier());
    }

    @Test
    public void applyCorrectlyAppliesEffect() {
        double damageMultiplier = 2;
        Creature rat = CreatureFactory.createRat();
        Effect strength = new StrengthEffect(1, damageMultiplier);
        strength.apply(rat);
        assertEquals(rat.getBaseModifiers().getDamage() * damageMultiplier, rat.getTurnModifiers().getDamage());
    }
}
