package cpsc224.creatures;

import cpsc224.items.Inventory;

public class Player extends Creature {

    private int gold;

    public Player(String creatureName, int maxHealth, CreatureModifiers modifiers, Inventory inventory) {
        super(creatureName, maxHealth, modifiers, inventory);

        gold = 0;
    }

    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }

}
