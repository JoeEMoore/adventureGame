package cpsc224;

import java.awt.Color;

import javax.swing.ToolTipManager;

import cpsc224.creatures.CreatureFactory;
import cpsc224.pools.BossCreaturePool;
import cpsc224.pools.DefaultCreaturePool;
import cpsc224.creatures.Player;
import cpsc224.pools.DefaultItemPool;
import cpsc224.items.Inventory;
import cpsc224.items.consumables.ConsumableFactory;
import cpsc224.pools.ShopConsumablePool;
import cpsc224.levels.rooms.shop.DefaultShopInitializer;
import cpsc224.levels.rooms.shop.ShopInitializer;
import cpsc224.pools.ShopWeaponPool;
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
    public static final Color GAME_COLOR = new Color(143, 147, 184);
    public static final Color HEALTH_COLOR = new Color(224, 45, 45);
    public static final Color POISON_COLOR = new Color(32, 148, 16);

    public static void main(String[] args) {
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE); // make tool tips not disappear

        ShopInitializer shopInit = new DefaultShopInitializer(new ShopWeaponPool(), new ShopConsumablePool());
        Game game = Game.getInstance();
        LevelInitializer levelInit = new DefaultLevelInitializer(15, 6, new DefaultCreaturePool(), new DefaultItemPool(), shopInit, new BossCreaturePool());
        Level level = new Level(levelInit);
        game.setLevel(level);

        new SplashWindow();
    }
}