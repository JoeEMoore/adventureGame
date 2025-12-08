package cpsc224.items.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cpsc224.TestUtils;
import org.junit.jupiter.api.Test;

import cpsc224.items.weapons.moves.Move;
import cpsc224.items.weapons.moves.MoveFactory;

import javax.swing.*;

public class WeaponsTests {
    
    @Test
    public void GettingAllAttributes () {
        Move slash = MoveFactory.createSlashMove();

        Weapon sword = new Weapon("sword", 1, slash);
        Weapon LongBow = new Weapon("Long bow", 2, null);
        
        assertEquals(sword.getName(), "sword");
        assertEquals(sword.getTier(), 1);
        assertEquals(sword.getMove(), slash);

        assertEquals(LongBow.getName(), "Long bow");
        assertEquals(LongBow.getTier(), 2);
        assertEquals(LongBow.getMove(), null);

    }

    @Test
    void toStringReturnsCorrectStringForWeaponWithUnlimitedUses() {
        String name = "Sword";
        Move m = TestUtils.createTestSlashMove();
        Weapon w = new Weapon(name, 2, m);

        assertEquals(name, w.toString());
    }

    @Test
    void toStringReturnsCorrectStringForWeaponWithLimitedUses() {
        String name = "Staff";
        Move m = TestUtils.createTestToxicBoltMove();
        Weapon w = new Weapon(name, 2, m);

        assertEquals(name + " (" + m.getUses() + ")", w.toString());
    }

    @Test
    void getToolTipTextContainsCorrectInformation() {
        Weapon w = TestUtils.createTestWoodClub();
        String text = w.getToolTipText();

        assertTrue(text.contains(w.getMove().getToolTipText()));
        assertTrue(text.contains(w.getName()));
        assertTrue(text.contains(String.valueOf(w.getTier())));
    }
}
