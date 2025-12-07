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
            entries.add(new ShopEntry(weaponPool.createNew(), 1));
        }
        return entries;
    }

    @Override
    public List<ShopEntry> generateConsumableEntries(){
        List<ShopEntry> entries = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            entries.add(new ShopEntry(consumablePool.createNew(), 3));
        }
        return entries;
    }
}
