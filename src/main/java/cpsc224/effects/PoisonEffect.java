package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;

public class PoisonEffect extends Effect {

    public static final double PERCENT_DAMAGE = 0.05;

    public PoisonEffect(int turns) {
        super(turns);
        name = "Poison";
    }

    @Override
    protected void apply(Creature creature) {
        creature.applyPercentDamage(PERCENT_DAMAGE, DamageType.Pure); // Could create a poison type
    }

}
