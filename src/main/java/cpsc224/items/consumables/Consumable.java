package cpsc224.items.consumables;

import java.util.Collection;
import java.util.List;

import cpsc224.effects.Effect;

public class Consumable {
    
    private List<Effect> effects;

    public Collection<Effect> getEffects() {
        return effects;
    }
}