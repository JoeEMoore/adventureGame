package cpsc224.items.weapons;

import cpsc224.moves.Move;

import javax.swing.*;
import java.awt.*;

/**
 * A class that represents weapons to be used by creatures in combat.
 */
public class Weapon {

    private String name;
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
        this.name = name;
        this.tier = tier;
        this.move = move;
        this.icon = icon;
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
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
        return name;
    }
}
