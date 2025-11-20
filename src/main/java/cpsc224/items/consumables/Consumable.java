package cpsc224.items.consumables;

import cpsc224.creatures.Creature;
import cpsc224.effects.Effect;
import cpsc224.effects.EffectsFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public String toString() {
        return name;
    }

    /**
     * Applies effects to a creature.
     * @param creature the creature
     */
    public void applyEffects(Creature creature) {
        List<String> results = new ArrayList<>();
        for (Effect e : effects.createEffects())
                creature.addEffect(e);
    }
}