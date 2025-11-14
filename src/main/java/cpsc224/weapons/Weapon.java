package cpsc224.weapons;

import cpsc224.moves.Move;

public class Weapon {
    protected String name;
    protected int tier;
    


    private String name;
    private int tier;
    private Move move;

    public Weapon(int tier, String name, Move move) {
        this.tier = tier;
        this.name = name;
        this.move = move;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public Move getMove() {
        return move;
    }
}
