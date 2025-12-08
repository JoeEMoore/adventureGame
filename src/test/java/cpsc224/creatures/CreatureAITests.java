package cpsc224.creatures;

import cpsc224.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureAITests {

    @Test
    void calculateMoveReturnsZeroForOneMove() {
        Creature rat = TestUtils.createTestRat();
        CreatureAI ai = new CreatureAI(rat);
        assertEquals(1, rat.getInventory().getWeapons().size());

        assertEquals(0, ai.calculateMove());
    }
}
