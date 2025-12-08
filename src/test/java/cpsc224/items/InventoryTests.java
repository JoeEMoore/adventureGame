package cpsc224.items;

import cpsc224.TestUtils;
import org.junit.jupiter.api.Test;

import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTests {
    
    @Test 
    public void GettingMaxes() {
        Inventory inv = new Inventory(4, 4);

        assertEquals(inv.getMaxConsumables(), 4);
        assertNotEquals(inv.getMaxConsumables(), 0);

        assertEquals(inv.getMaxWeapons(), 4);
        assertNotEquals(inv.getMaxWeapons(), 0);

    }

    @Test
    public void IncrementMaxSpace() {
      Inventory inv = new Inventory(1, 1);
      
      assertEquals(inv.getMaxWeapons(), 1);
      assertEquals(inv.getMaxConsumables(), 1);

      inv.incrementMaxWeapons();
      inv.incrementMaxConsumables();
      assertEquals(inv.getMaxWeapons(), 2);
      assertEquals(inv.getMaxConsumables(), 2);
    
    }

    @Test
    public void gettingItems () {
        Inventory inv = new Inventory(4, 4);

        Weapon sword = new Weapon("sword", 1, null);
        Weapon bow = new Weapon("bow", 1, null);

        assertEquals(inv.getWeapons().size(), 0);
        
        inv.getWeapons().add(sword);
        inv.getWeapons().add(bow);

        assertEquals(inv.getWeapons().size(), 2);
        

        Consumable smallHealthPotion = new Consumable("small health potion", 1, null, true);
        Consumable BigHealthPotion = new Consumable("big health potion", 3, null, true);

        assertEquals(inv.getConsumables().size(), 0);

        inv.getConsumables().add(smallHealthPotion);
        inv.getConsumables().add(BigHealthPotion);

        assertEquals(inv.getConsumables().size(), 2);
    }

    @Test
    public void gettingItem () {
        Inventory inv = new Inventory(4, 4);

        Weapon sword = new Weapon("sword", 1, null);
        Weapon bow = new Weapon("bow", 1, null);
        
        inv.getWeapons().add(bow);
        inv.getWeapons().add(sword);

        assertEquals(inv.getWeapon(0), bow);
        assertEquals(inv.getWeapon(1), sword);
        assertEquals(inv.getWeapon(5), null);
        
        Consumable smallHealthPotion = new Consumable("small health potion", 1, null, true);
        Consumable BigHealthPotion = new Consumable("big health potion", 3, null, true);

        inv.getConsumables().add(smallHealthPotion);
        inv.getConsumables().add(BigHealthPotion);

        assertEquals(inv.getConsumable(0), smallHealthPotion);
        assertEquals(inv.getConsumable(1), BigHealthPotion);
        assertEquals(inv.getConsumable( 5), null);
    }
    
    @Test
    public void settingItems () {
        Inventory inv = new Inventory(4, 4);

        Weapon sword = new Weapon("sword", 1, null);
        Weapon mace = new Weapon("mace", 1, null);
        
        inv.getWeapons().add(null);
        inv.getWeapons().add(null);

        inv.setWeapon(0, sword);
        inv.setWeapon(1, mace);

        assertEquals(inv.getWeapon(0), sword);
        assertEquals(inv.getWeapon(1), mace);

        
        Consumable smallHealthPotion = new Consumable("small health potion", 1, null, true);
        Consumable BigHealthPotion = new Consumable("big health potion", 1, null, true);

        inv.getConsumables().add(null);
        inv.getConsumables().add(null);

        inv.setConsumable(0, smallHealthPotion);
        inv.setConsumable(1, BigHealthPotion);

        assertEquals(inv.getConsumable(0), smallHealthPotion);
        assertEquals(inv.getConsumable(1), BigHealthPotion);
     
    }

    @Test
    void setWeaponReturnsWeaponWhenSlotIsGreaterThanMaxWeapons() {
        Inventory inv = new Inventory(4, 4);
        Weapon w = TestUtils.createTestSword();
        assertEquals(w, inv.setWeapon(5, w));
    }

    @Test
    void setConsumableReturnsConsumableWhenSlotIsGreaterThanMaxConsumables() {
        Inventory inv = new Inventory(4, 4);
        Consumable c = TestUtils.createTestPoisonPotion();
        assertEquals(c, inv.setConsumable(5, c));
    }

    @Test
    void addItemAddsWeaponToInventoryWhenThereIsRoom() {
        Inventory inv = new Inventory(1, 1);
        Weapon w = TestUtils.createTestSword();

        assertTrue(inv.addItem(w));
        assertTrue(inv.getWeapons().contains(w));
    }

    @Test
    void addItemAddsConsumableToInventoryWhenThereIsRoom() {
        Inventory inv = new Inventory(1, 1);
        Consumable c = TestUtils.createTestPoisonPotion();

        assertTrue(inv.addItem(c));
        assertTrue(inv.getConsumables().contains(c));
    }

    @Test
    void addItemDoesNotAddWeaponIfThereIsNoRoom() {
        Inventory inv = new Inventory(1, 1);
        Weapon w = TestUtils.createTestSword();
        Weapon w2 = TestUtils.createTestSword();
        assertTrue(inv.addItem(w));

        assertFalse(inv.addItem(w2));
        assertFalse(inv.getWeapons().contains(w2));
    }

    @Test
    void addItemDoesNotAddConsumableIfThereIsNoRoom() {
        Inventory inv = new Inventory(1, 1);
        Consumable c1 = TestUtils.createTestPoisonPotion();
        Consumable c2 = TestUtils.createTestHealthPotion();
        assertTrue(inv.addItem(c1));

        assertFalse(inv.addItem(c2));
        assertFalse(inv.getConsumables().contains(c2));
    }
}

