package cpsc224.creatures;

import java.util.ArrayList;
import java.util.List;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.items.Inventory;

public class Creature {
    private double health;
    private double maxHealth;
    private String name;
    private CreatureModifiers modifiers;
    private CreatureModifiers turnModifiers;
    private List<Effect> effects = new ArrayList<>(); 
    private Inventory inventory;

    public Creature(String creatureName, double maxHealth, CreatureModifiers modifiers, Inventory inventory) {
        this.name = creatureName;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.modifiers = modifiers;
        this.inventory = inventory;

        resetTurnModifiers();
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    public void clearEffects() {
        effects.clear();
    }

    public void calculateEffects() {
        for (Effect e : effects) {
            e.applyEffect(this);

            if (e.getTurns() <= 0)
                effects.remove(e);
        }
    }

    public String getName(){
        return name;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public CreatureModifiers getModifiers() {
        return modifiers;
    }

    public CreatureModifiers getTurnModifiers() {
        return turnModifiers;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addHealth(int amount) {
        if(health + amount <= maxHealth)
            this.health += amount;
        else
            this.health = maxHealth;
    }

    public double applyDamage(double damage, DamageType type) {
        double damageDealt = damage * turnModifiers.getResistance(type);

        if (health - damageDealt < 0)
            health = 0;
        else
            health -= damageDealt;
            
        return damageDealt;
    }

    public void applyPercentDamage(double percent, DamageType type) {
        applyDamage(health * percent, type);
    }

    public void setTurnModifiers(double damage, double evasion, DamageType type, double resistance) {
        turnModifiers.addDamage(damage);
        turnModifiers.addEvasion(evasion);
        turnModifiers.addResistance(type, resistance); 
   }

    public void resetTurnModifiers(){
        turnModifiers = modifiers.clone();
    }
}
