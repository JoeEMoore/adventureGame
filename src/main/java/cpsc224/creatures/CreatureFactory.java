package cpsc224.creatures;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import cpsc224.items.Inventory;
import cpsc224.items.consumables.ConsumableFactory;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.utils.BufferedImageBuilder;

import javax.swing.*;

/**
 * A factory class to create creatures.
 */

public class CreatureFactory {

    private static ImageIcon getIcon(String name, boolean flipHorizontally) {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/images/creatures/" + name + ".png");

        if (flipHorizontally)
            imageBuilder.flipHorizontally();

        return imageBuilder
                .scale(256, 256)
                .toImageIcon();
    }

    private static ImageIcon getIcon(String name) {
        return getIcon(name, true);
    }


    public static Player createPlayer() {
        final String name = "Player";
        final int health = 200;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, WeaponFactory.createRustyDagger());
        inv.setConsumable(0, ConsumableFactory.createSmallHealthPotion());
        inv.setConsumable(1, ConsumableFactory.createWeaponRefillPotion());

        return new Player(name, health, cm, inv);
    }

    public static Player createMagePlayer() {
        final String name = "Mage";
        final int health = 200;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, WeaponFactory.createBasicWand());
        inv.setConsumable(0, ConsumableFactory.createSmallHealthPotion());
        inv.setConsumable(1, ConsumableFactory.createWeaponRefillPotion());


        return new Player(name, health, cm, inv);
    }

    public static Player createBluntPlayer() {
        final String name = "Barbarian";
        final int health = 200;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, WeaponFactory.createWoodClub());
        inv.setConsumable(0, ConsumableFactory.createSmallHealthPotion());
        inv.setConsumable(1, ConsumableFactory.createWeaponRefillPotion());

        return new Player(name, health, cm, inv);
    }

    public static Player createSlashPlayer() {
        final String name = "Knight";
        final int health = 200;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, WeaponFactory.createRustyDagger());
        inv.setConsumable(0, ConsumableFactory.createSmallHealthPotion());
        inv.setConsumable(1, ConsumableFactory.createWeaponRefillPotion());

        return new Player(name, health, cm, inv);
    }

    public static Player createRangePlayer() {
        final String name = "Ranger";
        final int health = 200;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, WeaponFactory.createCrudeBow());
        inv.setConsumable(0, ConsumableFactory.createSmallHealthPotion());
        inv.setConsumable(1, ConsumableFactory.createWeaponRefillPotion());

        return new Player(name, health, cm, inv);
    }


    public static Creature createRat() {
        final String name = "Rat";
        final int health = 10;
        final CreatureModifiers cm = new CreatureModifiers(1, .15, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 0);
        inv.setWeapon(0, WeaponFactory.createRatClaws());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, getIcon(name), weaponWeights);
    }

    public static Creature createTroll() {
        final String name = "Troll";
        final int health = 150;
        final CreatureModifiers cm = new CreatureModifiers(2, .05, new LinkedList<>(Arrays.asList(0.7, 1.5, 0.8, 2.0)));
        final Inventory inv = new Inventory(4, 0);
        inv.setWeapon(0, WeaponFactory.createWoodClub());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, weaponWeights);
    }

    public static Creature createBird() {
        final String name = "Bird";
        final int health = 30;
        final CreatureModifiers cm = new CreatureModifiers(3, .2, new LinkedList<>(Arrays.asList(0.5, 0.7, 2.0, 1.4)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0,WeaponFactory.createBirdTalons());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, weaponWeights);
    }

    public static Creature createWitch() {
        final String name = "Witch";
        final int health = 75;
        final CreatureModifiers cm = new CreatureModifiers(2, .1, new LinkedList<>(Arrays.asList(1.2, 1.4, 0.8, 0.7)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.createToxicStaff());
        inv.setWeapon(1, WeaponFactory.createSteelSword());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(4.0);
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, weaponWeights);
    }

    public static Creature createGuardian() {
        final String name = "Guardian";
        final int health = 105;
        final CreatureModifiers cm = new CreatureModifiers(2, .15, new LinkedList<>(Arrays.asList(0.5, 0.8, 1.1, 2.0)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.createRoyalSword());
        inv.setWeapon(1, WeaponFactory.createSteelHammer());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(2.0);
        weaponWeights.add(1.0);
        
        return new Creature(name, health, cm, inv, weaponWeights);
    }

    public static Creature createGoblin() {
        final String name = "Goblin";
        final int health = 50;
        final CreatureModifiers cm = new CreatureModifiers(1, 0.1, new LinkedList<>(Arrays.asList(1.0, 0.9, 1.2, 1.4)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.createRustyDagger());
        inv.setWeapon(1, WeaponFactory.createWoodClub());
        inv.setWeapon(2, WeaponFactory.createSteelSword());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(1.5);
        weaponWeights.add(1.0);
        weaponWeights.add(0.5);

        return new Creature(name, health, cm, inv, getIcon(name), weaponWeights);
    }

    public static Creature createSkeleton() {
        final String name = "Skeleton";
        final int health = 50;
        final CreatureModifiers cm = new CreatureModifiers(1, 0.1, new LinkedList<>(Arrays.asList(1.5, 0.5, 0.5, 2.0)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.createCrossbow());
        inv.setWeapon(1, WeaponFactory.createRustyDagger());
        List<Double> weaponWeights = new ArrayList<Double>();
        weaponWeights.add(3.0);
        weaponWeights.add(1.0);

        return new Creature(name, health, cm, inv, weaponWeights);
    }


    // Bosses

    public static Creature createFireGolem() {
        final String name = "Fire golem";
        final int health = 500;
        final CreatureModifiers cm = new CreatureModifiers(1.5, 0.1, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.5, 0.5)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.creatFireHeal());
        inv.setWeapon(1, WeaponFactory.createFireBall());
        inv.setWeapon(2, WeaponFactory.createFireSword());
        inv.setWeapon(3, WeaponFactory.createFireClaws());
        List<Double> weaponWeights = new ArrayList<Double>();

        weaponWeights.add(1.0);
        weaponWeights.add(2.0);
        weaponWeights.add(2.0);
        weaponWeights.add(1.5);

        return new Creature(name, health, cm, inv, getIcon("FireGolemBoss"),weaponWeights);
    }

    public static Creature createRockGolem() {
        final String name = "Rock golem";
        final int health = 800;
        final CreatureModifiers cm = new CreatureModifiers(1, 0.1, new LinkedList<>(Arrays.asList(0.5,0.5,0.5,2.0)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.createRockHeal());
        inv.setWeapon(1, WeaponFactory.createRockSlam());
        inv.setWeapon(2, WeaponFactory.createRockThrow());
        inv.setWeapon(3, WeaponFactory.createRockCut());
        List<Double> weaponWeights = new ArrayList<Double>();


        weaponWeights.add(1.0);
        weaponWeights.add(2.0);
        weaponWeights.add(2.0);
        weaponWeights.add(1.5);

        return new Creature(name, health, cm, inv, getIcon("RockGolemBoss"), weaponWeights);
    }

    public static Creature createIceGolem() {
        final String name = "Ice golem";
        final int health = 500;
        final CreatureModifiers cm = new CreatureModifiers(1.5, 0.1, new LinkedList<>(Arrays.asList(1.5, 1.5, 1.0, 0.5)));
        final Inventory inv = new Inventory(4,0);
        inv.setWeapon(0, WeaponFactory.creatFrostHeal());
        inv.setWeapon(1, WeaponFactory.createIceball());
        inv.setWeapon(2, WeaponFactory.createIceSpear());
        inv.setWeapon(3, WeaponFactory.createIceClaws());
        List<Double> weaponWeights = new ArrayList<Double>();

        weaponWeights.add(1.0);
        weaponWeights.add(2.0);
        weaponWeights.add(2.0);
        weaponWeights.add(1.5);

        return new Creature(name, health, cm, inv, getIcon("IceGolemBoss"), weaponWeights);
    }
}
