package cpsc224.effects;

import java.text.DecimalFormat;

import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;

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

    @Override
    public String apply(Creature creature) {
        double damage = creature.applyPercentDamage(PERCENT_DAMAGE, DamageType.Pure);
        return creature.getName() + " was poisoned for " + roundDouble(damage) + " damage";
    }
}
