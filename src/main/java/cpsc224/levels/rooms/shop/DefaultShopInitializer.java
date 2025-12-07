package cpsc224.levels.rooms.shop;

import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.Pool;

import java.util.ArrayList;
import java.util.List;

public class DefaultShopInitializer extends ShopInitializer {

    public DefaultShopInitializer(Pool<Weapon> weaponPool, Pool<Consumable> consumablePool) {
        super(weaponPool, consumablePool);
    }

    @Override
    public List<ShopEntry> generateWeaponEntries(){
        List<ShopEntry> entries = new ArrayList<>();
       
        for (int i = 0; i < 3; i++) {
            Weapon w = weaponPool.createNew();
            entries.add(new ShopEntry(w, 1, w.getTier() * 40));
        }
        return entries;
    }

    @Override
    public List<ShopEntry> generateConsumableEntries() {
        List<ShopEntry> entries = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Consumable c = consumablePool.createNew();
            entries.add(new ShopEntry(c, 3, c.getTier() * 20));
        }
        return entries;
    }
}
