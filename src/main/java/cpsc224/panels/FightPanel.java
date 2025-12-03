package cpsc224.panels;

import javax.swing.*;

import java.awt.*;

import cpsc224.Fight;
import cpsc224.Game;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.dialogs.InventoryDialog;
import cpsc224.items.weapons.Weapon;
import cpsc224.levels.rooms.Room;

/**
 * A panel to visualize a fight.
 */
public class FightPanel extends JPanel implements GamePanel {

    public static final Color HEALTH_COLOR = new Color(224, 45, 45);
    public static final Color POISON_COLOR = new Color(32, 148, 16);

    private Player player;
    private Creature enemy;
    private Fight fight;
    private Room room;

    private JButton inventoryButton;

    private JLabel infoLabel;
    private JPanel infoPanel;

    private CreaturePanel playerPanel;
    private CreaturePanel enemyPanel;


    /**
     * Creates a panel for the specified player and enemy.
     * @param player the player
     * @param room the toom
     */
    public FightPanel(Player player, Room room) {
        this.player = player;
        this.room = room;
        enemy = room.getCreature();
        fight = new Fight(player, enemy);

        initComponents();
        layoutComponents();
        addListeners();
    }

    /**
     * Initializes the panel's components
     */
    private void initComponents() {
        inventoryButton = new JButton("Inventory");

        infoLabel = new JLabel("Start of fight between " + player.getName() + " and " + enemy.getName());
        infoLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        infoPanel.setOpaque(false);

        playerPanel = new CreaturePanel(player);
        enemyPanel = new CreaturePanel(enemy);

        setBackground(new Color(143, 147, 184));
    }

    /**
     * Lays out the panel's components
     */
    private void layoutComponents() {

        infoLabel.setAlignmentY(BOTTOM_ALIGNMENT);
        infoPanel.setLayout(new BorderLayout());
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(infoLabel, BorderLayout.CENTER);

        // add everything to this panel
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 50, 30, 50);
        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 2;
        add(infoPanel, c);

        c.insets = new Insets(10, 10, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 1;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        add(inventoryButton, c);

        c.insets = new Insets(0, 50, 50, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        add(playerPanel, c);

        c.insets = new Insets(0, 0, 50, 50);
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridx = 2;
        c.gridy = 1;
        add(enemyPanel, c);
    }

    /**
     * Add listeners to the panel's components
     */
    private void addListeners() {
        inventoryButton.addActionListener(e -> {
            InventoryDialog invDialog = new InventoryDialog((Frame)SwingUtilities.getWindowAncestor(this), playerPanel, player, false);
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
                    // enemy is dead
                    } else {
                        JOptionPane.showMessageDialog(this, "You beat " + enemy.getName() + "!", "You won!", JOptionPane.INFORMATION_MESSAGE);
                        room.removeCreature(enemy);
                        player.addGold((int)enemy.getMaxHealth());
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        Game game = Game.getInstance();
                        frame.setContentPane(new MapPanel());
                        frame.pack();
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

    public Creature getEnemy(){
        return enemy;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        ImageIcon icon = ImageUtils.getImageIcon("images/backgrounds/StoneWall.png");
//        BufferedImage image = ImageUtils.toBufferedImage(icon.getImage());
//        RescaleOp op = new RescaleOp(1f, 100, null);
//        op.filter(image, image);
//
//        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
//    }

}
