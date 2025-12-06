package cpsc224.effects;

import cpsc224.creatures.Creature;
import cpsc224.damagetypes.DamageType;
import cpsc224.damagetypes.DamageTypeUtils;
import cpsc224.utils.DoubleUtils;

public class ResistanceEffect extends Effect {

    double resistanceMultiplier;

    /**
     * Applies a multiplier to the resistances of a creature for each damage type
     * @param turns the number of turns
     * @param resistanceMultiplier the multiplier (less than 1 means more resistant)
     */
    public ResistanceEffect(int turns, double resistanceMultiplier) {
        super(turns);
        this.resistanceMultiplier = resistanceMultiplier;
        isAppliedInstantly = true;
        name = "Damage Resistance";
    }

    @Override
    public void multiplyEffect(double multiplier) {
        resistanceMultiplier *= multiplier;
    }

    @Override
    protected String apply(Creature creature) {
        for (DamageType dt : DamageTypeUtils.getDamageTypes())
            creature.getTurnModifiers().multiplyResistance(dt, resistanceMultiplier);

        return creature.getName() + " is " + DoubleUtils.roundDouble(1 / resistanceMultiplier) + "x as resilient.";
    }
}
