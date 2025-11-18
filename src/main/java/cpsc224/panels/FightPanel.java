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

    public static final Color HEALTH_COLOR = new Color(224, 45, 45);
    public static final Color POISON_COLOR = new Color(32, 148, 16);

    private Player player;
    private Creature enemy;
    private Fight fight;

    private CreaturePanel playerPanel;
    private CreaturePanel enemyPanel;

    private JPanel creaturePanel;
    private JLabel infoLabel;


    public FightPanel(Player player, Creature enemy, long seed) {
        this.player = player;
        this.enemy = enemy;

        fight = new Fight(player, enemy, seed);

        playerPanel = new CreaturePanel(player);
        enemyPanel = new CreaturePanel(enemy);

        // Setup listeners for each of the player's weapoms
        JButton[] weaponButtons = playerPanel.getWeaponButtons();
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = player.getInventory().getWeapon(i);
            if (weapon != null) {
                weaponButtons[i].addActionListener(weaponEvent -> {

                    playerPanel.enableWeaponButtons(false);

                    String result;
                    if (weapon.getMove().targetsAllies())
                        result = fight.performAttack(player, player, weapon);
                    else
                        result = fight.performAttack(player, enemy, weapon);
                    
                    displayAttackInfo(result);
                    playerPanel.displayEffectInfo(new ArrayList<>());
                    enemyPanel.displayEffectInfo(new ArrayList<>());
                    
                    if (enemy.getHealth() > 0) {
                        Timer timer = enemyAttackTimer(2000);
                        timer.setRepeats(false);
                        timer.start();
                    }
                });
            }
        }
        

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
            enemyPanel.displayEffectInfo(enemy.calculateEffects());
            displayAttackInfo(fight.creatureTurn(enemy, player));

            playerPanel.displayEffectInfo(player.calculateEffects());
            playerPanel.enableWeaponButtons(true);
        });      
    }

    private void displayAttackInfo(String text) {
        infoLabel.setText(text);
        updateDisplay();
    }

    private void updateDisplay() {
        playerPanel.updateDisplay();
        enemyPanel.updateDisplay();
    }

}
