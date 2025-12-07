package cpsc224.items.shop;

import java.util.ArrayList;
import java.util.List;


import cpsc224.items.ItemPool;

public class DefaultShopInitializer extends ShopInitializer {

    public DefaultShopInitializer(ItemPool weaponPool, ItemPool consumablePool) {
        super(weaponPool, consumablePool);
    }

    @Override
    public List<ShopEntry> generateWeaponEntries(){
        List<ShopEntry> entries = new ArrayList<>();
       
        for (int i = 0; i < 3; i++) {
            entries.add(new ShopEntry(weaponPool.getItem(), 1));
        }
        return entries;
    }

    @Override
    public List<ShopEntry> generateConsumableEntries(){
        List<ShopEntry> entries = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            entries.add(new ShopEntry(consumablePool.getItem(), 3));
        }
        return entries;
    }
}
