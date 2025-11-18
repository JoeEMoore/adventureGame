package cpsc224.effects;

import java.text.DecimalFormat;

import cpsc224.creatures.Creature;

public abstract class Effect {

    protected int turns;
    protected String name;
    protected boolean isAppliedInstantly;

    public Effect(int turns) {
        this.turns = turns;
        isAppliedInstantly = false;
    }

    public Effect(int turns, boolean isAppliedInstantly) {
        this.turns = turns;
        this.isAppliedInstantly = isAppliedInstantly;
    }

    public int getTurns() {
        return turns;
    }

    public String getName() {
        return name;
    }

    public String applyEffect(Creature creature) {
        turns--;
        return apply(creature);
    }

    public boolean isAppliedInstantly() {
        return isAppliedInstantly;
    }

    public String toString() {
        return name + " (" + turns + ")";
    }

    protected static String roundDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(d);
    }

    protected abstract String apply(Creature creature);
}
