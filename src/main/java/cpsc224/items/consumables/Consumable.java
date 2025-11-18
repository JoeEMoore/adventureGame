package cpsc224.items.consumables;

import java.util.Collection;
import java.util.List;

import cpsc224.effects.Effect;
import cpsc224.effects.EffectsFactory;

/**
 * A class to represent limited-use items that can be used by creatures
 */
public class Consumable {
    
    private String name;

    private EffectsFactory effects;

    /**
     * Creates a consumabel with the specified name and effects factory.
     * @param name the name
     * @param effects the EffectsFactory functional interface
     */
    public Consumable(String name, EffectsFactory effects) {
        this.name = name;
        this.effects = effects;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Creates the effects to be applied.
     * @return a collection of the effects.
     */
    public Collection<Effect> createEffects() {
        return effects.createEffects();
    }
}