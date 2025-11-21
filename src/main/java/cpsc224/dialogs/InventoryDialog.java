package cpsc224.dialogs;

import cpsc224.creatures.Creature;
import cpsc224.items.consumables.Consumable;
import cpsc224.panels.GamePanel;
import cpsc224.items.weapons.*;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;

/**
 * A dialog window that displays a creature's inventory and allows the user to
 * access items.
 */
public class InventoryDialog extends JDialog {

    private Creature creature;
    private GamePanel creaturePanel;
    private Consumable currentConsumable;
    private Weapon currentWeapon;
    private boolean canDropItems;

    private JPanel dialogPanel;
    private JPanel buttonPanel;
    private JPanel weaponPanel;
    private JPanel consumablePanel;

    private JList<Consumable> consumableList;
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
        this.creaturePanel = gamePanel;
        this.canDropItems = canDropItems;
        currentConsumable = null;

        initComponents();
        layoutComponents();
        addListeners();
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
        consumableScrollPane.setPreferredSize(new Dimension(200, 100));
        consumableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        consumableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        weaponListModel = new DefaultListModel<>();
        for (Weapon weapon : creature.getInventory().getWeapons()) {
            weaponListModel.addElement(weapon);
        }

        weaponList = new JList<>(weaponListModel);
        weaponList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        weaponScrollPane = new JScrollPane(weaponList);
        weaponScrollPane.setPreferredSize(new Dimension(200, 100));
        weaponScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        weaponScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        useButton = new JButton("Use");
        useButton.setEnabled(false);
        closeButton = new JButton("Close");
        dropButton = new JButton("Drop");
        dropButton.setEnabled(false);

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
            currentConsumable.applyEffects(creature);
            creaturePanel.updateDisplay();

            // remove item
            creature.getInventory().getConsumables().remove(currentConsumable);
            consumableListModel.removeElement(currentConsumable);

            // reset
            currentConsumable = null;
            consumableList.clearSelection();
        });

        closeButton.addActionListener(e -> {dispose();});

        consumableList.addListSelectionListener(e -> {
            currentConsumable = consumableList.getSelectedValue();
            useButton.setEnabled(currentConsumable != null);
            dropButton.setEnabled(canDropItems && currentConsumable != null);
            
            if (currentConsumable != null) {
                weaponList.clearSelection();
                currentWeapon = null;
            }
        });

        weaponList.addListSelectionListener(e -> {
            currentWeapon = weaponList.getSelectedValue();
            dropButton.setEnabled(canDropItems && currentWeapon != null);

            if (currentWeapon != null) {
                consumableList.clearSelection();
                currentConsumable = null;
            }
        });

        dropButton.addActionListener(e ->{
            // Dropping a consumable
            Consumable droppedConsumable = currentConsumable;
            creature.getInventory().getConsumables().remove(currentConsumable);
            consumableListModel.removeElement(currentConsumable);
            creaturePanel.updateDisplay();

            // reset
            currentConsumable = null;
            consumableList.clearSelection();   

            // Dropping a weapon
            Weapon droppedWeapon = currentWeapon;
            creature.getInventory().getWeapons().remove(currentWeapon);
            weaponListModel.removeElement(currentWeapon);
            creaturePanel.updateDisplay();

            // reset
            currentWeapon = null;
            weaponList.clearSelection();
        });
    }
}
