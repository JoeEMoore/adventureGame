package cpsc224.levels.rooms.shop;

import cpsc224.TestUtils;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.pools.Pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestShopInitializer implements ShopInitializer {
    List<ShopEntry> weapons = new ArrayList<>();
    List<ShopEntry> consumables = new ArrayList<>();

    public TestShopInitializer() {
        weapons = Arrays.asList(new ShopEntry(TestUtils::createTestSword, 1, 10),
                new ShopEntry(TestUtils::createTestWoodClub, 2, 20));

        consumables = Arrays.asList(new ShopEntry(TestUtils::createTestPoisonPotion, 1, 10),
                new ShopEntry(TestUtils::createTestHealthPotion, 2, 20));
    }

    @Override
    public List<ShopEntry> generateWeaponEntries() {
        return weapons;
    }

    @Override
    public List<ShopEntry> generateConsumableEntries() {
        return consumables;
    }
}
