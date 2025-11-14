package cpsc224.items.weapons;

import cpsc224.moves.Move;

public class Weapon {

    private String name;
    private int tier;
    private Move move;

    public Weapon(String name, int tier, Move move) {
        this.name = name;
        this.tier = tier;
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
