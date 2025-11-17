package cpsc224.panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.GridLayout;

import cpsc224.Fight;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.Weapon;

public class FightPanel extends JPanel {

    private Player player;
    private Creature enemy;
    private Fight fight;

    private JPanel playerHealthPanel;
    private JProgressBar playerHealth;
    private JLabel playerHealthNumber;
    private JPanel enemyHealthPanel;
    private JProgressBar enemyHealth;
    private JLabel enemyHealthNumber;

    private JPanel playerPanel;
    private JLabel playerName;

    private JPanel enemyPanel;
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
                weaponButtons[i].addActionListener(weaponEvent -> {

                    enableWeaponButtons(false);

                    double playerDamage = fight.performAttack(player, enemy, weapon);
                    displayAttackInfo(player, enemy, playerDamage, enemyHealth, enemyHealthNumber);
                    
                    if (enemy.getHealth() > 0) {
                        Timer timer = enemyAttackTimer(2000);
                        timer.setRepeats(false);
                        timer.start();
                    }
                });
            } else {
                weaponButtons[i].setText("None");
                weaponButtons[i].setEnabled(false);
            }

            buttonPanel.add(weaponButtons[i]);
        }

        playerHealth = new JProgressBar(0, (int)player.getMaxHealth());
        playerHealth.setValue((int)player.getHealth());
        playerHealthNumber = new JLabel(String.valueOf(player.getHealth()));
        playerHealthPanel = new JPanel();
        playerHealthPanel.add(playerHealth);
        playerHealthPanel.add(playerHealthNumber);

        enemyHealth = new JProgressBar(0, (int)enemy.getMaxHealth());
        enemyHealth.setValue((int)enemy.getHealth());
        enemyHealthNumber = new JLabel(String.valueOf(enemy.getHealth()));
        enemyHealthPanel = new JPanel();
        enemyHealthPanel.add(enemyHealth);
        enemyHealthPanel.add(enemyHealthNumber);

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setMaximumSize(new Dimension(200, 100));  
        playerName = new JLabel(player.getName());
        playerName.setAlignmentX(LEFT_ALIGNMENT);
        playerPanel.add(playerName);
        playerPanel.add(playerHealthPanel);
        playerPanel.add(buttonPanel);

        enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        enemyPanel.setMaximumSize(new Dimension(200, 100));
        enemyName = new JLabel(enemy.getName());
        enemyPanel.add(enemyName);
        enemyPanel.add(enemyHealthPanel);

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

    private void displayAttackInfo(Creature source, Creature target, double damage, JProgressBar targetHealth, JLabel targetHealthNumber) {
        targetHealth.setValue((int)target.getHealth());
        targetHealthNumber.setText(String.valueOf(target.getHealth()));

        if (damage == 0)
            infoLabel.setText(target.getName() + " dodged the attack");
        else
            infoLabel.setText(source.getName() + " dealt " + damage + " damage to " + target.getName());
    }

    private void enableWeaponButtons(boolean b) {
        for (JButton button : weaponButtons)
            button.setEnabled(b);
    }

    private Timer enemyAttackTimer(int delay) {
        return new Timer(delay, enemyEvent -> {
            double enemyDamage = fight.creatureTurn(enemy, player);
            displayAttackInfo(enemy, player, enemyDamage, playerHealth, playerHealthNumber);
            enableWeaponButtons(true);
        });      
    }

}
