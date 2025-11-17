package cpsc224;

import java.util.Random;

import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.effects.Effect;
import cpsc224.items.weapons.Weapon;
import cpsc224.moves.Move;

public class Fight {

    private Player player;
    private Creature enemy;
    private boolean isPlayersTurn;
    private Random rand;
    
    public Fight(Player player, Creature enemy, long seed) {
        this.player = player;
        this.enemy = enemy;
        rand = new Random(seed);
    }

    public double performAttack(Creature source, Creature target, Weapon weapon) {
        double hitChance = (1D - target.getModifiers().getEvasion()) * weapon.getMove().getAccuracy();

        if (rand.nextDouble(1D) > hitChance)
            return 0;
        
        Move move = weapon.getMove();

        double damageDealt = target.applyDamage(move.getDamage(), move.getDamageType());
        
        for (Effect e : move.getEffects()) {
            target.addEffect(e);
        }

        return damageDealt;
    }

    public double creatureTurn(Creature creature, Creature enemy) {
        return performAttack(creature, enemy, creature.getInventory().getWeapon(0));
    }
}
