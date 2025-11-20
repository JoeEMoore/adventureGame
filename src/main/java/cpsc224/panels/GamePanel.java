package cpsc224.panels;

import cpsc224.creatures.Player;
import cpsc224.items.weapons.Weapon;

import javax.swing.*;
import java.awt.*;

/**
 * An interface to represent all custom panels for this game.
 */
public interface GamePanel {

    /**
     * Refreshes the panel to account for changes in the game.
     */
    void updateDisplay();
}
