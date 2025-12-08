package cpsc224.levels.rooms.shop;

import cpsc224.items.Item;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.ObjectCreator;
import cpsc224.pools.Pool;

import java.util.ArrayList;
import java.util.List;

public class DefaultShopInitializer implements ShopInitializer {

    private final Pool<Item> weaponPool;
    private final Pool<Item> consumablePool;

    public DefaultShopInitializer(Pool<Item> weaponPool, Pool<Item> consumablePool) {
        this.weaponPool = weaponPool;
        this.consumablePool = consumablePool;
    }

    @Override
    public List<ShopEntry> generateWeaponEntries(){
        List<ShopEntry> entries = new ArrayList<>();
       
        for (int i = 0; i < 3; i++) {
            ObjectCreator<Item> creator = weaponPool.getCreator();
            entries.add(new ShopEntry(creator, 1, creator.createItem().getTier() * 60));
        }
        return entries;
    }

    @Override
    public List<ShopEntry> generateConsumableEntries() {
        List<ShopEntry> entries = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ObjectCreator<Item> creator = consumablePool.getCreator();
            entries.add(new ShopEntry(creator, 3, creator.createItem().getTier() * 20));
        }
        return entries;
    }
}