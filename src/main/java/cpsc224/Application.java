package cpsc224;

import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.panels.FightPanel;

/**
 * A turn-based fighting game.
 */
public class Application {

    static Random rand;

    public static void main(String[] args) {

        rand = new Random();

        Player player = CreatureFactory.createPlayer();
        player.getInventory().setWeapon(1, WeaponFactory.createToxicStaff());
        player.getInventory().setWeapon(2, WeaponFactory.createHealStaff());

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new FightPanel(player, CreatureFactory.createGoblin(), rand.nextLong()));
        frame.setPreferredSize(new Dimension(1400, 800));
        frame.setMinimumSize(new Dimension(1200, 700));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}