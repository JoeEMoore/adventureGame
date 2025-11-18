package cpsc224.items.consumables;

import java.util.Arrays;

import cpsc224.effects.EffectsFactory;
import cpsc224.effects.HealEffect;

/**
 * A factory class to create consumables.
 */
public class ConsumableFactory {

    public static Consumable createSmallHealthPotion() {
        final String name = "Small Health Potion";
        final EffectsFactory effects = () -> {
            return Arrays.asList(new HealEffect(25));
        };

        return new Consumable(name, effects);
    }
}
