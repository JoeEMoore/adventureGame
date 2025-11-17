package cpsc224.panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Dimension;
import java.awt.GridLayout;

import cpsc224.Fight;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.Weapon;

public class FightPanel extends JPanel {

    //private final Dimension CREATURE_PANEL_MAX_DIMENSION = new Dimension(200, 100);

    private Player player;
    private Creature enemy;
    private Fight fight;

    private JPanel playerPanel;
    private JProgressBar playerHealth;
    private JLabel playerName;

    private JPanel enemyPanel;
    private JProgressBar enemyHealth;
    private JLabel enemyName;

    private JPanel creaturePanel;
    private JLabel infoLabel;

    JButton[] weaponButtons = new JButton[4];

    public FightPanel(Player player, Creature enemy, long seed) {
        this.player = player;
        this.enemy = enemy;

        fight = new Fight(player, enemy, seed);

        // Weapon buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = player.getInventory().getWeapon(i);
            weaponButtons[i] = new JButton();
            if (weapon != null) {
                weaponButtons[i].setText(weapon.getName());
                weaponButtons[i].addActionListener(e -> {
                    double damageDealt = fight.performAttack(player, enemy, weapon);
                    enemyHealth.setValue((int)enemy.getHealth());

                    if (damageDealt == 0)
                        infoLabel.setText(enemy.getName() + " dodged the attack");
                    else
                        infoLabel.setText(player.getName() + " dealt " + damageDealt + " damage to " + enemy.getName());
                });
            } else {
                weaponButtons[i].setText("None");
                weaponButtons[i].setEnabled(false);
            }

            buttonPanel.add(weaponButtons[i]);
        }

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setMaximumSize(new Dimension(200, 100));
        playerHealth = new JProgressBar(0, (int)player.getMaxHealth());
        playerHealth.setValue((int)player.getHealth());
        playerName = new JLabel(player.getName());
        playerName.setAlignmentX(LEFT_ALIGNMENT);
        playerPanel.add(playerName);
        playerPanel.add(playerHealth);
        playerPanel.add(buttonPanel);

        enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        enemyPanel.setMaximumSize(new Dimension(200, 100));
        enemyHealth = new JProgressBar(0, (int)enemy.getMaxHealth());
        enemyHealth.setValue((int)enemy.getHealth());
        enemyName = new JLabel(enemy.getName());
        enemyPanel.add(enemyName);
        enemyPanel.add(enemyHealth);

        creaturePanel = new JPanel();
        creaturePanel.setLayout(new BoxLayout(creaturePanel, BoxLayout.X_AXIS));
        creaturePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        creaturePanel.add(playerPanel);
        creaturePanel.add(Box.createGlue());
        creaturePanel.add(enemyPanel);
        creaturePanel.add(Box.createRigidArea(new Dimension(20, 0)));

        infoLabel = new JLabel();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        this.add(infoLabel);
        this.add(creaturePanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
    }

}
