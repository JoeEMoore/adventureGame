package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.utils.DoubleUtils;

public class StrengthEffect extends Effect {

    private double damageMultiplier;

    public StrengthEffect(int turns, double damageMultiplier) {
        super(turns, true);
        this.damageMultiplier = damageMultiplier;
        name = "Strength";
    }

    @Override
    public void multiplyEffect(double multiplier) {
        damageMultiplier *= multiplier;
    }

    @Override
    protected String apply(Creature creature) {
        creature.getTurnModifiers().multiplyDamage(damageMultiplier);
        return creature.getName() + " is feeling " + DoubleUtils.roundDouble(damageMultiplier) + "x stronger";
    }

}
