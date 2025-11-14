package cpsc224.effects;

import cpsc224.creatures.Creature;

public class HealEffect extends InstantEffect {

    private int healAmount;

    public HealEffect(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    protected void apply(Creature creature) {
        // TODO
    }

}
