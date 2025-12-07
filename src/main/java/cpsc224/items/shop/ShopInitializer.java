package cpsc224.items.shop;

import cpsc224.items.ItemPool;

import java.util.List;

public abstract class ShopInitializer {
    protected final ItemPool weaponPool;
    protected final ItemPool consumablePool;

    public ShopInitializer(ItemPool weaponPool, ItemPool consumablePool) {
        this.weaponPool = weaponPool;
        this.consumablePool = consumablePool;
    }

    public abstract List<ShopEntry> generateWeaponEntries();

    public abstract List<ShopEntry> generateConsumableEntries();
}
