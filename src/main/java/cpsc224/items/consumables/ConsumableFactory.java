package cpsc224.items.consumables;

import java.util.Arrays;
import java.util.List;

import cpsc224.effects.Effect;
import cpsc224.effects.HealEffect;

public class ConsumableFactory {

    public static Consumable createSmallHealthPotion() {
        final String name = "Small Health Potion";
        final List<Effect> effects = Arrays.asList(new HealEffect(25));

        return new Consumable(name, effects);
    }
}
