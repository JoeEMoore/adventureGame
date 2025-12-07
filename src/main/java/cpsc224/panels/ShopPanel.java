package cpsc224.panels;
import cpsc224.creatures.Player;
import cpsc224.levels.rooms.shop.ShopEntry;
import cpsc224.levels.rooms.shop.ShopRoom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ShopPanel extends JPanel implements GamePanel {
    private final Player player;
    private GamePanel gamePanel;
    private List<ItemBoxPanel> itemBoxPanels = new ArrayList<>();

    public ShopPanel(ShopRoom shop, Player player, GamePanel gamePanel){
        this.player = player;
        this.gamePanel = gamePanel;
        
        setLayout(new BorderLayout());

        JLabel title = new JLabel("SHOP", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);


        JPanel itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel weaponsRow = createRowPanel(shop.getWeaponEntries(), "Weapons");
        JPanel consumablesRow = createRowPanel(shop.getConsumableEntries(), "Consumables");

        itemsContainer.add(weaponsRow);
        itemsContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        itemsContainer.add(consumablesRow);

        add(itemsContainer, BorderLayout.CENTER);
        updateDisplay();
    }

    private JPanel createRowPanel(Collection<ShopEntry> items, String labelName){
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());

        JLabel rowLabel = new JLabel(labelName);
        rowLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        rowPanel.add(rowLabel, BorderLayout.NORTH);


        JPanel itemsGrid = new JPanel(new GridLayout(1, items.size(), 15, 0));

        for (ShopEntry entry : items) {
            ItemBoxPanel panel = new ItemBoxPanel(entry, player, gamePanel, this);
            itemsGrid.add(panel);
            itemBoxPanels.add(panel);
        }

        rowPanel.add(itemsGrid, BorderLayout.CENTER);
        return rowPanel;
    }

    @Override
    public void updateDisplay() {
        for (ItemBoxPanel panel : itemBoxPanels) {
            panel.updateDisplay();
        }
    }
}
