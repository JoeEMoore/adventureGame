package cpsc224.items.consumables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;
import cpsc224.effects.Effect;
import org.junit.jupiter.api.Test;

public class ConsumablesTests {

    @Test
    void createConsumableCreatesCorrectConsumable() {
        Consumable consumable = new Consumable("Consumable", () -> {return new ArrayList<>();});

        assertEquals("Consumable", consumable.toString());
        assertEquals(0, consumable.applyEffects(CreatureFactory.createRat()).size());
    }

    @Test
    void applyEffectsCreatesNewEffectsEachTime() {
        Consumable poisonPotion = TestUtils.createTestPoisonPotion();
        Creature rat = TestUtils.createTestRat();

        poisonPotion.applyEffects(rat);
        poisonPotion.applyEffects(rat);

        List<Effect> effects = rat.getEffects().stream().toList();
        assertNotEquals(effects.get(0), effects.get(1));
    }
}
