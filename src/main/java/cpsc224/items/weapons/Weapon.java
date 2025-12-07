package cpsc224.items.weapons;

import cpsc224.items.Item;
import cpsc224.items.weapons.moves.Move;

import javax.swing.*;
import java.awt.*;

/**
 * A class that represents weapons to be used by creatures in combat.
 */
public class Weapon extends Item {

    private Move move;

    /**
     * Creates a weapon with the specified name, tier, and move.
     * @param name the name
     * @param tier the tier
     * @param move the move
     */
    public Weapon(String name, int tier, Move move) {
        this(name, tier, move, null);
    }

    /**
     * Creates a weapon with the specified name, tier, and move.
     * @param name the name
     * @param tier the tier
     * @param move the move
     */
    public Weapon(String name, int tier, Move move, ImageIcon icon) {
        super(name, tier, icon);
        this.move = move;
    }

    /**
     * Gets the move.
     * @return the tier
     */
    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        if (move.getMaxUses() < 0)
            return name;

        return name + " (" + move.getUses() + ")";
    }

    @Override
    public String getToolTipText() {
        String text = "<b>" + getName() + "</b><br>";
        text += "Tier: " + getTier() + "<br><br>";
        text += getMove().getToolTipText();
        
        return text;
    }
}
