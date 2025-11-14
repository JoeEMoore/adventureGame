package cpsc224.items.weapons;

import cpsc224.moves.Move;
import cpsc224.moves.MoveFactory;

public class WeaponFactory {

    // *** PLAYER WEAPONS ***

    public static Weapon createDullSword() {
        final String name = "Dull Sword";
        final int tier = 1;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move);
    }


    // *** NON-PLAYER WEAPONS ***

    public static Weapon createRatClaws() {
        final String name = "Rat Claws";
        final int tier = 1;
        final Move move = MoveFactory.createScratchMove();

        return new Weapon(name, tier, move);
    }

}
