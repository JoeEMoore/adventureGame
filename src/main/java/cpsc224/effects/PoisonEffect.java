package cpsc224.effects;

import cpsc224.creatures.Creature;

public class PoisonEffect extends Effect {

    public static final double PERCENT_DAMAGE = 0.05;

    public PoisonEffect(int turns) {
        super(turns);
    }

    @Override
    protected void apply(Creature creature) {
        // TODO
    }

}
