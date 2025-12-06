package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;

/**
 * An effect that deals instant damage to a creature.
 */

public class DamageEffect extends Effect {

    private double damageAmount;

    /**
     * Creates an instant damage effect with a damage amount.
     * @param damageAmount the damage amount
     */
    public DamageEffect(int turns, double damageAmount) {
        super(turns, true);
        this.damageAmount = damageAmount;
        isAppliedInstantly = true;
    }

    @Override
    public String apply(Creature creature) {
        creature.applyDamage(damageAmount, DamageType.Pure);
        return creature.getName() + " was dealt " + (int)damageAmount + " damage.";
    }
}
