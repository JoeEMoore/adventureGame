package cpsc224.creatures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cpsc224.TestUtils;
import cpsc224.damagetypes.DamageType;


public class CreatureModifiersTests {

    @Test
    void creatureModifiersCreatesCorrectCreatureModifiers() {
        CreatureModifiers modifiers = new CreatureModifiers(2, 2, new LinkedList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0)));
    
        assertEquals(2, modifiers.getDamage());
        assertEquals(2, modifiers.getEvasion());
        assertEquals(1, modifiers.getResistance(DamageType.Blunt));
        assertEquals(2, modifiers.getResistance(DamageType.Slice));
        assertEquals(3, modifiers.getResistance(DamageType.Projectile));
        assertEquals(4, modifiers.getResistance(DamageType.Magic));
    }

    @Test
    void getResistanceReturnsOneWhenNoResistanceIsPresent() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        assertEquals(1, modifiers.getResistance(DamageType.Pure));
    }

    @Test
    void addDamageAddsCorrectDamage() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.addDamage(1);

        assertEquals(3, modifiers.getDamage());
    }

    @Test
    void multiplyDamageMultipliesCorrectDamage() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.multiplyDamage(2);

        assertEquals(4, modifiers.getDamage());
    }

    @Test
    void setDamageSetsCorrectDamage() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.setDamage(3);

        assertEquals(3, modifiers.getDamage());
    }

    @Test
    void addEvasionAddsCorrectEvasion() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.addEvasion(1);

        assertEquals(3, modifiers.getEvasion());
    }

    @Test
    void multiplyEvasionMultipliesCorrectEvasion() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.multiplyEvasion(2);

        assertEquals(4, modifiers.getEvasion());
    }

    @Test
    void setEvasionSetsCorrectEvasion() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.setEvasion(3);

        assertEquals(3, modifiers.getEvasion());
    }

    @Test
    void addResistanceAddsCorrectResistance() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.addResistance(DamageType.Blunt, 1);

        assertEquals(2, modifiers.getResistance(DamageType.Blunt));
    }

    @Test
    void multiplyResistanceMultipliesCorrectResistance() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.multiplyResistance(DamageType.Blunt, 2);

        assertEquals(2, modifiers.getResistance(DamageType.Blunt));
    }

    @Test
    void setResistanceSetsCorrectResistance() {
        CreatureModifiers modifiers = TestUtils.createTestModifiers();
        modifiers.setResistance(DamageType.Blunt, 3);

        assertEquals(3, modifiers.getResistance(DamageType.Blunt));
    }

    @Test
    public void cloneCreatesDeepCopy() {
        CreatureModifiers cm = new CreatureModifiers(10, 0.8, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        CreatureModifiers cm2 = cm.clone();

        assertEquals(cm.getDamage(), cm2.getDamage());
        assertEquals(cm.getEvasion(), cm2.getEvasion());
        assertEquals(cm.getResistance(DamageType.Slice), cm2.getResistance(DamageType.Slice));
        assertEquals(cm.getResistance(DamageType.Magic), cm2.getResistance(DamageType.Magic));

        cm.addDamage(1);
        cm.addEvasion(1);
        cm.addResistance(DamageType.Slice, 1);
        cm.addResistance(DamageType.Magic, -1);

        assertNotEquals(cm.getDamage(), cm2.getDamage());
        assertNotEquals(cm.getEvasion(), cm2.getEvasion());
        assertNotEquals(cm.getResistance(DamageType.Slice), cm2.getResistance(DamageType.Slice));
        assertNotEquals(cm.getResistance(DamageType.Magic), cm2.getResistance(DamageType.Magic));
    }

}
