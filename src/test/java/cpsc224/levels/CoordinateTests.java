package cpsc224.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CoordinateTests {

    @Test
    void createCoordinateCreatesCorrectCoordinate() {
        Coordinate c1 = new Coordinate(2, 4);

        assertEquals(2, c1.getRow());
        assertEquals(4, c1.getCol());
    }

    @Test
    void equalsReturnsTrueIfCoordinateIsEqual() {
        Coordinate c1 = new Coordinate(2, 4);
        Coordinate c2 = new Coordinate(2, 4);

        assertTrue(c1.equals(c2));
    }

    @Test
    void equalsReturnsFalseIfCoordinateIsNotEqual() {
        Coordinate c1 = new Coordinate(2, 4);
        Coordinate c2 = new Coordinate(3, 4);

        assertFalse(c1.equals(c2));
    }

    @Test
    void isAdjacentReturnsTrueIfCoordinateIsAdjacent() {
        Coordinate c = new Coordinate(1, 2);

        assertTrue(c.isAdjacent(new Coordinate(0, 2)));
        assertTrue(c.isAdjacent(new Coordinate(2, 2)));
        assertTrue(c.isAdjacent(new Coordinate(1, 1)));
        assertTrue(c.isAdjacent(new Coordinate(1, 3)));
    }

    @Test
    void isAdjacentReturnFalseIfCoordinateIsNotAdjacent() {
        Coordinate c = new Coordinate(1, 2);

        assertFalse(c.isAdjacent(new Coordinate(2, 3)));
        assertFalse(c.isAdjacent(new Coordinate(3, 2)));
        assertFalse(c.isAdjacent(new Coordinate(1, 0)));
    }

}
