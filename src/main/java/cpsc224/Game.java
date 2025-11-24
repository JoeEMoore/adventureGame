package cpsc224;

import javax.swing.JPanel;

import cpsc224.creatures.Player;
import cpsc224.levels.Level;

public class Game {

    private static Game instance;

    private Player player;
    private Level level;
    private JPanel rootPanel;

    private Game() {}

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();

        return instance;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void setRootPanel(JPanel rootPanel) {
        this.rootPanel = rootPanel;
    }
}
