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

    private static ImageIcon getIcon(String name) {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/images/Weapons/" + name + ".png");
        return imageBuilder
                .scale(64, 64)
                .toImageIcon();
    }

    // *** CREATURE-SPECIFIC WEAPONS ***

    public static Weapon createRatClaws() {
        final String name = "Rat Claws";
        final int tier = 1;
        final Move move = MoveFactory.createScratchMove();

        return new Weapon(name, tier, move, getIcon(2, 4));
    }

    public static Weapon createMushroomCap() {
        final String name = "Mushroom Cap";
        final int tier = 1;
        final Move move = MoveFactory.createSporeShotMove();

        return new Weapon(name, tier, move, getIcon(name));
    }

    public static Weapon createBirdTalons() {
        final String name = "Bird Talons";
        final int tier = 1;
        final Move move = MoveFactory.createScratchMove();

        return new Weapon(name, tier, move);
    }

    // *** OTHER WEAPONS ***

    public static Weapon createCrudeBow() {
        final String name = "Crude Bow";
        final int tier = 1;
        final Move move = MoveFactory.createShootMove();

        return new Weapon(name, tier, move, getIcon(9, 2));
    }

    public static Weapon createLongBow() {
        final String name = "Long Bow";
        final int tier = 2;
        final Move move = MoveFactory.createShootMove();

        return new Weapon(name, tier, move, getIcon(9, 3));
    }

    public static Weapon createCrossbow() {
        final String name = "Crossbow";
        final int tier = 3;
        final Move move = MoveFactory.createShootMove();

        return new Weapon(name, tier, move, getIcon(9, 4));
    }

    public static Weapon createToxicStaff() {
        final String name = "Toxic Staff";
        final int tier = 3;
        final Move move = MoveFactory.createToxicBoltMove();

        return new Weapon(name, tier, move, getIcon(10, 2));
    }

    public static Weapon createHealStaff() {
        final String name = "Heal Staff";
        final int tier = 3;
        final Move move = MoveFactory.createHealMove();

        return new Weapon(name, tier, move, getIcon(10, 1));
    }

    public static Weapon createRustyDagger() {
        final String name = "Rusty Dagger";
        final int tier = 1;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(0, 0));
    }

    public static Weapon createSteelSword() {
        final String name = "Steel Sword";
        final int tier = 2;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(0, 2));
    }

    public static Weapon createRoyalSword() {
        final String name = "Royal Sword";
        final int tier = 3;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(0, 8));
    }

    public static Weapon createWoodClub() {
        final String name = "Wood Club";
        final int tier = 1;
        final Move move = MoveFactory.createSmashMove();

        return new Weapon(name, tier, move, getIcon(8, 2));
    }

    public static Weapon createSteelMace() {
        final String name = "Steel Mace";
        final int tier = 2;
        final Move move = MoveFactory.createSmashMove();

        return new Weapon(name, tier, move, getIcon(5, 0));
    }

    public static Weapon createSteelHammer() {
        final String name = "Steel Hammer";
        final int tier = 3;
        final Move move = MoveFactory.createSmashMove();

        return new Weapon(name, tier, move, getIcon(4, 4));
    }

    public static Weapon createRudimentaryStaff() {
        final String name = "Rudimentary Staff";
        final int tier = 1;
        final Move move = MoveFactory.createMagicMissileMove();

        return new Weapon(name, tier, move, getIcon(10, 0));
    }

    public static Weapon createEnchantedStaff() {
        final String name = "Enchanted Staff";
        final int tier = 2;
        final Move move = MoveFactory.createMagicMissileMove();

        return new Weapon(name, tier, move, getIcon(10, 7));
    }

    public static Weapon createStaffOfPower() {
        final String name = "Staff Of Power";
        final int tier = 3;
        final Move move = MoveFactory.createMagicMissileMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    // boss weapons

    // Rock golem moves
    public static Weapon createRockHeal() {
        final String name = "Rock heal";
        final int tier = 1;
        final Move move = MoveFactory.createHealMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createRockSlam() {
        final String name = "Rock Slam";
        final int tier = 3;
        final Move move = MoveFactory.createSmashMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createRockThrow() {
        final String name = "Rock throw";
        final int tier = 3;
        final Move move = MoveFactory.createShootMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createRockCut() {
        final String name = "Rock Cut";
        final int tier = 3;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(10, 6));

    }

    // Ice golem moves

    public static Weapon creatFrostHeal() {
        final String name = "Frost heal";
        final int tier = 1;
        final Move move = MoveFactory.createHealMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createIceball() {
        final String name = "Ice ball";
        final int tier = 3;
        final Move move = MoveFactory.createMagicMissileMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createIceSpear() {
        final String name = "Ice spear attack";
        final int tier = 3;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createIceClaws() {
        final String name = "Ice Claws";
        final int tier = 3;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(10, 6));

    }

    // Fire golem moves

    public static Weapon creatFireHeal() {
        final String name = "Fire heal";
        final int tier = 1;
        final Move move = MoveFactory.createHealMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createFireBall() {
        final String name = "Fireball";
        final int tier = 3;
        final Move move = MoveFactory.createMagicMissileMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createFireSword() {
        final String name = "Fire sword attack";
        final int tier = 3;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(10, 6));
    }

    public static Weapon createFireClaws() {
        final String name = "Fire Claws";
        final int tier = 3;
        final Move move = MoveFactory.createSlashMove();

        return new Weapon(name, tier, move, getIcon(10, 6));

    }
}
