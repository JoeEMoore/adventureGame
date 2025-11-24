package cpsc224;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cpsc224.creatures.Creature;
import cpsc224.creatures.CreatureModifiers;
import cpsc224.creatures.Player;
import cpsc224.damagetypes.DamageType;
import cpsc224.effects.PoisonEffect;
import cpsc224.items.Inventory;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.moves.Move;

public class TestUtils {

    // CREATURES

    public static Player createTestPlayer() {
        final String name = "Player";
        final int health = 100;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, createTestSword());

        return new Player(name, health, cm, inv);
    }


    public static Creature createTestRat() {
        final String name = "Rat";
        final int health = 10;
        final CreatureModifiers cm = new CreatureModifiers(1, .3, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 0);
        inv.setWeapon(0, createTestClaws());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, weaponWeights);
    }

    public static Creature createTestTroll() {
        final String name = "Troll";
        final int health = 150;
        final CreatureModifiers cm = new CreatureModifiers(2, .05, new LinkedList<>(Arrays.asList(0.5, 1.5, 1.0, 2.0)));
        final Inventory inv = new Inventory(4, 0);
        inv.setWeapon(0, createTestWoodClub());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, weaponWeights);
    }

    // WEAPONS

    public static Weapon createTestSword() {
        final String name = "Sword";
        final int tier = 1;
        final Move move = createTestSlashMove();

        return new Weapon(name, tier, move);
    }

    public static Weapon createTestClaws() {
        final String name = "Claws";
        final int tier = 1;
        final Move move = createTestScratchMove();

        return new Weapon(name, tier, move);
    }

    public static Weapon createTestWoodClub() {
        final String name = "Wood Club";
        final int tier = 1;
        final Move move = createTestSmashMove();

        return new Weapon(name, tier, move);
    }

    public static Weapon createTestToxicStaff() {
        final String name = "Toxic Staff";
        final int tier = 1;
        final Move move = createTestToxicBoltMove();

        return new Weapon(name, tier, move);
    }

    // MOVES

    public static Move createTestSlashMove() {
        final String name = "Slash";
        final int damage = 15;
        final DamageType dt = DamageType.Slice;
        final int maxUses = -1;
        final double accuracy = 0.8;
        final boolean targetsAllies = false;

        return new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
    }

    public static Move createTestScratchMove() {
        final String name = "Scratch";
        final int damage = 5;
        final DamageType dt = DamageType.Slice;
        final int maxUses = -1;
        final double accuracy = 0.8;
        final boolean targetsAllies = false;

        return new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
    }

    public static Move createTestSmashMove() {
        final String name = "Smash";
        final int damage = 20;
        final DamageType dt = DamageType.Blunt;
        final int maxUses = -1;
        final double accuracy = 0.6;
        final boolean targetsAllies = false;

        return new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
    }

    public static Move createTestToxicBoltMove() {
        final String name = "Toxic Bolt";
        final int damage = 10;
        final DamageType dt = DamageType.Magic;
        final int maxUses = 5;
        final double accuracy = 0.8;
        final boolean targetsAllies = false;

        Move move = new Move(name, damage, dt, maxUses, accuracy, targetsAllies);
        move.setEffects(() -> {
            return Arrays.asList(new PoisonEffect(2));
        });
        return move;
    }

    // CONSUMABLES

    public static Consumable createTestPoisonPotion() {
        return new Consumable("Poison Potion", () -> {return List.of(new PoisonEffect(2));});
    }

    // MODIFIERS

    public static CreatureModifiers createTestModifiers() {
        return new CreatureModifiers(2, 2, new LinkedList<>(Arrays.asList(1.0, 2.0, 3.0, 4.0)));
    }

}
