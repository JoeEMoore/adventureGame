package cpsc224.levels.rooms.shop;

import cpsc224.items.Item;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.Pool;

import java.util.List;

public abstract class ShopInitializer {
    protected final Pool<Item> weaponPool;
    protected final Pool<Item> consumablePool;

    public ShopInitializer(Pool<Item> weaponPool, Pool<Item> consumablePool) {
        this.weaponPool = weaponPool;
        this.consumablePool = consumablePool;
    }

    public abstract List<ShopEntry> generateWeaponEntries();

    public abstract List<ShopEntry> generateConsumableEntries();
}
