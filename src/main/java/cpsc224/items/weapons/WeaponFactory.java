package cpsc224.items.weapons;

import cpsc224.items.weapons.moves.Move;
import cpsc224.items.weapons.moves.MoveFactory;
import cpsc224.utils.BufferedImageBuilder;

import javax.swing.*;

/**
 * A factory class to create weapons.
 */
public class WeaponFactory {

    private static final int ICON_WIDTH = 32;
    private static final int ICON_HEIGHT = 32;

    private static ImageIcon getIcon(int row, int col) {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/sprites/items/items.png");
        return imageBuilder
                .sliceToSprite(ICON_WIDTH, ICON_HEIGHT, row, col)
                .scale(64, 64)
                .toImageIcon();
    }

    // *** PLAYER STARTER WEAPONS ***

    public static Weapon createDullSword() {
        final String name = "Dull Sword";
        final int tier = 1;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(0, 2));
    }


    // *** CREATURE-SPECIFIC WEAPONS ***

    public static Weapon createRatClaws() {
        final String name = "Rat Claws";
        final int tier = 1;
        final Move move = MoveFactory.createScratchMove();

        return new Weapon(name, tier, move, getIcon(2, 4));
    }

    public static Weapon createBirdTalons() {
        final String name = "Bird Talons";
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

        return new Weapon(name, tier, move, getIcon(10, 2));
    }

    public static Weapon createHealStaff() {
        final String name = "Heal Staff";
        final int tier = 3;
        final Move move = MoveFactory.createHealMove();

        return new Weapon(name, tier, move, getIcon(10, 1));
    }

    public static Weapon createRoyalSword() {
        final String name = "Royal Sword";
        final int tier = 2;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move);
    }

    public static Weapon createRustyDagger() {
        final String name = "Rusty Dagger";
        final int tier = 1;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(0, 0));
    }

    public static Weapon createSteelHammer() {
        final String name = "Steel Hammer";
        final int tier = 2;
        final Move move = MoveFactory.createSmashMove();

        return new Weapon(name, tier, move);
    }

    public static Weapon createBow() {
        final String name = "Bow";
        final int tier = 1;
        final Move move = MoveFactory.createShootMove();

        return new Weapon(name, tier, move);
    }
}
