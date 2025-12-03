package cpsc224.items;
import cpsc224.items.weapons.*;
import cpsc224.items.consumables.*;

public interface ItemsPool {

    Weapon getWeapons();
    Consumable getConsumable();
}
