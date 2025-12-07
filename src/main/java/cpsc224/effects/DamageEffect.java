package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;
import cpsc224.utils.DoubleUtils;

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
        name = "Damage";
    }

    /**
     * Multiplies the damage amount.
     * @param multiplier the multiplier
     */
    @Override
    public void multiplyEffect(double multiplier) {
        damageAmount *= (int) multiplier;
    }

    @Override
    public String apply(Creature creature) {
        creature.applyDamage(damageAmount, DamageType.Pure);
        return creature.getName() + " was dealt " + DoubleUtils.roundDouble(damageAmount) + " damage.";
    }
}
