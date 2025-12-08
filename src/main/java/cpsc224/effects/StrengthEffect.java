package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.utils.DoubleUtils;

/**
 * An Effect that multiplies the damage stat of a Creature.
 */
public class StrengthEffect extends Effect {

    private double damageMultiplier;

    /**
     * Creates a new StrengthEffect.
     * @param turns the number of turns
     * @param damageMultiplier the damage multiplier
     */
    public StrengthEffect(int turns, double damageMultiplier) {
        super(turns, true);
        this.damageMultiplier = damageMultiplier;
        name = "Strength";
    }

    /**
     * Gets the damage multiplier.
     * @return the damage multiplier
     */
    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    public void multiplyEffect(double multiplier) {
        damageMultiplier *= multiplier;
    }

    @Override
    protected String apply(Creature creature) {
        creature.getTurnModifiers().multiplyDamage(damageMultiplier);
        return creature.getName() + " is feeling " + DoubleUtils.roundDouble(damageMultiplier) + "x stronger";
    }

}
