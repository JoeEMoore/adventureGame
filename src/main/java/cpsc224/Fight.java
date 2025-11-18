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

    public String performAttack(Creature source, Creature target, Weapon weapon) {
        
        Move move = weapon.getMove();
        
        if (!source.equals(target)) {
            double hitChance = (1D - target.getTurnModifiers().getEvasion()) * weapon.getMove().getAccuracy();

            if (rand.nextDouble(1D) > hitChance)
                return source.getName() + " used " + move.getName() + " with their " + weapon.getName() + " against " + target.getName() + ". They Missed!";
        }
        
        double damage = move.getDamage() * source.getTurnModifiers().getDamage();
        double damageDealt = target.applyDamage(damage, move.getDamageType());
        
        for (Effect e : move.createEffects()) {
            target.addEffect(e);
        }

        String result = source.getName() + " used " + move.getName() + " with their " + weapon.getName() + " on " + target.getName() + ".";

        if (damageDealt > 0)
            result += " They dealt " + damageDealt + " damage!";
        
        return result;
    }

    public String creatureTurn(Creature creature, Creature enemy) {
        return performAttack(creature, enemy, creature.getInventory().getWeapon(0));
    }
}
