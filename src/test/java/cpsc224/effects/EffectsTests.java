package cpsc224.effects;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(c2.getHealth(), 10);
    }

    @Test
    public void TestPoisonEffect() {
        Creature c1 = CreatureFactory.createRat();
        PoisonEffect p1 = new PoisonEffect(1);
        p1.apply(c1);
        assertEquals(c1.getHealth(), 10 - 10 * 0.05);
    }
}

