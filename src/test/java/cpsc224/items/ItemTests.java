package cpsc224.items;

import cpsc224.TestUtils;
import cpsc224.items.weapons.Weapon;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ItemTests {

    @Test
    void createItemCreatesCorrectItem() {
        String name = "Sword";
        int tier = 2;
        Item i = new Weapon(name, tier, TestUtils.createTestSlashMove());
        assertEquals(name, i.getName());
        assertEquals(tier, i.getTier());
    }

    @Test
    void getIconReturnsCorrectIcon() {
        ImageIcon icon = new ImageIcon();
        Item i = new Weapon("sword", 1, TestUtils.createTestSlashMove(), icon);

        assertEquals(icon, i.getIcon());
    }

    @Test
    void toStringReturnsName() {
        Item i = TestUtils.createTestPoisonPotion();
        assertEquals(i.getName(), i.toString());
    }

    @Test
    void setNameChangesName() {
        String newName = "newName";
        Item i = TestUtils.createTestHealthPotion();
        assertNotEquals(newName, i.getName());

        i.setName(newName);
        assertEquals(newName, i.getName());
    }

    @Test
    void setTierChangesTier() {
        int newTier = 3;
        Item i = TestUtils.createTestSword();
        assertNotEquals(newTier, i.getTier());

        i.setTier(newTier);
        assertEquals(newTier, i.getTier());
    }

    @Test
    void mapTierToMultiplierMapsToCorrectMultiplier() {
        assertEquals(1.0, Item.mapTierToMultiplier(1));
        assertEquals(1.5, Item.mapTierToMultiplier(2));
        assertEquals(2.0, Item.mapTierToMultiplier(3));
    }
}
