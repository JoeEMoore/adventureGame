package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.utils.DoubleUtils;

/**
 * An effect that heals creatures. It is instantly applied.
 */
public class HealEffect extends Effect {

    private int healAmount;

    /**
     * Creates a one turn heal effect with a heal amount.
     * @param healAmount the amount to heal
     */
    public HealEffect(int healAmount) {
        this(healAmount, 1);
    }

    /**
     * Multiplies the heal amount.
     * @param multiplier the multiplier
     */
    @Override
    public void multiplyEffect(double multiplier) {
        healAmount *= (int) multiplier;
    }

    /**
     * Creates a heal effect with a heal amount and number of turns.
     * @param healAmount the amount to heal
     * @param turns the number of turns
     */
    public HealEffect(int healAmount, int turns) {
        super(turns, true);
        this.healAmount = healAmount;
        name = "Heal";
    }

    @Override
    public String apply(Creature creature) {
        double health = creature.addHealth(healAmount);
        return creature.getName() + " gained " + DoubleUtils.roundDouble(health) + " health";
    }
}
