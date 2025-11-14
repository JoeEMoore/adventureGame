package cpsc224.creatures;

import java.util.Arrays;
import java.util.LinkedList;

import cpsc224.items.Inventory;
import cpsc224.items.weapons.WeaponFactory;

public class CreatureFactory {

    public static Player createPlayer() {
        final String name = "Player";
        final int health = 100;
        final CreatureModifiers cm = new CreatureModifiers(1, .2, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(4, 4);
        inv.setWeapon(0, WeaponFactory.createDullSword());

        return new Player(name, health, cm, inv);
    }

    public static Creature createRat() {
        final String name = "Rat";
        final int health = 10;
        final CreatureModifiers cm = new CreatureModifiers(1, .3, new LinkedList<>(Arrays.asList(1.0, 1.0, 1.0, 1.0)));
        final Inventory inv = new Inventory(1, 0);
        inv.setWeapon(0, WeaponFactory.createRatClaws());

        return new Creature(name, health, cm, inv);
    }

}
