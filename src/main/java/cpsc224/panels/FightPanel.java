package cpsc224.panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;

import cpsc224.Fight;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.effects.Effect;
import cpsc224.effects.PoisonEffect;
import cpsc224.items.Inventory;
import cpsc224.items.weapons.Weapon;

public class FightPanel extends JPanel {

    private final Color HEALTH_COLOR = new Color(224, 45, 45);
    private final Color POISON_COLOR = new Color(32, 148, 16);

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
    private JTextArea playerInfo;

    private JPanel enemyPanel;
    private JLabel enemyName;
    private JTextArea enemyInfo;

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

                    String result;
                    if (weapon.getMove().targetsAllies())
                        result = fight.performAttack(player, player, weapon);
                    else
                        result = fight.performAttack(player, enemy, weapon);
                    
                    displayAttackInfo(result);
                    displayEffectInfo(new ArrayList<>(), player, playerInfo);
                    displayEffectInfo(new ArrayList<>(), enemy, enemyInfo);
                    
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
        playerHealth.setForeground(HEALTH_COLOR);
        playerHealthNumber = new JLabel(String.valueOf(player.getHealth()));
        playerHealthPanel = new JPanel();
        playerHealthPanel.add(playerHealth);
        playerHealthPanel.add(playerHealthNumber);

        enemyHealth = new JProgressBar(0, (int)enemy.getMaxHealth());
        enemyHealth.setValue((int)enemy.getHealth());
        enemyHealth.setForeground(HEALTH_COLOR);
        enemyHealthNumber = new JLabel(String.valueOf(enemy.getHealth()));
        enemyHealthPanel = new JPanel();
        enemyHealthPanel.add(enemyHealth);
        enemyHealthPanel.add(enemyHealthNumber);

        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setMaximumSize(new Dimension(200, 200));  
        playerName = new JLabel(player.getName());
        playerInfo = new JTextArea();
        playerInfo.setEditable(false);
        playerPanel.add(playerName);
        playerPanel.add(playerHealthPanel);
        playerPanel.add(buttonPanel);
        playerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playerPanel.add(playerInfo);

        enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        enemyPanel.setMaximumSize(new Dimension(200, 200));
        enemyName = new JLabel(enemy.getName());
        enemyInfo = new JTextArea();
        enemyInfo.setEditable(false);
        enemyPanel.add(enemyName);
        enemyPanel.add(enemyHealthPanel);
        enemyPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        enemyPanel.add(enemyInfo);

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

    private Timer enemyAttackTimer(int delay) {
        return new Timer(delay, e -> {
            displayEffectInfo(enemy.calculateEffects(), enemy, enemyInfo);
            displayAttackInfo(fight.creatureTurn(enemy, player));

            displayEffectInfo(player.calculateEffects(), player, playerInfo);
            enableWeaponButtons(true);
        });      
    }

    private void displayAttackInfo(String text) {
        infoLabel.setText(text);
        updateDisplay();
    }

    private void displayEffectInfo(Collection<String> info, Creature creature, JTextArea label) {
        String text = "";
        for (String result : info) {
            text += creature.getName() + " was effected by " + result + "\n";
        }
        label.setText(text);

        updateDisplay();
    }

    private void updateDisplay() {
        playerHealth.setValue((int)player.getHealth());
        playerHealthNumber.setText(String.valueOf(Math.round(player.getHealth())));

        enemyHealth.setValue((int)enemy.getHealth());
        enemyHealthNumber.setText(String.valueOf(Math.round(enemy.getHealth())));

        updateHealthBar(playerHealth, player);
        updateHealthBar(enemyHealth, enemy);
    }

    private void updateHealthBar(JProgressBar bar, Creature creature) {
        String toolTipText = "";
        bar.setForeground(HEALTH_COLOR);
        for (Effect e : creature.getEffects()) {
            toolTipText += e.getName() + " (" + e.getTurns() + ") <br>";
            if (e instanceof PoisonEffect)
                bar.setForeground(POISON_COLOR);
        }
        bar.setToolTipText("<html><p width=\"100\">" + toolTipText + "</p></html>");
    }

    private void enableWeaponButtons(boolean b) {
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = player.getInventory().getWeapon(i);
            if (weapon != null)
                weaponButtons[i].setEnabled(b);
        }
    }

}
