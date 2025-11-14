package cpsc224.moves;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import cpsc224.DamageType;
import cpsc224.effects.Effect;

public class MoveFactory {

    public static Move createSlashMove() {
        String name = "Slash";
        int damage = 10;
        DamageType dt = DamageType.Slice;
        int maxUses = -1;
        double accuracy = 0.8;
        boolean canTargetAllies = false;
        boolean canTargetEnemies = true;
        List<Effect> effects = new ArrayList<>();

        return new Move(name, damage, dt, maxUses, accuracy, canTargetAllies, canTargetEnemies, effects);
    }

}
