package cpsc224.pools;

import cpsc224.items.Item;
import cpsc224.items.weapons.Weapon;

public class TestWeaponPool extends Pool<Item> {

    public TestWeaponPool(ObjectCreator<Item> creator) {
        addObjectCreator(creator, 1);
    }
}
