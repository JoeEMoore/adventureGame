package cpsc224.items.consumables;

import java.util.Collection;
import java.util.List;

import cpsc224.effects.Effect;

public class Consumable {
    
    private String name;

    private List<Effect> effects;

    public Consumable(String name, List<Effect> effects) {
        this.name = name;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }

    public Collection<Effect> getEffects() {
        return effects;
    }
}