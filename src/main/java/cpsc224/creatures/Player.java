package cpsc224.creatures;

import cpsc224.items.Inventory;

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
        super(creatureName, maxHealth, modifiers, inventory);

        gold = 0;
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

}
