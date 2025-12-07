package cpsc224.levels.rooms.shop;

import cpsc224.levels.rooms.Room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShopRoom extends Room {

    private List<ShopEntry> weaponEntries = new ArrayList<>();
    private List<ShopEntry> consumableEntries = new ArrayList<>();

    public ShopRoom(ShopInitializer generator){
        weaponEntries = generator.generateWeaponEntries();
        consumableEntries = generator.generateConsumableEntries();
    }

    public Collection<ShopEntry> getConsumableEntries() {
        return consumableEntries;
    }

    public Collection<ShopEntry> getWeaponEntries() {
        return weaponEntries;
    }
}
