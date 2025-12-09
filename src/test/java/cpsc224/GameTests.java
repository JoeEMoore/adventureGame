package cpsc224;

import cpsc224.creatures.Player;
import cpsc224.levels.Level;
import cpsc224.levels.TestLevelInitializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTests {

    @Test
    void getInstanceGetsSameInstance() {
        assertEquals(Game.getInstance(), Game.getInstance());
    }

    @Test
    void setPlayerSetsPlayer() {
        Game g = Game.getInstance();
        Player p = TestUtils.createTestPlayer();
        g.setPlayer(p);
        assertEquals(p, g.getPlayer());
    }

    @Test
    void setLevelSetsLevel() {
        Game g = Game.getInstance();
        Level l = new Level(new TestLevelInitializer());
        g.setLevel(l);
        assertEquals(l, g.getLevel());
    }
}
