package cpsc224.items.consumables;

import cpsc224.creatures.Creature;
import cpsc224.effects.Effect;
import cpsc224.effects.EffectsFactory;
import cpsc224.items.Item;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class to represent limited-use items that can be used by creatures
 */
public class Consumable extends Item {
    
    private EffectsFactory effects;
    private boolean affectsSelf;

    /**
     * Creates a consumable with the specified name and effects factory.
     * @param name the name
     * @param effects the EffectsFactory functional interface
     */
    public Consumable(String name, int tier, EffectsFactory effects, boolean affectsSelf, ImageIcon icon) {
        super(name, tier, icon);
        this.effects = effects;
        this.affectsSelf = affectsSelf;
    }

    /**
     * Creates a consumable with the specified name and effects factory.
     * @param name the name
     * @param effects the EffectsFactory functional interface
     */
    public Consumable(String name, int tier, EffectsFactory effects, boolean affectsSelf) {
        this(name, tier, effects, affectsSelf, null);
    }

    /**
     * Get if the consumable targets the creature using it.
     * @return true if it targets self
     */
    public boolean getAffectsSelf(){
        return affectsSelf;
    }

    /**
     * Applies effects to a creature.
     * @param creature the creature
     * @return the result of applying the effect as a String
     */
    public String applyEffects(Creature creature) {
        List<String> messages = new ArrayList<>();
        for (Effect e : effects.createEffects()) {
            //e.multiplyEffect(mapTierToMultiplier(getTier()));
            messages.add(creature.addEffect(e));
        }

        return String.join(", ", messages);
    }

    /**
     * Delegate method to create the effects of its effects
     * @return a collection of effects
     */
    public Collection<Effect> createEffects() {
        return effects.createEffects();
    }

    @Override
    public String getToolTipText() {
        StringBuilder text = new StringBuilder("<b>" + getName() + "</b><br>");
        text.append("Tier: ").append(tier);

        // get effect info
        Collection<Effect> effectCollection = effects.createEffects();
        if (!effectCollection.isEmpty()) {
            text.append("<br><br>Effects: ");
            for (Effect e : effectCollection) {
                text.append("<br>&emsp;").append(e.toString());
            }
        }

        return text.toString();
    };
}