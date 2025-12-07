package cpsc224.levels.rooms.shop;

import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.Pool;

import java.util.List;

public abstract class ShopInitializer {
    protected final Pool<Weapon> weaponPool;
    protected final Pool<Consumable> consumablePool;

    public ShopInitializer(Pool<Weapon> weaponPool, Pool<Consumable> consumablePool) {
        this.weaponPool = weaponPool;
        this.consumablePool = consumablePool;
    }

    public abstract List<ShopEntry> generateWeaponEntries();

    public abstract List<ShopEntry> generateConsumableEntries();
}
