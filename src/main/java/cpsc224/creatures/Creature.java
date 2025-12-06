package cpsc224.creatures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.items.Inventory;

import javax.swing.*;

/**
 * A class that represents creatures within the game.
 */

public class Creature {
    private double health;
    private double maxHealth;
    private String name;
    private CreatureModifiers baseModifiers;
    private CreatureModifiers turnModifiers;
    private List<Effect> effects = new ArrayList<>();
    private Inventory inventory;
    private ImageIcon icon;
    private List<Double> weaponWeights;

    /**
     * Instantiates a creature.
     * @param creatureName the name
     * @param maxHealth the max health
     * @param baseModifiers the base modifiers
     * @param inventory the inventory
     */
    public Creature(String creatureName, double maxHealth, CreatureModifiers baseModifiers, Inventory inventory, List<Double> weaponWeights) {
        this(creatureName, maxHealth, baseModifiers, inventory, null, weaponWeights);
    }

    /**
     * Instantiates a creature.
     * @param creatureName the name
     * @param maxHealth the max health
     * @param baseModifiers the base modifiers
     * @param inventory the inventory
     * @param icon the ImageIcon
     */
    public Creature(String creatureName, double maxHealth, CreatureModifiers baseModifiers, Inventory inventory, ImageIcon icon) {
        this.name = creatureName;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseModifiers = baseModifiers;
        this.inventory = inventory;
        this.icon = icon;
        weaponWeights = new ArrayList<>();

        resetTurnModifiers();
    }

    /**
     * Instantiates a creature.
     * @param creatureName the name
     * @param maxHealth the max health
     * @param baseModifiers the base modifiers
     * @param inventory the inventory
     * @param icon the ImageIcon
     */
    public Creature(String creatureName, double maxHealth, CreatureModifiers baseModifiers, Inventory inventory, ImageIcon icon, List<Double> weaponWeights) {
        this.name = creatureName;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseModifiers = baseModifiers;
        this.inventory = inventory;
        this.icon = icon;
        this.weaponWeights = weaponWeights;

        resetTurnModifiers();
    }

    /**
     * Adds an effect to the creature. The effect is
     * only applied if it is an instantly applied effect.
     * @param effect the effect
     * @return the result of the effect as a string
     */
    public String addEffect(Effect effect) {
        String result = null;

        // apply effect if it is applied instantly and set result accordingly
        if (effect.isAppliedInstantly()) {
            result = effect.applyEffect(this);
        } else {
            result = "Added " + effect.toString() + " to " + name;
        }

        // don't add the effect if it is out of turns
        if (effect.getTurns() <= 0)
            return result;

        effects.add(effect);
        return result;
    }

    /**
     * Clears all effects from the creature.
     */
    public void clearEffects() {
        effects.clear();
    }

    /**
     * Gets all effects from the creature.
     * @return the effects as a collection
     */
    public Collection<Effect> getEffects() {
        return effects;
    }

    /**
     * Applies all effects to the creature for the turn. 
     * The creature's turn modifiers are reset to the base modifiers.
     * Effects that run out of turns are removed.
     * @return the result as a String
     */
    public String calculateEffects() {
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
        return String.join(", ", results);
    }

    /**
     * Gets the name of the creature.
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the current health of the creature.
     * @return the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Sets the current health of the creature.
     * @param health the new health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets the max health of the creature.
     * @return the max health
     */
    public double getMaxHealth() {
        return maxHealth;
    }

    /**
     * Gets the base modifiers of the creature.
     * @return the base modifiers
     */
    public CreatureModifiers getBaseModifiers() {
        return baseModifiers;
    }

    /**
     * Gets the turn modifiers of the creature.
     * @return the turn modifiers
     */
    public CreatureModifiers getTurnModifiers() {
        return turnModifiers;
    }

    /**
     * Gets the inventory of the creature.
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public List<Double> getWeaponWeights(){
        return weaponWeights;
    }

    public void setWeaponWeights(List<Double> weights){
        weaponWeights = weights;
    }

    /**
     * Adds health to the creature up to the max health.
     * @param amount the amount of health to add
     * @return the amount of health added
     */
    public double addHealth(int amount) {
        double amountAdded = Math.min(maxHealth - health, amount);
        health = Math.min(maxHealth, health + amount);
        return amountAdded;
    }

    /**
     * Applies damage to the creature. Takes into account damage type resistances.
     * @param damage the amount of damage
     * @param type the damage type
     * @return the amount of damage applied
     */
    public double applyDamage(double damage, DamageType type) {
        double damageDealt = damage * turnModifiers.getResistance(type);

        if (health - damageDealt < 0)
            health = 0;
        else
            health -= damageDealt;

        return damageDealt;
    }

    /**
     * Applies damage as a percent of the creature's max health.
     * @param percent the percent of the creature's health
     * @param type the damage type
     * @return the amount of damage applied
     */
    public double applyPercentDamage(double percent, DamageType type) {
        return applyDamage(maxHealth * percent, type);
    }

    /**
     * resets the turn modifiers to a copy of the base modifiers
     */
    private void resetTurnModifiers(){
        turnModifiers = baseModifiers.clone();
    }
}
