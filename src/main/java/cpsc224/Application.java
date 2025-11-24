package cpsc224;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.levels.DefaultLevelInitializer;
import cpsc224.levels.Level;
import cpsc224.levels.LevelInitializer;
import cpsc224.panels.FightPanel;
import cpsc224.windows.SplashWindow;

/**
 * A turn-based fighting game.
 */
public class Application {

    public static void main(String[] args) {
        Game game = Game.getInstance();
        LevelInitializer levelInit = new DefaultLevelInitializer();
        Level level = new Level(7, 15, levelInit);
        Player player = CreatureFactory.createPlayer();

        game.setPlayer(player);
        game.setLevel(level);

        player.getInventory().setWeapon(1, WeaponFactory.createToxicStaff());
        player.getInventory().setWeapon(2, WeaponFactory.createHealStaff());

        new SplashWindow();
    }
}