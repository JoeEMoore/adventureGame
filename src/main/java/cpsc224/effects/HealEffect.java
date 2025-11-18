package cpsc224.effects;

import cpsc224.creatures.Creature;

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
        return creature.getName() + " was healed by " + roundDouble(health);
    }
}
