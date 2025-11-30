package cpsc224.items.weapons;

import cpsc224.items.Item;
import cpsc224.items.weapons.moves.Move;

import javax.swing.*;
import java.awt.*;

/**
 * A class that represents weapons to be used by creatures in combat.
 */
public class Weapon extends Item {

    private int tier;
    private Move move;

    private ImageIcon icon;

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
        super(name);
        this.tier = tier;
        this.move = move;
        this.icon = icon;
    }

    /**
     * Gets the tier.
     * @return the tier
     */
    public int getTier() {
        return tier;
    }

    /**
     * Gets the move.
     * @return the tier
     */
    public Move getMove() {
        return move;
    }

    /**
     * Gets the icon.
     * @return the icon
     */
    public ImageIcon getIcon() { return icon; }

    @Override
    public String toString() {
        if (move.getMaxUses() < 0)
            return name;

        return name + " (" + move.getUses() + ")";
    }

    public String getToolTipText() {
        String text = "<b>" + getName() + "</b><br>";
        text += "Tier: " + getTier() + "<br><br>";
        text += getMove().getToolTipText();
        
        return text;
    }
}
