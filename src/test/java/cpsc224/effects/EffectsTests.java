package cpsc224.effects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cpsc224.damagetypes.DamageType;
import cpsc224.damagetypes.DamageTypeUtils;
import org.junit.jupiter.api.Test;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;

public class EffectsTests {

    @Test
    public void testHealEffect() { 
        Creature c1 = CreatureFactory.createRat();
        c1.setHealth(4);
        HealEffect h1 = new HealEffect(5);
        h1.apply(c1);
        assertEquals(9, c1.getHealth());

        Creature c2 = CreatureFactory.createRat();
        c2.setHealth(6);
        HealEffect h2 = new HealEffect(5);
        h2.apply(c2);
        assertEquals(10, c2.getHealth());
    }

    @Test
    public void testPoisonEffect() {
        Creature c1 = CreatureFactory.createRat();
        PoisonEffect p1 = new PoisonEffect(1);
        p1.apply(c1);
        assertEquals(c1.getMaxHealth() - c1.getMaxHealth() * PoisonEffect.PERCENT_DAMAGE, c1.getHealth());
    }

    @Test
    public void testStrengthEffect() {
        Creature rat = CreatureFactory.createRat();
        Effect strength = new StrengthEffect(1, 2);
        strength.apply(rat);
        assertEquals(rat.getBaseModifiers().getDamage() * 2, rat.getTurnModifiers().getDamage());
    }

    @Test
    public void testResistanceEffect() {
        Creature rat = CreatureFactory.createRat();
        Effect resistance = new ResistanceEffect(1, 2);
        resistance.apply(rat);

        for (DamageType dt : DamageTypeUtils.getDamageTypes())
            assertEquals(rat.getBaseModifiers().getResistance(dt) * 2, rat.getTurnModifiers().getResistance(dt));
    }
}

