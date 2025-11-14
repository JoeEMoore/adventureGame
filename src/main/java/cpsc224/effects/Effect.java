package cpsc224.effects;

import cpsc224.creatures.Creature;

public abstract class Effect {

    protected int turns;    

    public Effect(int turns) {
        this.turns = turns;
    }

    public abstract void apply(Creature creature);
}
