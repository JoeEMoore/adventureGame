package cpsc224.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CoordinateTests {

    @Test
    void equalsReturnsTrueIfCoordinateIsEqual() {
        Coordinate c1 = new Coordinate(2, 4);
        Coordinate c2 = new Coordinate(2, 4);

        assertTrue(c1.equals(c2));
    }

}
