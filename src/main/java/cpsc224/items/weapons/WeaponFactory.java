package cpsc224.items.weapons;

import cpsc224.moves.Move;
import cpsc224.moves.MoveFactory;

public class WeaponFactory {

    public static Weapon createDullSword() {
        final String name = "Dull Sword";
        final int tier = 1;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move);
    }

}
