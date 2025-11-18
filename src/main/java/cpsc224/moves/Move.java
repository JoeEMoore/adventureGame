package cpsc224.moves;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.effects.EffectFactory;

public class Move {

    private final String name;
    private final double damage;
    private final DamageType damageType;
    private final int maxUses;
    private int uses;
    private final double accuracy;
    private final boolean targetsAllies;

    private EffectFactory effectsFactory;

    public Move(String name, double damage, DamageType damageType, int maxUses, double accuracy, boolean targetAllies) {

        this.name = name;
        this.damage = damage;
        this.damageType = damageType;
        this.maxUses = maxUses;
        this.accuracy = accuracy;
        this.targetsAllies = targetAllies;
        
        effectsFactory = () -> {return new ArrayList<>();};
        uses = maxUses;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }
    
    public DamageType getDamageType() {
        return damageType;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public int getUses() {
        return uses;
    }

    public void decrementUses() {
        uses--;
    }

    public void resetUses() {
        uses = maxUses;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public boolean targetsAllies() {
        return targetsAllies;
    }

    public void setEffects(EffectFactory effectsFactory) {
        this.effectsFactory = effectsFactory;
    }

    public Collection<Effect> createEffects() {
        return effectsFactory.createEffects();
    }
}
