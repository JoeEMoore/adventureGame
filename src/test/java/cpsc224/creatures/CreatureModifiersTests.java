package cpsc224.creatures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cpsc224.DamageType;


public class CreatureModifiersTests {

    @Test
    public void cloneCreatesDeepCopy() {
        CreatureModifiers cm = new CreatureModifiers(10, 0.8, new double[] {1.0, 1.0, 1.0, 1.0});
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
