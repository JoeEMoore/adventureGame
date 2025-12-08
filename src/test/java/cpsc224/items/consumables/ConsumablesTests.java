package cpsc224.items.consumables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.TestUtils;
import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureFactory;
import cpsc224.effects.Effect;
import cpsc224.effects.HealEffect;
import cpsc224.items.consumables.Consumable;

import cpsc224.items.weapons.Weapon;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumablesTests {

    @Test
    void createConsumableCreatesCorrectConsumable() {
        Consumable c = new Consumable("Consumable", 1,  () -> {return new ArrayList<>();},true);

        assertEquals("Consumable", c.toString());
        assertEquals(1, c.getTier());
        assertTrue(c.getAffectsSelf());
    }

    @Test
    void applyEffectsCreatesNewEffectsEachTime() {
        Consumable poisonPotion = TestUtils.createTestPoisonPotion();
        Creature rat = TestUtils.createTestRat();

        poisonPotion.applyEffects(rat);
        poisonPotion.applyEffects(rat);

        List<Effect> effects = rat.getEffects().stream().toList();
        assertEquals("Poison", effects.get(0).getName());
        assertEquals("Poison", effects.get(1).getName());
        assertNotEquals(effects.get(0), effects.get(1));
    }

    @Test
    void getToolTipTextContainsCorrectInformation() {
        Consumable c = TestUtils.createTestPoisonPotion();
        String text = c.getToolTipText();
        Collection<Effect> effects = c.createEffects();

        assertTrue(text.contains(c.getName()));
        assertTrue(text.contains(String.valueOf(c.getTier())));
        for (Effect e : effects) {
            assertTrue(text.contains(e.getName()));
        }
    }
}
