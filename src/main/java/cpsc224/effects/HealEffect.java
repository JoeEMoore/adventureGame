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
    public void apply(Creature creature) {
        creature.addHealth(healAmount);
    }

}
