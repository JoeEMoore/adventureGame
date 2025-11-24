package cpsc224;

import cpsc224.creatures.Player;
import cpsc224.levels.Level;

public class Game {

    private static Game instance;

    private Player player;
    private Level level;

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

}
