package cpsc224;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.panels.FightPanel;
import cpsc224.windows.SplashWindow;

/**
 * A turn-based fighting game.
 */
public class Application {

    public static void main(String[] args) {
        new SplashWindow();
    }
}