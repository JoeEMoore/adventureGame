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
    public void PerformingMoves (){
        Player p = CreatureFactory.createPlayer();
        Creature rat = CreatureFactory.createRat();
        Weapon s = WeaponFactory.createSteelSword();
        Fight f = new Fight(p, rat);
        double intitialRhealth = rat.getHealth();
        String t1 = f.performMove(p,rat,s);
        double dmg = s.getMove().getDamage() * p.getTurnModifiers().getDamage();
        rat.applyDamage(dmg, s.getMove().getDamageType());
        double afterRhealth = rat.getHealth();

        assertTrue(t1.contains(s.getName()));
        assertTrue(t1.contains(rat.getName()));
        assertTrue(t1.contains(p.getInventory().getWeapon(0).getName()));
        assertTrue(t1.contains(p.getName()));
        rat.getBaseModifiers().setEvasion(0);
        assertTrue(afterRhealth < intitialRhealth);

        assertEquals(s.getMove().createEffects().size(), rat.getEffects().size());
        
    }
    
    @Test
    public void createsTurns () {
        Player p = CreatureFactory.createPlayer();
        Creature rat = CreatureFactory.createRat();
        Fight f = new Fight(p, rat);
        String t1 = f.creatureTurn(p, rat);

        assertTrue(t1.contains("Dull Sword"));
        assertTrue(t1.contains("Rat"));
        assertTrue(t1.contains(p.getInventory().getWeapon(0).getName()));
        assertTrue(t1.contains("Player"));

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
