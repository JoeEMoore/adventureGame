package cpsc224.panels;

import cpsc224.Application;
import cpsc224.Game;
import cpsc224.creatures.Player;
import cpsc224.dialogs.InventoryDialog;
import cpsc224.dialogs.RoomItemsDialog;
import cpsc224.levels.Level;
import cpsc224.utils.BufferedImageBuilder;
import cpsc224.windows.SplashWindow;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel implements GamePanel {

    Player player;

    LevelPanel levelPanel;

    JProgressBar healthBar;
    JButton inventoryButton;
    JLabel goldLabel;
    JButton roomItemsButton;

    JButton exitButton;

    public MapPanel() {
        player = Game.getInstance().getPlayer();
        initComponents();
        layoutComponents();
        addListeners();

        updateDisplay();
    }

    private void initComponents() {
        levelPanel = new LevelPanel(Game.getInstance().getLevel(), player);
        healthBar = new JProgressBar(0, (int)player.getMaxHealth());
        //healthBar.setPreferredSize(new Dimension(150, 20));

        // gold icon
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/sprites/items/items.png");
        ImageIcon goldIcon = imageBuilder
                .sliceToSprite(32, 32, 24, 2)
                .scale(64, 64)
                .toImageIcon();

        goldLabel = new JLabel();
        goldLabel.setIcon(goldIcon);
        goldLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        inventoryButton = new JButton("Inventory");
        roomItemsButton = new JButton("Room Items");
        exitButton = new JButton("Exit");

        levelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    private void layoutComponents() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridy = 0;
        leftPanel.add(healthBar, c);
        c.weighty = 0;
        c.gridy = 1;
        leftPanel.add(goldLabel, c);
        c.gridy = 2;
        leftPanel.add(inventoryButton, c);
        c.gridy = 3;
        leftPanel.add(roomItemsButton, c);
        c.gridy = 4;
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_END;
        leftPanel.add(exitButton, c);

        setLayout(new GridBagLayout());
        c.insets = new Insets(30, 5, 30, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.BOTH;
        add(leftPanel, c);
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 1;
        c.gridx = 1;
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.BOTH;
        add(levelPanel, c);
    }

    private void addListeners() {
        inventoryButton.addActionListener(e -> {
            InventoryDialog invDialog = new InventoryDialog((Frame)SwingUtilities.getWindowAncestor(this), this, player, true);
            invDialog.setVisible(true);
        });

        exitButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit?", "Exit", javax.swing.JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                SwingUtilities.getWindowAncestor(this).dispose();
                new SplashWindow();
            }
        });

        roomItemsButton.addActionListener(e -> {
            RoomItemsDialog dialog = new RoomItemsDialog((Frame)SwingUtilities.getWindowAncestor(this), this, player);
            dialog.setVisible(true);
            updateDisplay();
        });
    }


    @Override
    public void updateDisplay() {
        goldLabel.setText(String.valueOf(player.getGold()));

        Level level = levelPanel.getLevel();
        //roomItemsButton.setEnabled(level.getRoom(level.getCurrentPosition()).hasItems());

        updateHealthBar();
        levelPanel.updateDisplay();
    }

    private void updateHealthBar() {
        healthBar.setValue((int)Math.ceil(player.getHealth()));
        healthBar.setForeground(Application.HEALTH_COLOR);
    }
}
