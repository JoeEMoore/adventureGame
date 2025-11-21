package cpsc224.windows;

import cpsc224.creatures.CreatureFactory;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.WeaponFactory;
import cpsc224.panels.FightPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameFrame extends JFrame {

    static Random rand = new Random();

    public GameFrame() {
        Player player = CreatureFactory.createPlayer();
        player.getInventory().setWeapon(1, WeaponFactory.createToxicStaff());
        player.getInventory().setWeapon(2, WeaponFactory.createHealStaff());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new FightPanel(player, CreatureFactory.createGoblin(), rand.nextLong()));
        setPreferredSize(new Dimension(1400, 800));
        setMinimumSize(new Dimension(1200, 700));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
