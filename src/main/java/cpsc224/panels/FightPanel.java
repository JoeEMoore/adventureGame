package cpsc224.panels;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import cpsc224.Fight;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.items.weapons.Weapon;

/**
 * A panel to visualize a fight.
 */
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


    /**
     * Creates a panel for the specified player and enemy.
     * @param player the player
     * @param enemy the enemy
     * @param seed the game seed
     */
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

                    // targets either enemy or ally based on move and gets the result as a string
                    String result;
                    if (weapon.getMove().targetsAllies())
                        result = fight.performMove(player, player, weapon);
                    else
                        result = fight.performMove(player, enemy, weapon);
                    
                    // display info as a result of the move
                    displayMoveInfo(result);
                    playerPanel.displayEffectInfo(new ArrayList<>());
                    enemyPanel.displayEffectInfo(new ArrayList<>());
                    
                    // enemy attacks after delay
                    if (enemy.getHealth() > 0) {
                        enemyAttackTimer(3000);
                    }
                });
            }
        }
        

        // the panel containing the player and enemy panels
        creaturePanel = new JPanel();
        creaturePanel.setLayout(new BoxLayout(creaturePanel, BoxLayout.X_AXIS));
        creaturePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        creaturePanel.add(playerPanel);
        creaturePanel.add(Box.createGlue());
        creaturePanel.add(enemyPanel);
        creaturePanel.add(Box.createRigidArea(new Dimension(20, 0)));

        // add everything to this panel
        infoLabel = new JLabel();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        this.add(infoLabel);
        this.add(creaturePanel);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    /**
     * The enemy's attack.
     * @param delay the delay in milliseconds for the enemies attack
     */
    private void enemyAttackTimer(int delay) {
        Timer timer = new Timer(delay, e -> {
            enemyPanel.displayEffectInfo(enemy.calculateEffects());
            displayMoveInfo(fight.creatureTurn(enemy, player));

            playerPanel.displayEffectInfo(player.calculateEffects());
            playerPanel.enableWeaponButtons(true);
        });   
        timer.setRepeats(false);
        timer.start();   
    }

    /**
     * Displays info about the move.
     * @param text the move info
     */
    private void displayMoveInfo(String text) {
        infoLabel.setText(text);
        updateDisplay();
    }

    /**
     * Updates the fight display.
     */
    private void updateDisplay() {
        playerPanel.updateDisplay();
        enemyPanel.updateDisplay();
    }

}
