package cpsc224.items.weapons.moves;

import java.util.ArrayList;
import java.util.Collection;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.effects.EffectsFactory;
import cpsc224.items.Item;


/**
 * A class that represents the moves a creature can perform in a fight.
 */
public class Move {

    private final String name;
    private final double damage;
    private final DamageType damageType;
    private final int maxUses;
    private int uses;
    private final double accuracy;
    private final boolean targetsAllies;
    private int tier;

    private EffectsFactory effectsFactory;

    /**
     * Creates a new move.
     * @param name the name
     * @param damage the amount of damage
     * @param damageType the damage type
     * @param maxUses the max number of uses or -1 if unlimited
     * @param accuracy the base chance to hit from 0 to 1.0
     * @param targetAllies true if the move should target allies rather than enemies
     */
    public Move(String name, double damage, DamageType damageType, int maxUses, double accuracy, boolean targetAllies) {

        this.name = name;
        this.damage = damage;
        this.damageType = damageType;
        this.maxUses = maxUses;
        this.accuracy = accuracy;
        this.targetsAllies = targetAllies;
        
        effectsFactory = ArrayList::new;
        uses = maxUses;
        tier = 1;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the damage.
     * @return the damage
     */
    public double getDamage() {
        return damage * Item.mapTierToMultiplier(tier);
    }
    
    /**
     * Gets the damage type.
     * @return the damage type
     */
    public DamageType getDamageType() {
        return damageType;
    }

    /**
     * Gets the max uses.
     * @return the max uses
     */
    public int getMaxUses() {
        return maxUses;
    }

    /**
     * Gets the current uses.
     * @return the current uses
     */
    public int getUses() {
        return uses;
    }

    /**
     * Decreases the number of current uses by one.
     */
    public void decrementUses() {
        if (uses > 0)
            uses--;
    }

    /**
     * Resets the current number of uses to max uses.
     */
    public void resetUses() {
        uses = maxUses;
    }

    /**
     * Gets the accuracy.
     * @return the accuracy
     */
    public double getAccuracy() {
        return accuracy;
    }


    /**
     * Predicate for if the move targets allies.
     * @return true if the move targets allies
     */
    public boolean targetsAllies() {
        return targetsAllies;
    }

    /**
     * Gets the tier.
     * @return the tier
     */
    public int getTier() {
        return tier;
    }

    /**
     * Sets the tier.
     * @param tier the tier
     */
    public void setTier(int tier) {
        this.tier = tier;
    }

    /**
     * Sets the EffectsFactory for the move.
     * @param effectsFactory the EffectsFactory
     */
    public void setEffects(EffectsFactory effectsFactory) {
        this.effectsFactory = effectsFactory;
    }

    /**
     * Creates effects from the EffectsFactory.
     * @return the effects as a collection
     */
    public Collection<Effect> createEffects() {
        Collection<Effect> effects = effectsFactory.createEffects();
        for (Effect e : effects)
            e.multiplyEffect(Item.mapTierToMultiplier(tier));

        return effects;
    }

    public String getToolTipText() {
        String text = "<b>Move: " + getName() + "</b><br>";
        text += "Damage Type: " + getDamageType().toString() + "<br>";
        text += "Damage: " + getDamage() + " (" + Item.mapTierToMultiplier(tier) + "x) <br>";
        text += "Accuracy: " + getAccuracy() * 100 + "% <br>";

        // shows uses if there are limited uses
        if (getMaxUses() > 0)
            text += "Uses: " + getUses() + "/" + getMaxUses() + "<br>";

        text += "Targets Self: " + targetsAllies();

        // Effects info
        Collection<Effect> effects = createEffects();
        if (!effects.isEmpty()) {
            text += "<br><br>Effects: (" + Item.mapTierToMultiplier(tier) + "x)" ;
            for (Effect e : effects) {
                text += "<br>&emsp;" + e.toString();
            }
        }

        return text;
    }
}
