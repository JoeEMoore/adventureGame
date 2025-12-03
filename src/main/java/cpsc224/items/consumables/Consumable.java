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
    private boolean affectsPlayer;

    /**
     * Creates a consumabel with the specified name and effects factory.
     * @param name the name
     * @param effects the EffectsFactory functional interface
     */
    public Consumable(String name, int tier, EffectsFactory effects, boolean affectsPlayer) {
        super(name, tier);
        this.effects = effects;
        this.affectsPlayer = affectsPlayer;
    }

    public boolean getAffectsPlayer(){
        return affectsPlayer;
    }

    /**
     * Applies effects to a creature.
     * @param creature the creature
     */
    public void applyEffects(Creature creature) {
        for (Effect e : effects.createEffects())
                creature.addEffect(e);
    }
}