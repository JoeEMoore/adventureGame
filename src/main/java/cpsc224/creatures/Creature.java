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
        effects.add(effect);
    }

    public void clearEffects() {
        effects.clear();
    }

    public Collection<Effect> getEffects() {
        return effects;
    }

    public Collection<String> calculateEffects() {
        turnModifiers = baseModifiers.clone();

        List<String> results = new ArrayList<>();
        for (int i = 0; i < effects.size(); i++) {
            Effect e = effects.get(i);
            e.applyEffect(this);

            if (e.getTurns() <= 0) {
                effects.remove(i);
                i--;
            }

            results.add(e.getName());
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
        turnModifiers = baseModifiers.clone();
    }
}
