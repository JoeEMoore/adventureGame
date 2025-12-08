package cpsc224.dialogs;

import cpsc224.Game;
import cpsc224.creatures.Creature;
import cpsc224.items.consumables.Consumable;
import cpsc224.levels.Level;
import cpsc224.panels.GamePanel;
import cpsc224.utils.BufferedImageBuilder;
import cpsc224.panels.FightPanel;
import cpsc224.items.weapons.*;

import javax.swing.*;

import java.awt.*;

/**
 * A dialog window that displays a creature's inventory and allows the user to
 * access items.
 */
public class InventoryDialog extends JDialog {

    private static final int ICON_WIDTH = 16;
    private static final int ICON_HEIGHT = 16;

    private Creature creature;
    private Creature enemy;
    private GamePanel gamePanel;
    private boolean canDropItems;

    private JPanel dialogPanel;
    private JPanel buttonPanel;
    private JPanel weaponPanel;
    private JPanel consumablePanel;

    private static JList<Consumable> consumableList;
    private JList<Weapon> weaponList;
    DefaultListModel<Consumable> consumableListModel;
    DefaultListModel<Weapon> weaponListModel;
    private JScrollPane consumableScrollPane;
    private JScrollPane weaponScrollPane;

    private JButton closeButton;
    private JButton useButton;
    private JButton dropButton;

    /**
     * Creates the inventory dialog.
     * @param owner the frame the dialog should appear over
     * @param gamePanel the panel to update after actions are performed
     * @param creature the creature that will have its inventory displayed
     */
    public InventoryDialog(Frame owner, GamePanel gamePanel, Creature creature, boolean canDropItems) {
        super((Frame) owner, "Inventory", true);

        this.creature = creature;
        this.gamePanel = gamePanel;
        this.canDropItems = canDropItems;

        initComponents();
        layoutComponents();
        addListeners();
        updateDisplay();
        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Initializes the dialog's components
     */
    private void initComponents() {
        consumableListModel = new DefaultListModel<>();
        for (Consumable cons : creature.getInventory().getConsumables()) {
            consumableListModel.addElement(cons);
        }


        consumableList = new JList<>(consumableListModel);
        consumableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        consumableScrollPane = new JScrollPane(consumableList);
        consumableScrollPane.setPreferredSize(new Dimension(250, 290));
        consumableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        consumableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        consumableList.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Consumable cons = (Consumable) value;
            label.setIcon(cons.getIcon());

            return label;
        }
    });

        weaponListModel = new DefaultListModel<>();
        for (Weapon weapon : creature.getInventory().getWeapons()) {
            weaponListModel.addElement(weapon);
        }

        weaponList = new JList<>(weaponListModel);
        weaponList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        weaponList.setSelectedIndex(0);
        weaponScrollPane = new JScrollPane(weaponList);
        weaponScrollPane.setPreferredSize(new Dimension(250, 290));
        weaponScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        weaponScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        weaponList.setCellRenderer(new DefaultListCellRenderer(){

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            Weapon weapon = (Weapon) value;
            
            label.setIcon(weapon.getIcon());
            return label;
        }
    });

//        if (!weaponListModel.isEmpty())
//            currentWeapon = weaponListModel.get(0);
//        else if (!consumableListModel.isEmpty())
//            currentConsumable = consumableListModel.get(0);
//        else
//            currentWeapon = null;

        useButton = new JButton("Use");
        //useButton.setEnabled(false);
        closeButton = new JButton("Close");
        dropButton = new JButton("Drop");
        //dropButton.setEnabled(!(weaponListModel.isEmpty() && consumableListModel.isEmpty()));

        buttonPanel = new JPanel();
        dialogPanel = new JPanel();
        weaponPanel = new JPanel();
        consumablePanel = new JPanel();
    }

    /**
     * Lays out the dialog's components
     */
    private void layoutComponents() {
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(useButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(dropButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(closeButton);

        // Weapon panel
        weaponPanel.setLayout(new BorderLayout());
        weaponPanel.add(new JLabel("Weapons"), BorderLayout.NORTH);
        weaponPanel.add(weaponScrollPane, BorderLayout.CENTER);

        // Consumable panel
        consumablePanel.setLayout(new BorderLayout());
        consumablePanel.add(new JLabel("Consumables"), BorderLayout.NORTH);
        consumablePanel.add(consumableScrollPane, BorderLayout.CENTER);

        dialogPanel.setLayout(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, weaponPanel, consumablePanel);

        dialogPanel.add(splitPane, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(dialogPanel);
    }

    /**
     * Adds listeners to the dialog's components
     */
    private void addListeners() {
        useButton.addActionListener(e -> {
            // use item and display effects
            Consumable cons = consumableList.getSelectedValue();

            if (cons == null)
                return;

            // use consumable while in a fight
            if (gamePanel instanceof FightPanel fightPanel) {

                // display result of consumable
                if (!cons.getAffectsSelf())
                    fightPanel.displayMoveInfo(cons.applyEffects(fightPanel.getEnemy()));
                else
                    fightPanel.displayMoveInfo(cons.applyEffects(creature));

                // dispose this dialog if the fight is over
                if (fightPanel.isWinner())
                    dispose();
                
            } else {
                // use consumable on self
                cons.applyEffects(creature);
            }
            
            gamePanel.updateDisplay();

            // remove item
            creature.getInventory().getConsumables().remove(cons);
            consumableListModel.removeElement(cons);

            // reset
            consumableList.setSelectedIndex(0);
        });

        closeButton.addActionListener(e -> {dispose();});

        consumableList.addListSelectionListener(e -> {
            weaponList.clearSelection();
            updateDisplay();
        });

        weaponList.addListSelectionListener(e -> {
            consumableList.clearSelection();
            updateDisplay();
        });

        dropButton.addActionListener(e -> {
            Level level = Game.getInstance().getLevel();

            Consumable cons = consumableList.getSelectedValue();
            Weapon weapon = weaponList.getSelectedValue();
            if (cons != null) {   // Dropping a consumable
                creature.getInventory().getConsumables().remove(cons);
                consumableListModel.removeElement(cons);
                level.getRoom(level.getCurrentPosition()).addItem(cons);
    
                // reset
                consumableList.setSelectedIndex(0);
            } else if (weapon != null) {   // Dropping a weapon
                creature.getInventory().getWeapons().remove(weapon);
                weaponListModel.removeElement(weapon);
                level.getRoom(level.getCurrentPosition()).addItem(weapon);

                // reset
                weaponList.setSelectedIndex(0);
            }
            gamePanel.updateDisplay();
        });
    }

    private static ImageIcon getIcon(int row, int col) {

    BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/sprites/items/items.png");
        return imageBuilder
                .sliceToSprite(ICON_WIDTH, ICON_HEIGHT, row, col)
                .scale(64, 64)
                .toImageIcon();
    }

    
    public void updateDisplay() {
        Weapon weapon = weaponList.getSelectedValue();
        Consumable cons = consumableList.getSelectedValue();
        dropButton.setEnabled(weapon != null || cons != null);
        useButton.setEnabled(cons != null);
    }
}
