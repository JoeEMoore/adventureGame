package cpsc224.items.consumables;
import java.util.*;
public class ConsumablePool {
 private final List<Consumable> allConsumables;

    public ConsumablePool() {
        allConsumables = List.of(
            ConsumableFactory.createSmallHealthPotion(),
            ConsumableFactory.createMediumHealthPotion(),
            ConsumableFactory.createLargeHealthPotion(),

            ConsumableFactory.createSmallPoisonPotion(),
            ConsumableFactory.createMediumPoisonPotion(),
            ConsumableFactory.createLargePoisonPotion(),

            ConsumableFactory.createSmallDamagePotion(),
            ConsumableFactory.createMediumDamagePotion(),
            ConsumableFactory.createLargeDamagePotion()
        );
    }

    public List<Consumable> getAllConsumables() {
        return allConsumables;
    }

    public List<Consumable> getRandomConsumables(int count) {
        List<Consumable> copy = new ArrayList<>(allConsumables);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }
}
