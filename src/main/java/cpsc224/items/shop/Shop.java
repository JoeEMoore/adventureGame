package cpsc224.items.shop;

import cpsc224.items.Item;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<ShopEntry> weaponEntries;
    private List<ShopEntry> consumableEntries;

    public Shop(List<Item> weapnPool, List<Item> consumablePool){
        weaponEntries = new ArrayList<>();
        consumableEntries = new ArrayList<>();

    }

}
