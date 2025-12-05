package cpsc224.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import cpsc224.Game;
import cpsc224.creatures.Creature;
import cpsc224.items.Item;
import cpsc224.items.consumables.Consumable;
import cpsc224.items.weapons.Weapon;
import cpsc224.levels.Level;
import cpsc224.levels.rooms.Room;
import cpsc224.panels.GamePanel;

public class RoomItemsDialog extends JDialog {

    private GamePanel gamePanel;
    private Creature creature;
    private Room room;
    private Item currentItem;

    private JPanel dialogPanel;
    private JPanel buttonPanel;
    private JPanel itemPanel;

    private JList<Item> itemList;
    DefaultListModel<Item> itemListModel;
    private JScrollPane itemScrollPane;

    private JButton closeButton;
    private JButton pickUpButton;

    public RoomItemsDialog(Frame owner, GamePanel gamePanel, Creature creature) {
        super((Frame) owner, "Room Items", true);

        this.creature = creature;
        this.gamePanel = gamePanel;
        currentItem = null;
        Level level = Game.getInstance().getLevel();
        room = level.getRoom(level.getCurrentPosition());

        initComponents();
        layoutComponents();
        addListeners();
        pack();
        setLocationRelativeTo(owner);
    }
    
    private void initComponents() {
        itemListModel = new DefaultListModel<>();
        for (Item item : room.getItems()) {
            itemListModel.addElement(item);
        }

        itemList = new JList<>(itemListModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setSelectedIndex(0);
        itemScrollPane = new JScrollPane(itemList);
        itemScrollPane.setPreferredSize(new Dimension(200, 100));
        itemScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        itemScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        if (!itemListModel.isEmpty())
            currentItem = itemListModel.get(0);
        else
            currentItem = null;

        closeButton = new JButton("Close");
        pickUpButton = new JButton("Pick Up");
        pickUpButton.setEnabled(!itemListModel.isEmpty());

        buttonPanel = new JPanel();
        itemPanel = new JPanel();
        dialogPanel = new JPanel();
    }

    private void layoutComponents() {
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(pickUpButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(closeButton);

        itemPanel.setLayout(new BorderLayout());
        itemPanel.add(new JLabel("Room Items"), BorderLayout.NORTH);
        itemPanel.add(itemScrollPane, BorderLayout.CENTER);

        dialogPanel.setLayout(new BorderLayout());
        dialogPanel.add(itemPanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(dialogPanel);
    }

    private void addListeners() {
        pickUpButton.addActionListener(e -> {
            if (!creature.getInventory().addItem(currentItem)) {
                    JOptionPane.showMessageDialog(this, "No room in your inventory", "No Room", JOptionPane.OK_OPTION);
            } else {
                room.removeItem(currentItem);
                itemListModel.removeElement(currentItem);
                itemList.setSelectedIndex(0);
                currentItem = itemList.getSelectedValue();
                pickUpButton.setEnabled(currentItem != null);
                gamePanel.updateDisplay();
            }
        });

        closeButton.addActionListener(e -> {dispose();});

        itemList.addListSelectionListener(e -> {
            currentItem = itemList.getSelectedValue();
            pickUpButton.setEnabled(true);
        });
    }
}
