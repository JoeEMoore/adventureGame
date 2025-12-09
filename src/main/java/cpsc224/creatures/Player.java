package cpsc224.creatures;

import cpsc224.items.Inventory;
import cpsc224.levels.Coordinate;

import javax.swing.*;

/**
 * A class to represent a player in the game.
 */
public class Player extends Creature {

    private int gold;

    /**
     * Creates a player with the specified name, max health, base modifiers, and inventory
     * @param creatureName the name
     * @param maxHealth the max health
     * @param modifiers the base modifiers
     * @param inventory the inventory
     */
    public Player(String creatureName, int maxHealth, CreatureModifiers modifiers, Inventory inventory) {
        this(creatureName, maxHealth, modifiers, inventory, null);
    }

    /**
     * Creates a player with the specified name, max health, base modifiers, and inventory
     * @param creatureName the name
     * @param maxHealth the max health
     * @param modifiers the base modifiers
     * @param inventory the inventory
     */
    public Player(String creatureName, int maxHealth, CreatureModifiers modifiers, Inventory inventory, ImageIcon icon) {
        super(creatureName, maxHealth, modifiers, inventory, icon);

        gold = 0;
        setCurrentPosition( new Coordinate(0, 0));
    }

    /**
     * Gets the amount of gold.
     * @return amount of gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Adds the amount of gold.
     * @param amount the amount of gold
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * Subtracts the amount of gold.
     * @param amount the amount of gold
     */
    public void subractGold(int amount) {
        gold -= amount;
    }

}
