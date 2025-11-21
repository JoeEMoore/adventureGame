package cpsc224.panels;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.ArrayList;

import cpsc224.Fight;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.dialogs.InventoryDialog;
import cpsc224.items.weapons.Weapon;

/**
 * A panel to visualize a fight.
 */
public class FightPanel extends JPanel implements GamePanel {

    public static final Color HEALTH_COLOR = new Color(224, 45, 45);
    public static final Color POISON_COLOR = new Color(32, 148, 16);

    private Player player;
    private Creature enemy;
    private Fight fight;

    private JPanel topPanel;
    private JButton inventoryButton;

    private JPanel mainPanel;
    private JLabel infoLabel;

    private CreaturePanel playerPanel;
    private CreaturePanel enemyPanel;
    private JPanel infoPanel;
    private JPanel creaturePanel;


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

        initComponents();
        layoutComponents();
        addListeners();
    }

    /**
     * Initializes the panel's components
     */
    private void initComponents() {
        topPanel = new JPanel();
        inventoryButton = new JButton("Inventory");

        infoLabel = new JLabel("Start of fight between " + player.getName() + " and " + enemy.getName());
        infoPanel = new JPanel();

        playerPanel = new CreaturePanel(player);
        enemyPanel = new CreaturePanel(enemy);

        creaturePanel = new JPanel();
        mainPanel = new JPanel();
    }

    /**
     * Lays out the panel's components
     */
    private void layoutComponents() {
        // top panel
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        topPanel.add(inventoryButton);

        
        // the panel containing the player and enemy panels
        creaturePanel.setLayout(new BoxLayout(creaturePanel, BoxLayout.X_AXIS));
        creaturePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        creaturePanel.add(playerPanel);
        creaturePanel.add(Box.createGlue());
        
        Border empty = BorderFactory.createEmptyBorder(25, 50, 25, 50);
        Border line = BorderFactory.createLineBorder(Color.black);
        Border compound = BorderFactory.createCompoundBorder(line, empty);
        
        infoLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        infoLabel.setBorder(compound);
        infoPanel.add(infoLabel);
        creaturePanel.add(infoPanel);

        //infoPanel.setMaximumSize(infoPanel.getPreferredSize());
        infoPanel.setSize(getPreferredSize());
        
        creaturePanel.add(enemyPanel);
        creaturePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        
        // the panel containing creature panel and info label
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(creaturePanel);

        // align everything to the left
        topPanel.setAlignmentX(LEFT_ALIGNMENT);
        mainPanel.setAlignmentX(LEFT_ALIGNMENT);

        // add everything to this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(topPanel);
        add(Box.createGlue());
        add(mainPanel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    /**
     * Add listeners to the panel's components
     */
    private void addListeners() {
        inventoryButton.addActionListener(e -> {
            InventoryDialog invDialog = new InventoryDialog((Frame)SwingUtilities.getWindowAncestor(this), playerPanel, player);
            invDialog.setVisible(true);
        });


        // Setup listeners for each of the player's weapons
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
                    playerPanel.updateDisplay();
                    enemyPanel.updateDisplay();

                    // enemy attacks after delay
                    if (enemy.getHealth() > 0) {
                        enemyAttackTimer(3000);
                    }
                });
            }
        }
    }

    /**
     * The enemy's attack.
     * @param delay the delay in milliseconds for the enemies attack
     */
    private void enemyAttackTimer(int delay) {
        Timer timer = new Timer(delay, e -> {
            enemy.calculateEffects();
            enemyPanel.updateDisplay();
            displayMoveInfo(fight.creatureTurn(enemy, player));

            player.calculateEffects();
            playerPanel.updateDisplay();

            if (player.getHealth() > 0)
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

    public void updateDisplay() {
        playerPanel.updateDisplay();
        enemyPanel.updateDisplay();
    }


}
