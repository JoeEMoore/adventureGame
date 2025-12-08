package cpsc224.views.windows;

import cpsc224.Game;
import cpsc224.views.panels.PlayerSelectionPanel;

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

        setContentPane(new PlayerSelectionPanel());

        //add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 800));
        setMinimumSize(new Dimension(1200, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
