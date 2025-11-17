package cpsc224.effects;

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

    public void applyEffect(Creature creature) {
        apply(creature);
        turns--;
    }

    public boolean isAppliedInstantly() {
        return isAppliedInstantly;
    }

    protected abstract void apply(Creature creature);
}
