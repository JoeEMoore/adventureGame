package cpsc224.effects;

import cpsc224.creatures.Creature;

public abstract class Effect {

    protected int turns;

    public Effect(int turns) {
        this.turns = turns;
    }

    public int getTurns() {
        return turns;
    }

    public void applyEffect(Creature creature) {
        apply(creature);
        turns--;
    }

    protected abstract void apply(Creature creature);

}
