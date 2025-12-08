package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.utils.DoubleUtils;

/**
 * An effect that heals creatures. It is instantly applied.
 */
public class HealEffect extends Effect {

    private double healAmount;

    /**
     * Creates a one turn heal effect with a heal amount.
     * @param healAmount the amount to heal
     */
    public HealEffect(double healAmount) {
        this(1, healAmount);
    }

    /**
     * Creates a heal effect with a heal amount and number of turns.
     * @param healAmount the amount to heal
     * @param turns the number of turns
     */
    public HealEffect(int turns, double healAmount) {
        super(turns, true);
        this.healAmount = healAmount;
        name = "Heal";
    }

    /**
     * Gets the heal amount.
     * @return the heal amount
     */
    public double getHealAmount() {
        return healAmount;
    }

    /**
     * Multiplies the heal amount.
     * @param multiplier the multiplier
     */
    @Override
    public void multiplyEffect(double multiplier) {
        healAmount *= multiplier;
    }


    @Override
    public String apply(Creature creature) {
        double health = creature.addHealth((int)healAmount);
        return creature.getName() + " gained " + DoubleUtils.roundDouble(health) + " health";
    }
}
