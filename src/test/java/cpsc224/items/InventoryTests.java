package cpsc224.items;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;

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
        

        Consumable smallHealthPotion = new Consumable("small health potion", null);
        Consumable BigHealthPotion = new Consumable("big health potion", null);

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
        
        Consumable smallHealthPotion = new Consumable("small health potion", null);
        Consumable BigHealthPotion = new Consumable("big health potion", null);

        inv.getConsumables().add(smallHealthPotion);
        inv.getConsumables().add(BigHealthPotion);

        assertEquals(inv.getConsumable(0), smallHealthPotion);
        assertEquals(inv.getConsumable(1), BigHealthPotion);
        assertEquals(inv.getConsumable( 5), null);
    }
    
    @Test
    public void settingItems (){
        Inventory inv = new Inventory(4, 4);

        Weapon sword = new Weapon("sword", 1, null);
        Weapon mace = new Weapon("mace", 1, null);
        
        inv.getWeapons().add(null);
        inv.getWeapons().add(null);

        inv.setWeapon(0, sword);
        inv.setWeapon(1, mace);

        assertEquals(inv.getWeapon(0), sword);
        assertEquals(inv.getWeapon(1), mace);

        
        Consumable smallHealthPotion = new Consumable("small health potion", null);
        Consumable BigHealthPotion = new Consumable("big health potion", null);

        inv.getConsumables().add(null);
        inv.getConsumables().add(null);

        inv.setConsumable(0, smallHealthPotion);
        inv.setConsumable(1, BigHealthPotion);

        assertEquals(inv.getConsumable(0), smallHealthPotion);
        assertEquals(inv.getConsumable(1), BigHealthPotion);
     
    }
}

