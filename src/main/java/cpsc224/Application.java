package cpsc224;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.ToolTipManager;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.DefaultCreaturePool;
import cpsc224.creatures.Player;
import cpsc224.items.DefaultItemPool;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.levels.DefaultLevelInitializer;
import cpsc224.levels.Level;
import cpsc224.levels.LevelInitializer;
import cpsc224.windows.SplashWindow;

/**
 * A turn-based fighting game.
 */
public class Application {

    public static final Color MENU_COLOR = new Color(156, 219, 173);
    public static final Color HEALTH_COLOR = new Color(224, 45, 45);
    public static final Color POISON_COLOR = new Color(32, 148, 16);
    public static void main(String[] args) {
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE); // make tool tips not disappear


        Game game = Game.getInstance();
        LevelInitializer levelInit = new DefaultLevelInitializer(15, 6, new DefaultCreaturePool(), new DefaultItemPool());
        Level level = new Level(levelInit);
        Player player = CreatureFactory.createPlayer();

        game.setPlayer(player);
        game.setLevel(level);

        player.getInventory().setWeapon(1, WeaponFactory.createToxicStaff());
        player.getInventory().setWeapon(2, WeaponFactory.createHealStaff());

        new SplashWindow();
    }
}