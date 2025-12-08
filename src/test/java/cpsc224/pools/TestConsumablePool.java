package cpsc224.pools;

import cpsc224.items.consumables.Consumable;

public class TestConsumablePool extends Pool<Consumable> {

    Consumable c;

    public TestConsumablePool(Consumable c) {
        this.c = c;
    }

    @Override
    public Consumable createNew() {
        return c;
    }
}
