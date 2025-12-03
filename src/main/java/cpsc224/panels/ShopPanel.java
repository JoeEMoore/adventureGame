package cpsc224.panels;

import cpsc224.items.shop.ShopEntry;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ShopPanel extends JPanel {
    JPanel ShopPanel;

    public ShopPanel(List<ShopEntry> weapons, List<ShopEntry> consumables){
        setLayout(new BorderLayout());

        JLabel title = new JLabel("SHOP", SwingConstants.CENTER);
         title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);


        JPanel itemsContainer = new JPanel(new GridLayout(2, 1, 0, 20));
        itemsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel weaponsRow = createRowPanel(weapons, "Weapons");
        JPanel consumablesRow = createRowPanel(consumables, "Consumables");

        itemsContainer.add(weaponsRow);
        itemsContainer.add(consumablesRow);

         add(itemsContainer, BorderLayout.CENTER);

    }

    private JPanel createRowPanel(List<ShopEntry> items, String labelName){
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());

        JLabel rowLabel = new JLabel(labelName);
        rowLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        rowPanel.add(rowLabel, BorderLayout.NORTH);


        JPanel itemsGrid = new JPanel(new GridLayout(1, 5, 15, 0));

        for (ShopEntry entry : items) {
            itemsGrid.add(createItemBox(entry));
        }

        rowPanel.add(itemsGrid, BorderLayout.CENTER);
        return rowPanel;
    }

    private JPanel createItemBox(ShopEntry entry){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setPreferredSize(new Dimension(120, 80));

        JLabel name = new JLabel(entry.getItem().getName(),SwingConstants.CENTER);
        JLabel quantity = new JLabel("Qty" + entry.getQuantity(), SwingConstants.CENTER);
        JLabel price = new JLabel("Price: " + entry.getPrice(), SwingConstants.CENTER);
        panel.add(name);
        panel.add(quantity);
        panel.add(price);

        return panel;
    
    
    }

}
