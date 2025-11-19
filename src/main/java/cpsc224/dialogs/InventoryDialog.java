package cpsc224.dialogs;

import cpsc224.creatures.Creature;
import cpsc224.items.consumables.Consumable;
import cpsc224.panels.CreaturePanel;

import javax.swing.*;
import java.awt.*;

public class InventoryDialog extends JDialog {

    private Creature creature;
    private CreaturePanel creaturePanel;
    private Consumable currentConsumable;

    private JPanel dialogPanel;
    private JPanel buttonPanel;

    private JList<Consumable> consumableList;
    DefaultListModel<Consumable> listModel;
    private JScrollPane consumableScrollPane;

    private JButton closeButton;
    private JButton useButton;

    public InventoryDialog(Window owner, CreaturePanel creaturePanel, Creature creature) {
        super(owner, "Inventory");

        this.creature = creature;
        this.creaturePanel = creaturePanel;
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
        listModel = new DefaultListModel<>();
        for (Consumable cons : creature.getInventory().getConsumables()) {
            listModel.addElement(cons);
        }

        consumableList = new JList<>(listModel);
        consumableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        consumableScrollPane = new JScrollPane(consumableList);
        consumableScrollPane.setPreferredSize(new Dimension(200, 100));
        consumableScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        consumableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        useButton = new JButton("Use");
        useButton.setEnabled(false);
        closeButton = new JButton("Close");

        buttonPanel = new JPanel();
        dialogPanel = new JPanel();
    }

    /**
     * Lays out the dialog's components
     */
    private void layoutComponents() {
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(useButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(closeButton);

        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.add(consumableScrollPane);
        dialogPanel.add(buttonPanel);

        add(dialogPanel);
    }

    /**
     * Adds listeners to the dialog's components
     */
    private void addListeners() {
        useButton.addActionListener(e -> {
            // use item and display effects
            creaturePanel.displayEffectInfo(currentConsumable.applyEffects(creature));

            // remove item
            creature.getInventory().getConsumables().remove(currentConsumable);
            listModel.removeElement(currentConsumable);

            // reset
            currentConsumable = null;
            consumableList.clearSelection();
        });

        closeButton.addActionListener(e -> {dispose();});

        consumableList.addListSelectionListener(e -> {
            currentConsumable = consumableList.getSelectedValue();
            useButton.setEnabled(currentConsumable != null);
        });
    }
}
