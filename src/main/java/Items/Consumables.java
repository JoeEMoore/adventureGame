package Items;

import java.util.List;

import cpsc224.effects.Effect;

public class Consumables {
    
    private List<Effects> effects;


    public Consumable(List<Effect> effects) {
        this.effects = effects;
    }

    public List<Effect> getEffects() {
        return effects;
    }


}
