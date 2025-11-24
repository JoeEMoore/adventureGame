package cpsc224.levels;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinate other)
            return other.getRow() == this.getRow() && other.getCol() == this.getCol();

        return false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
