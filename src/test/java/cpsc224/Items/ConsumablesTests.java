package cpsc224.Items;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import cpsc224.effects.Effect;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.consumables.ConsumableFactory;

public class ConsumablesTests {
    
    @Test
    public void GettingNameAndEffects () {
        Consumable smallHealthPotion = ConsumableFactory.createSmallHealthPotion();
        Collection<Effect> effects = smallHealthPotion.createEffects();

        assertEquals(smallHealthPotion.getName(), "Small Health Potion");
        assertEquals(effects.size(), 1);


    }
}
