package cpsc224.windows;

import cpsc224.Game;
import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.levels.DefaultLevelInitializer;
import cpsc224.levels.Level;
import cpsc224.levels.LevelInitializer;
import cpsc224.panels.FightPanel;
import cpsc224.panels.LevelPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameFrame extends JFrame {

    private Game game;
    static Random rand = new Random();

    public GameFrame() {
        game = Game.getInstance();

        //JPanel panel = new JPanel();
        //panel.setLayout(new OverlayLayout(panel));

        add(new LevelPanel(game.getLevel(), game.getPlayer()));

        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 800));
        setMinimumSize(new Dimension(1200, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
