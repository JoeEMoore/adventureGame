package cpsc224.items.weapons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cpsc224.TestUtils;
import org.junit.jupiter.api.Test;

import cpsc224.items.weapons.moves.Move;
import cpsc224.items.weapons.moves.MoveFactory;

public class WeaponsTests {
    
    @Test
    public void GettingAllAttributes () {
        Move slash = TestUtils.createTestSlashMove();
        Move smash = TestUtils.createTestSmashMove();

        Weapon sword = new Weapon("sword", 1, slash);
        Weapon Club = new Weapon("Club", 2, smash);
        
        assertEquals(sword.getName(), "sword");
        assertEquals(sword.getTier(), 1);
        assertEquals(sword.getMove(), slash);

        assertEquals(Club.getName(), "Club");
        assertEquals(Club.getTier(), 2);
        assertEquals(Club.getMove(), smash);

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
