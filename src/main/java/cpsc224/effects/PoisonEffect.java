package cpsc224.effects;

import java.text.DecimalFormat;

import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;
import cpsc224.utils.DoubleUtils;

/**
 * An effect that deals percent damage to a creature.
 */
public class PoisonEffect extends Effect {

    public static final double PERCENT_DAMAGE = 0.05;

    /**
     * Creates a poison effect with a number of turns.
     * @param turns the number of turns
     */
    public PoisonEffect(int turns) {
        super(turns);
        name = "Poison";
    }

    /**
     * Multiplies the number of turns of the effect
     * @param multiplier the multiplier
     */
    @Override
    public void multiplyEffect(double multiplier) {
        turns = (int)Math.floor(turns * multiplier);
    }

    @Override
    public String apply(Creature creature) {
        double damage = creature.applyPercentDamage(PERCENT_DAMAGE, DamageType.Pure);
        return creature.getName() + " was poisoned for " + DoubleUtils.roundDouble(damage) + " damage";
    }
}
