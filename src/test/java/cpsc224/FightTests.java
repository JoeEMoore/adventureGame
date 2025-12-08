package cpsc224;

import org.junit.jupiter.api.Test;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.creatures.Creature;
import cpsc224.items.weapons.Weapon;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FightTests {
    
    @Test
    public void PerformingMoves () {
        Player p = CreatureFactory.createPlayer();
        Creature rat = CreatureFactory.createRat();
        Weapon s = WeaponFactory.createSteelSword();
        Fight f = new Fight(p, rat);
        double initialHealth = rat.getHealth();
        String result = f.performMove(p, rat, s);
        double dmg = s.getMove().getDamage() * p.getTurnModifiers().getDamage();
        double afterHealth = rat.getHealth();

        assertTrue(result.contains(s.getName()));
        assertTrue(result.contains(rat.getName()));
        assertTrue(result.contains(p.getName()));
        rat.getBaseModifiers().setEvasion(0);
        assertTrue(afterHealth < initialHealth);

        assertEquals(s.getMove().createEffects().size(), rat.getEffects().size());
        
    }
    
    @Test
    public void creatureTurnReturnsResultAsString() {
        Creature rat = TestUtils.createTestRat();
        Player p = TestUtils.createTestPlayer();
        Fight f = new Fight(p, rat);
        String t1 = f.creatureTurn(rat, p);

        assertTrue(t1.contains(rat.getName()));
        assertTrue(t1.contains(rat.getInventory().getWeapon(0).getName()));
        assertTrue(t1.contains(p.getName()));

    }

    @Test
    public void createFights() {
        Player p = TestUtils.createTestPlayer();
        Creature rat = TestUtils.createTestRat();
        Fight f = new Fight(p, rat);
        String o = f.creatureTurn(p, rat);

        assertTrue(o.contains("Player"));
        assertTrue(o.contains("Rat"));
        assertTrue(o.contains(p.getInventory().getWeapon(0).getName()));
        
        

    }

}
