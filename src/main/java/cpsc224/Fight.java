package cpsc224;

import java.util.Random;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureAI;
import cpsc224.creatures.Player;
import cpsc224.effects.Effect;
import cpsc224.items.weapons.Weapon;
import cpsc224.items.weapons.moves.Move;

/**
 * A class to represent a fight between two creatures.
 */
public class Fight {

    private Player player;
    private Creature enemy;
    private boolean isPlayersTurn;
    private Random rand;
    
    /**
     * Creates a fight with a player, enemy, and seed.
     * @param player the player
     * @param enemy the enemy
     */
    public Fight(Player player, Creature enemy) {
        this.player = player;
        this.enemy = enemy;
        rand = new Random();
    }

    /**
     * Perform a move in the fight.
     * @param source the creature performing the move
     * @param target the creature being targeted
     * @param weapon the weapon being used
     * @return the result of the move as a string
     */
    public String performMove(Creature source, Creature target, Weapon weapon) {
        Move move = weapon.getMove();

        if (move.getUses() > 0)
            move.decrementUses();
        
        // attack has a chance to miss if the move is not self-targeting
        if (!source.equals(target)) {
            double hitChance = (1D - target.getTurnModifiers().getEvasion()) * weapon.getMove().getAccuracy();

            if (rand.nextDouble(1D) > hitChance)
                return source.getName() + " used " + move.getName() + " with their " + weapon.getName() + " against " + target.getName() + ". They Missed!";
        }
        
        double damage = move.getDamage() * source.getTurnModifiers().getDamage();
        double damageDealt = target.applyDamage(damage, move.getDamageType());

        // result of move
        StringBuilder result = new StringBuilder();
        result.append(source.getName()).append(" used ")
                .append(move.getName()).append(" with their ")
                .append(weapon.getName()).append(" on ")
                .append(target.getName()).append(".");

        // display damage dealt if greater than zero
        if (damageDealt > 0)
            result.append(" They dealt ").append(damageDealt).append(" damage!");

        // apply each effect and get result
        result.append("<br>");
        for (Effect e : move.createEffects()) {
            result.append(target.addEffect(e)).append(". ");
        }
        
        return result.toString();
    }

    /**
     * Non-player creature performs move using first weapon in inventory.
     * @param creature the creature performing the move
     * @param enemy the creature being targeted.
     * @return the result of the move as a string.
     */
    public String creatureTurn(Creature creature, Creature enemy) {
        CreatureAI move = new CreatureAI(creature);
        int index = move.calculateMove();
        return performMove(creature, enemy, creature.getInventory().getWeapon(index));
    }
}
