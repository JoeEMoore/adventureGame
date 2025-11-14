package cpsc224.creatures;

import cpsc224.DamageType;

import java.util.ArrayList;
import java.util.List;
import cpsc224.effects.Effect;

public abstract class Creature {
    private int health;
    private int maxHealth;
    private String name;
    private CreatureModifiers modifiers;
    private CreatureModifiers turnModifiers;
    private List<Effect> effects = new ArrayList<>(); 

    public Creature(CreatureModifiers modifiers, String creatureName, int maxHealth) {
        this.modifiers = modifiers;
        this.health = maxHealth;
        this.name = creatureName;
        this.maxHealth = maxHealth;

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

    public int getHealth() {
        return health;
    }

    public CreatureModifiers getModifiers() {
        return modifiers;
    }

    public CreatureModifiers getTurnModifiers() {
        return turnModifiers;
    }

    public void addHealth(int amount) {
        if(health + amount <= maxHealth)
            this.health += amount;
        else
            this.health = maxHealth;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
    }

    public void setTurnModifiers(double damage, double evasion, DamageType type, double resistance) {
        this.turnModifiers.addDamage(damage);
        this.turnModifiers.addEvasion(evasion);
        this.turnModifiers.addResistance(type, resistance); 
   }

    public void resetTurnModifiers(){
        this.turnModifiers = modifiers.clone();
    }
}
