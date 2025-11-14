package cpsc224.moves;

import java.util.Collection;
import java.util.List;

import cpsc224.DamageType;
import cpsc224.effects.Effect;

public class Move {

    private final int maxUses;
    private int uses;
    private int damage;
    private DamageType damageType;
    private double accuracy;
    private boolean canTargetAllies;
    private boolean canTargetEnemies;
    private List<Effect> effects;

    public Move(int maxUses, int uses, int damage, DamageType damageType, double accuracy, boolean canTargetAllies,
            boolean canTargetEnemies, List<Effect> effects) {
        this.maxUses = maxUses;
        this.uses = uses;
        this.damage = damage;
        this.damageType = damageType;
        this.accuracy = accuracy;
        this.canTargetAllies = canTargetAllies;
        this.canTargetEnemies = canTargetEnemies;
        this.effects = effects;
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

    public int getDamage() {
        return damage;
    }
    
    public DamageType getDamageType() {
        return damageType;
    }


    public double getAccuracy() {
        return accuracy;
    }

    public boolean canTargetAllies() {
        return canTargetAllies;
    }

    public boolean canTargetEnemies() {
        return canTargetEnemies;
    }

    public Collection<Effect> getEffects() {
        return effects;
    }

}
