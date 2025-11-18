package cpsc224.creatures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.items.Inventory;

public class Creature {
    private double health;
    private double maxHealth;
    private String name;
    private CreatureModifiers baseModifiers;
    private CreatureModifiers turnModifiers;
    private List<Effect> effects = new ArrayList<>(); 
    private Inventory inventory;

    public Creature(String creatureName, double maxHealth, CreatureModifiers baseModifiers, Inventory inventory) {
        this.name = creatureName;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseModifiers = baseModifiers;
        this.inventory = inventory;

        resetTurnModifiers();
    }

    public void addEffect(Effect effect) {
        if (effect.isAppliedInstantly())
            effect.applyEffect(this);

        if (effect.getTurns() <= 0)
            return;
        
        effects.add(effect);
    }

    public void clearEffects() {
        effects.clear();
    }

    public Collection<Effect> getEffects() {
        return effects;
    }

    public Collection<String> calculateEffects() {
        resetTurnModifiers();

        List<String> results = new ArrayList<>();
        for (int i = 0; i < effects.size(); i++) {
            Effect e = effects.get(i);
            results.add(e.applyEffect(this));

            if (e.getTurns() <= 0) {
                effects.remove(i);
                i--;
            }
        }
        return results;
    }

    public String getName(){
        return name;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public CreatureModifiers getBaseModifiers() {
        return baseModifiers;
    }

    public CreatureModifiers getTurnModifiers() {
        return turnModifiers;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public double addHealth(int amount) {
        double amountAdded = Math.min(maxHealth - health, amount);
        health = Math.min(maxHealth, health + amount);
        return amountAdded;
    }

    public double applyDamage(double damage, DamageType type) {
        double damageDealt = damage * turnModifiers.getResistance(type);

        if (health - damageDealt < 0)
            health = 0;
        else
            health -= damageDealt;

        return damageDealt;
    }

    public double applyPercentDamage(double percent, DamageType type) {
        return applyDamage(health * percent, type);
    }

    private void resetTurnModifiers(){
        turnModifiers = baseModifiers.clone();
    }
}
