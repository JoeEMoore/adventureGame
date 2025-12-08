package cpsc224.levels.rooms.shop;

import cpsc224.items.Item;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.Pool;

import java.util.List;

public interface ShopInitializer {

    List<ShopEntry> generateWeaponEntries();

    List<ShopEntry> generateConsumableEntries();
}
