package cpsc224.effects;

import cpsc224.creatures.Creature;

public class HealEffect extends InstantEffect {

    private int healAmount;

    public HealEffect(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public void apply(Creature creature) {
        creature.addHealth(healAmount);
    }

}
