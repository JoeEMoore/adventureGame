package cpsc224.items.consumables;

import cpsc224.creatures.Creature;
import cpsc224.effects.Effect;
import cpsc224.effects.EffectsFactory;
import cpsc224.items.Item;

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
    public Consumable(String name, int tier, EffectsFactory effects, boolean affectsSelf) {
        super(name, tier);
        this.effects = effects;
        this.affectsSelf = affectsSelf;
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

    @Override
    public String getToolTipText() {
        StringBuilder text = new StringBuilder("<b>" + getName() + "</b><br>");

        // get effect info
        Collection<Effect> effectCollection = effects.createEffects();
        if (!effectCollection.isEmpty()) {
            text.append("Effects: ");
            for (Effect e : effectCollection) {
                text.append("<br>&emsp;").append(e.toString());
            }
        }

        return text.toString();
    };
}