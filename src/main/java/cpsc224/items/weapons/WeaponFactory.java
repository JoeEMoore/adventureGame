package cpsc224.items.weapons;

import cpsc224.moves.Move;
import cpsc224.moves.MoveFactory;

public class WeaponFactory {

    // *** PLAYER STARTER WEAPONS ***

    public static Weapon createDullSword() {
        final String name = "Dull Sword";
        final int tier = 1;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move);
    }


    // *** CREATURE-SPECIFIC WEAPONS ***

    public static Weapon createRatClaws() {
        final String name = "Rat Claws";
        final int tier = 1;
        final Move move = MoveFactory.createScratchMove();

        return new Weapon(name, tier, move);
    }

    // *** OTHER WEAPONS ***
    public static Weapon createWoodClub() {
        final String name = "Wood Club";
        final int tier = 1;
        final Move move = MoveFactory.createSmashMove();

        return new Weapon(name, tier, move);
    }

    public static Weapon createToxicStaff() {
        final String name = "Toxic Staff";
        final int tier = 1;
        final Move move = MoveFactory.createToxicBoltMove();

        return new Weapon(name, tier, move);
    }
}
