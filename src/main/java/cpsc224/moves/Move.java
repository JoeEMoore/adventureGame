package cpsc224.moves;

import java.util.Collection;
import java.util.List;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;

public class Move {

    private final String name;
    private final double damage;
    private final DamageType damageType;
    private final int maxUses;
    private int uses;
    private final double accuracy;
    private final boolean canTargetAllies;
    private final boolean canTargetEnemies;
    private final List<Effect> effects;

    public Move(String name, double damage, DamageType damageType, int maxUses, double accuracy, boolean canTargetAllies,
            boolean canTargetEnemies, List<Effect> effects) {

        this.name = name;
        this.damage = damage;
        this.damageType = damageType;
        this.maxUses = maxUses;
        this.accuracy = accuracy;
        this.canTargetAllies = canTargetAllies;
        this.canTargetEnemies = canTargetEnemies;
        this.effects = effects;

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
