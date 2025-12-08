package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;
import cpsc224.damagetypes.DamageType;
import cpsc224.damagetypes.DamageTypeUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResistanceEffectTests {

    @Test
    void createResistanceEffectCreatesCorrectEffect() {
        int turns = 2;
        double res = 0.5;
        ResistanceEffect e = new ResistanceEffect(turns, res);

        assertEquals(turns, e.getTurns());
        assertEquals(res, e.getResistanceMultiplier());
        assertEquals("Damage Resistance", e.getName());
    }

    @Test
    void multiplyEffectCorrectlyMultipliesEffect() {
        int turns = 2;
        double res = 0.5;
        ResistanceEffect e = new ResistanceEffect(2, res);
        e.multiplyEffect(1.5);

        assertEquals(res * 1.5, e.getResistanceMultiplier());
    }

    @Test
    void applyCorrectlyAppliesEffect() {
        Creature rat = CreatureFactory.createRat();
        Effect resistance = new ResistanceEffect(1, 2);
        resistance.apply(rat);

        for (DamageType dt : DamageTypeUtils.getDamageTypes())
            assertEquals(rat.getBaseModifiers().getResistance(dt) * 2, rat.getTurnModifiers().getResistance(dt));
    }
}
