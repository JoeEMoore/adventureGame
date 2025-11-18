package cpsc224.moves;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cpsc224.damagetypes.DamageType;
import cpsc224.effects.Effect;
import cpsc224.effects.HealEffect;
import cpsc224.effects.PoisonEffect;

public class MoveFactory {

    public static Move createSlashMove() {
        final String name = "Slash";
        final int damage = 15;
        final DamageType dt = DamageType.Slice;
        final int maxUses = -1;
        final double accuracy = 0.8;
        final boolean targetsAllies = false;

        return new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
    }


    public static Move createScratchMove() {
        final String name = "Scratch";
        final int damage = 5;
        final DamageType dt = DamageType.Slice;
        final int maxUses = -1;
        final double accuracy = 0.8;
        final boolean targetsAllies = false;

        return new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
    }

    public static Move createSmashMove() {
        final String name = "Smash";
        final int damage = 20;
        final DamageType dt = DamageType.Blunt;
        final int maxUses = -1;
        final double accuracy = 0.6;
        final boolean targetsAllies = false;

        return new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
    }

    public static Move createToxicBoltMove() {
        final String name = "Toxic Bolt";
        final int damage = 10;
        final DamageType dt = DamageType.Magic;
        final int maxUses = -1;
        final double accuracy = 0.8;
        final boolean targetsAllies = false;

        Move move = new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
        move.setEffects(() -> {
            return Arrays.asList(new PoisonEffect(2));
        });
        return move;
    }

    public static Move createHealMove() {
        final String name = "Heal";
        final int damage = 0;
        final DamageType dt = DamageType.Pure;
        final int maxUses = -1;
        final double accuracy = 1.0;
        final boolean targetsAllies = true;

        Move move = new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
        move.setEffects(() -> {
            return Arrays.asList(new HealEffect(20));
        });
        return move;
    }
}
