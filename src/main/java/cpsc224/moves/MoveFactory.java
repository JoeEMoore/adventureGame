package cpsc224.moves;

import java.util.ArrayList;
import java.util.List;

import cpsc224.DamageType;
import cpsc224.effects.Effect;

public class MoveFactory {

    public static Move createSlashMove() {
        final String name = "Slash";
        final int damage = 10;
        final DamageType dt = DamageType.Slice;
        final int maxUses = -1;
        final double accuracy = 0.8;
        final boolean canTargetAllies = false;
        final boolean canTargetEnemies = true;
        final List<Effect> effects = new ArrayList<>();

        return new Move(name, damage, dt, maxUses, accuracy, canTargetAllies, canTargetEnemies, effects);
    }

}
