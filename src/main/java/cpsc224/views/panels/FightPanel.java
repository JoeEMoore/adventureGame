package cpsc224.views.panels;

import javax.swing.*;

import java.awt.*;

import cpsc224.Application;
import cpsc224.Fight;
import cpsc224.creatures.Creature;
import cpsc224.creatures.Player;
import cpsc224.views.dialogs.InventoryDialog;
import cpsc224.items.weapons.Weapon;
import cpsc224.levels.rooms.Room;

/**
 * A panel to visualize a fight.
 */
public class FightPanel extends JPanel implements GamePanel {

    private Player player;
    private Creature enemy;
    private Fight fight;
    private Room room;
    private boolean isPlayersTurn;
    
    private Image backgroundImage;

    private JButton inventoryButton;

    private JLabel infoLabel;
    private JPanel infoPanel;

    private CreaturePanel playerPanel;
    private CreaturePanel enemyPanel;


    /**
     * Creates a panel for the specified player and enemy.
     * @param player the player
     * @param room the room
     */
    public FightPanel(Player player, Room room) {
        this.player = player;
        this.room = room;
        enemy = room.getCreature();
        fight = new Fight(player, enemy);
        isPlayersTurn = true;

        initComponents();
        layoutComponents();
        addListeners();

        checkForWeapon();
    }

    /**
     * Initializes the panel's components
     */
    private void initComponents() {
        backgroundImage = new ImageIcon(getClass().getResource("/images/backgrounds/fightBackground.png")).getImage();

        inventoryButton = new JButton("Inventory");

        infoLabel = new JLabel("Start of fight between " + player.getName() + " and " + enemy.getName());
        infoLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        infoPanel = new JPanel();
        infoPanel.setBorder(null);
        infoPanel.setOpaque(false);
        infoLabel.setForeground(Color.WHITE);



        playerPanel = new CreaturePanel(player);
        enemyPanel = new CreaturePanel(enemy);

        playerPanel.setOpaque(false);
        enemyPanel.setOpaque(false);


        setBackground(Application.GAME_COLOR);
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

        c.insets = new Insets(0, 50, 0, 50);
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 3;
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        add(infoPanel, c);

        c.insets = new Insets(10, 10, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        add(inventoryButton, c);

        c.insets = new Insets(0, 50, 20, 0);
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.weightx = 1;
        c.weighty = 1;                 
        c.gridx = 0;
        c.gridy = 2;
        add(playerPanel, c);

        c.insets = new Insets(0, 0, 20, 50);
        c.anchor = GridBagConstraints.LAST_LINE_END;
         c.weightx = 1;
        c.weighty = 1;
        c.gridx = 2;
        c.gridy = 2;
        add(enemyPanel, c);
    }

    /**
     * Add listeners to the panel's components
     */
    private void addListeners() {
        inventoryButton.addActionListener(e -> {
            InventoryDialog invDialog = new InventoryDialog((Frame)SwingUtilities.getWindowAncestor(this), this, player, false);
            invDialog.setVisible(true);
        });

        // Setup listeners for each of the player's weapons
        JButton[] weaponButtons = playerPanel.getWeaponButtons();
        for (int i = 0; i < weaponButtons.length; i++) {
            Weapon weapon = player.getInventory().getWeapon(i);
            if (weapon != null) {
                weaponButtons[i].addActionListener(weaponEvent -> {
                    isPlayersTurn = false;
                    playerPanel.enableWeaponButtons(false);

                    // targets either enemy or ally based on move and gets the result as a string
                    String result;
                    if (weapon.getMove().targetsAllies())
                        result = fight.performMove(player, player, weapon);
                    else
                        result = fight.performMove(player, enemy, weapon);

                    // display info as a result of the move
                    displayMoveInfo(result);
                    updateDisplay();

                    if (!isWinner())
                        enemyAttackTimer(3000, 3000);
                });
            }
        }
    }

    private void checkForWeapon() {
        for (Weapon w : player.getInventory().getWeapons()) {
            if (w != null)
                return;
        }

        repeatedEnemyAttackTimer(500);
    }

    /**
     * The enemy's attack.
     * @param enemyTurnDelay the delay in milliseconds for the enemies attack
     * @param playerTurnDelay the delay in milliseconds for the player's turn to begin after the enemy's turn
     *                        only if the player has effects
     */
    private void enemyAttackTimer(int enemyTurnDelay, int playerTurnDelay) {
        Timer timer = new Timer(enemyTurnDelay, e -> {
            String result = enemy.calculateEffects();
            updateDisplay();

            if (isWinner()) {
                displayMoveInfo(result);
                return;
            }
            if (!result.isBlank())
                result += "<br>";

            displayMoveInfo(result + fight.creatureTurn(enemy, player));

            // delay before showing player effect results if player has effects
            if (!player.getEffects().isEmpty())
                playerStartTurnTimer(playerTurnDelay);
            else
                playerStartTurnTimer(0);
        });   
        timer.setRepeats(false);
        timer.start();   
    }

    private void playerStartTurnTimer(int delay) {
        Timer timer = new Timer(delay, e -> {
            String result = player.calculateEffects();
            if (!result.isBlank())
                displayMoveInfo(result);

            updateDisplay();

            if (isWinner())
                return;

            isPlayersTurn = true;
            updateDisplay();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void repeatedEnemyAttackTimer(int delay) {
        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            if (player.getHealth() <= 0) {
                timer.stop();
                return;
            }
            enemyAttackTimer(delay / 4, delay / 4);
        });
        timer.start();
    }

    private void winFight() {
        JOptionPane.showMessageDialog(this, "You beat " + enemy.getName() + "!", "You won!", JOptionPane.INFORMATION_MESSAGE);
        room.removeCreature();
        player.addGold((int)enemy.getMaxHealth());
        player.clearEffects();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new MapPanel());
        frame.revalidate();
        frame.repaint();
    }

    private void loseFight() {
        JOptionPane.showMessageDialog(this, "You were killed by " + enemy.getName() + "!", "You Died!", JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.getWindowAncestor(this).dispose();
        Application.main(null);
    }


    @Override
    protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Displays info about the move.
     * @param text the move info
     */
    public void displayMoveInfo(String text) {
        infoLabel.setText("<html><p style=\"text-align: center;\">" + text + "</p></html>");

        updateDisplay();
    }

    public void updateDisplay() {
        playerPanel.updateDisplay();
        enemyPanel.updateDisplay();

        playerPanel.enableWeaponButtons(isPlayersTurn);
    }

    public boolean isWinner() {
        if (player.getHealth() <= 0) {
            loseFight();
            return true;
        } 

        if (enemy.getHealth() <= 0) {
            winFight();
            return true;
        }

        return false;
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
