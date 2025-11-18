package cpsc224.creatures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import cpsc224.TestUtils;
import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.effects.HealEffect;
import cpsc224.effects.PoisonEffect;
import cpsc224.items.Inventory;

public class CreatureTests {

    @Test
    void creatureCreatesCorrectCreature() {
        CreatureModifiers modifiers = new CreatureModifiers(1, 0.1, new LinkedList<>());
        Inventory inv = new Inventory();
        Creature c = new Creature("Creature", 100, modifiers, inv);

        assertEquals("Creature", c.getName());
        assertEquals(100, c.getMaxHealth());
        assertEquals(100, c.getHealth());
        assertEquals(modifiers, c.getBaseModifiers());
        assertEquals(inv, c.getInventory());
    }

    @Test
    void addEffectWithTurnsLeftAddsTheEffect() {
        Creature rat = TestUtils.createTestRat();
        Effect effect = new PoisonEffect(2);
        rat.addEffect(effect);

        assertTrue(rat.getEffects().contains(effect));
    }

    @Test 
    void addEffectWithNoTurnsLeftDoesNotAddEffect() {
        Creature rat = CreatureFactory.createRat();
        Effect effect = new PoisonEffect(0);
        rat.addEffect(effect);

        assertFalse(rat.getEffects().contains(effect));
    }

    @Test 
    void addEffectAppliesAffectThatIsInstant() {
        Creature rat = TestUtils.createTestRat();
        rat.applyDamage(5, DamageType.Pure);
        Effect effect = new HealEffect(5);
        rat.addEffect(effect);

        assertEquals(rat.getMaxHealth(), rat.getHealth());
    }

    @Test
    void clearEffectsClearsEffects() {
        Creature rat = TestUtils.createTestRat();
        Effect effect = new PoisonEffect(2);
        Effect effect2 = new HealEffect(25, 2);
        rat.addEffect(effect);
        rat.addEffect(effect2);
        rat.clearEffects();

        assertTrue(rat.getEffects().isEmpty());
    }

    @Test
    void getEffectsReturnsEffects() {
        Creature rat = TestUtils.createTestRat();
        Effect effect = new PoisonEffect(2);
        Effect effect2 = new HealEffect(25, 2);
        rat.addEffect(effect);
        rat.addEffect(effect2);

        assertTrue(rat.getEffects().contains(effect));
        assertTrue(rat.getEffects().contains(effect2));
        assertEquals(2, rat.getEffects().size());
    }

    @Test
    void calculateEffectsAppliesEachEffect() {
        Creature rat = TestUtils.createTestRat();
        Effect effect = new HealEffect(2, 2);
        Effect effect2 = new HealEffect(2, 2);
        rat.addEffect(effect);
        rat.addEffect(effect2);

        rat.applyDamage(4, DamageType.Pure);
        rat.calculateEffects();

        assertEquals(rat.getMaxHealth(), rat.getHealth());
    }

    @Test
    void calculateEffectsRemovesEffectsAfterTheyRunOutOfTurns() {
        Creature rat = TestUtils.createTestRat();
        Effect effect = new PoisonEffect(1);
        Effect effect2 = new PoisonEffect(1);
        rat.addEffect(effect);
        rat.addEffect(effect2);
        rat.calculateEffects();

        assertTrue(rat.getEffects().isEmpty());
    }

    @Test
    void calculateEffectsResetsTurnModifiersToCopyOfBaseModifiers() {
        Creature rat = TestUtils.createTestRat();
        rat.getTurnModifiers().addDamage(2);
        rat.getTurnModifiers().addEvasion(2);
        rat.getTurnModifiers().addResistance(DamageType.Blunt, 2);
        rat.calculateEffects();

        CreatureModifiers base = rat.getBaseModifiers();
        CreatureModifiers turn = rat.getTurnModifiers();

        assertEquals(base.getDamage(), turn.getDamage());
        assertEquals(base.getEvasion(), turn.getEvasion());
        assertEquals(base.getResistance(DamageType.Blunt), turn.getResistance(DamageType.Blunt));
    }

    @Test
    void getNameReturnsName() {
        Creature rat = TestUtils.createTestRat();

        assertEquals("Rat", rat.getName());
    }

    @Test
    void getHealthReturnsHealth() {
        Creature rat = TestUtils.createTestRat();
        rat.applyDamage(2, DamageType.Pure);

        assertEquals(8, rat.getHealth());
    }

    @Test
    void setHealthSetsHealthToCorrectValue() {
        Creature rat = TestUtils.createTestRat();
        rat.setHealth(5);

        assertEquals(5, rat.getHealth());
    }

    @Test
    void getMaxHealthReturnsMaxHealth() {
        Creature rat = TestUtils.createTestRat();

        assertEquals(10, rat.getMaxHealth());
    }

    @Test
    void getBaseModifiersReturnsBaseModifiers() {
        CreatureModifiers modifiers = new CreatureModifiers(1, 0.1, new LinkedList<>());
        Inventory inv = new Inventory();
        Creature c = new Creature("Creature", 100, modifiers, inv);

        assertEquals(modifiers, c.getBaseModifiers());
    }

    @Test
    void getInventoryReturnsInventory() {
        CreatureModifiers modifiers = new CreatureModifiers(1, 0.1, new LinkedList<>());
        Inventory inv = new Inventory();
        Creature c = new Creature("Creature", 100, modifiers, inv);

        assertEquals(inv, c.getInventory());
   }

    @Test
    void addHealthAddsAmountWhenResultLessThanMax() {
        Creature rat = TestUtils.createTestRat();
        rat.applyDamage(4, DamageType.Pure);
        double health = rat.addHealth(2);

        assertEquals(2, health);
        assertEquals(8, rat.getHealth());
    }

    @Test
    void addHealthAddsToMaxWhenAmountGreaterThanMax() {
        Creature rat = TestUtils.createTestRat();
        rat.applyDamage(2, DamageType.Pure);
        double health = rat.addHealth(4);

        assertEquals(2, health);
        assertEquals(10, rat.getHealth());
    }

    @Test
    void applyDamageAppliesDamageWhenResistanceIsOneAndDamageIsLessThanHealth() {
        Creature troll = TestUtils.createTestTroll();
        double damage = troll.applyDamage(4, DamageType.Projectile);

        assertEquals(4, damage);
        assertEquals(146, troll.getHealth());
    }

    @Test
    void applyDamageDoesNotRemoveHealthBelowZeroWhenDamageIsGreaterThanHealth() {
        Creature troll = TestUtils.createTestTroll();
        double damage = troll.applyDamage(160, DamageType.Projectile);

        assertEquals(160, damage);
        assertEquals(0, troll.getHealth());
    }

    @Test
    void applyDamageAppliesDoubleDamageWhenResistanceIsTwo() {
        Creature troll = TestUtils.createTestTroll();
        double damage = troll.applyDamage(4, DamageType.Magic);

        assertEquals(8, damage);
        assertEquals(142, troll.getHealth());
    }

    @Test
    void applyDamageAppliesHalfDamageWhenResistanceIsHalf() {
        Creature troll = TestUtils.createTestTroll();
        double damage = troll.applyDamage(4, DamageType.Blunt);

        assertEquals(2, damage);
        assertEquals(148, troll.getHealth());
    }

    @Test
    void applyPercentDamageAppliesDamageAsPercent() {
        Creature troll = TestUtils.createTestTroll();
        double damage = troll.applyPercentDamage(0.1, DamageType.Projectile);

        assertEquals(15, damage);
        assertEquals(135, troll.getHealth());
    }
}
