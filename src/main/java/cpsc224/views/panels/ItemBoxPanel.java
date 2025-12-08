package cpsc224.views.panels;

import cpsc224.creatures.Player;
import cpsc224.levels.rooms.shop.ShopEntry;

import javax.swing.*;
import java.awt.*;

public class ItemBoxPanel extends JPanel implements GamePanel {

    private final ShopEntry entry;
    private final Player shopper;
    private final GamePanel mapPanel;
    private final GamePanel shopPanel;

    JLabel nameLabel;
    JLabel quantityLabel;
    JLabel priceLabel;
    JButton buyButton;

    public ItemBoxPanel(ShopEntry entry, Player shopper, GamePanel mapPanel, GamePanel shopPanel) {
        this.entry = entry;
        this.shopper = shopper;
        this.mapPanel = mapPanel;
        this.shopPanel = shopPanel;

        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initComponents() {
        nameLabel = new JLabel(entry.getItem().getIcon());
        nameLabel.setToolTipText("<html><p width=\"150\">" + entry.getItem().getToolTipText() + "</p></html>");
        quantityLabel = new JLabel("Qty: " + entry.getQuantity(), SwingConstants.CENTER);
        priceLabel = new JLabel("Price: " + entry.getPrice(), SwingConstants.CENTER);
        buyButton = new JButton("BUY");
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setPreferredSize(new Dimension(220, 120));
    }

    private void layoutComponents() {
        setLayout(new GridLayout(2,1));
        add(nameLabel);
        add(quantityLabel);
        add(priceLabel);
        add(buyButton);
    }

    private void addListeners() {
        buyButton.addActionListener(e->{

//            if(entry.getQuantity() <= 0){
//                JOptionPane.showMessageDialog(this, "Sold out!");
//                return;
//            }
//
//            if (shopper.getGold() < entry.getPrice()) {
//                JOptionPane.showMessageDialog(this, "Not enough gold!");
//                return;
//            }

            if (shopper.getInventory().addItem(entry.getItem())) {
                entry.decreaseQuantity();
                shopper.subractGold(entry.getPrice());
                mapPanel.updateDisplay();
                shopPanel.updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "No room in your inventory", "No Room", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void updateDisplay() {
        quantityLabel.setText("Qty: " + entry.getQuantity());
        priceLabel.setText("Price: " + entry.getPrice());
        buyButton.setEnabled(entry.getQuantity() > 0 && shopper.getGold() >= entry.getPrice());
    }

    public JButton getBuyButton() {
        return buyButton;
    }

    public JLabel getPriceLabel() {
        return priceLabel;
    }

    public JLabel getQuantityLabel() {
        return quantityLabel;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public ShopEntry getEntry() {
        return entry;
    }
}
