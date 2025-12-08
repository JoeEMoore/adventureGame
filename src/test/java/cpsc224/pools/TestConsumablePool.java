package cpsc224.pools;

import cpsc224.items.Item;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.consumables.ConsumableFactory;

public class TestConsumablePool extends Pool<Item> {

    public TestConsumablePool(ObjectCreator<Item> creator) {
        addObjectCreator(creator, 1);
    }

}
