package cpsc224.effects;

import cpsc224.creatures.Creature;

public class HealEffect extends Effect {

    private int healAmount;

    public HealEffect(int healAmount) {
        this(healAmount, 1);
    }

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
