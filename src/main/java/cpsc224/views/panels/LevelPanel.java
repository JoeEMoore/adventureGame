package cpsc224.views.panels;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cpsc224.creatures.Player;

import cpsc224.levels.Coordinate;
import cpsc224.levels.Level;
import cpsc224.levels.rooms.BossRoom;
import cpsc224.levels.rooms.Room;
import cpsc224.levels.rooms.shop.ShopRoom;
import cpsc224.utils.BufferedImageBuilder;


public class LevelPanel extends JPanel implements GamePanel {

    Level level;
    Player player;
    GamePanel mapPanel;
    Room[][] rooms;
    JButton[][] roomButtons;
    Coordinate playerPosition;

    public LevelPanel(Level level, Player player, GamePanel mapPanel) {
        this.level = level;
        this.player = player;
        this.mapPanel = mapPanel;
        rooms = level.getRooms();
        playerPosition = level.getCurrentPosition();
        
        initComponents();
        layoutComponents();
        addListeners();

        updateDisplay();

    }

    private void initComponents() {
        roomButtons = new JButton[rooms.length][rooms.length];
        setOpaque(false);
    }

    private void layoutComponents() {

        setLayout(new GridLayout(level.getRoomLength(), level.getRoomLength(), 1, 1));

        for (int i = 0; i < level.getRoomLength(); i++) {
            for (int j = 0; j < level.getRoomLength(); j++) {
                JButton button = new JButton();
                button.setOpaque(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setBackground(Color.LIGHT_GRAY);
                roomButtons[i][j] = button;
                add(roomButtons[i][j]);
            }
        }
    }

    private void addListeners() {
        for (int i = 0; i < roomButtons.length; i++) {
            for (int j = 0; j < roomButtons[i].length; j++) {
                JButton button = roomButtons[i][j];
                int row = i;
                int col = j;
                Room room = rooms[i][j];
                if (room == null)
                    continue;

                button.addActionListener(e -> {
                    playerPosition = new Coordinate(row, col);
                    level.setCurrentPosition(playerPosition);

                    // start fight if there is a creature
                    if (room.hasCreature()) {
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        frame.setContentPane(new FightPanel(player, room));
                        frame.revalidate();
                        frame.repaint();
                    }

                    updateDisplay();

                    // open shop dialog
                    if (room instanceof ShopRoom shop){
                        ShopPanel shopPanel = new ShopPanel(shop, player, mapPanel);
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        JDialog shopDialog = new JDialog(frame, "SHOP", true);
                        shopDialog.setContentPane(shopPanel);
                        shopDialog.pack();
                        shopDialog.setLocationRelativeTo(frame);
                        shopDialog.setVisible(true);
                    }
                });
            }
        }
    }

    @Override
    public void updateDisplay() {
        updateRoomButtons();
    }

    public Level getLevel() {
        return level;
    }

    private void updateRoomButtons() {
        for (int i = 0; i < level.getRoomLength(); i++) {   
            for (int j = 0; j < level.getRoomLength(); j++) {
                Room room = rooms[i][j];
                JButton button = roomButtons[i][j];
                Coordinate c = new Coordinate(i, j);

                // default value
                button.setEnabled(false);

                if (room != null) {

                    // if room is discovered
                    if (room.isDiscovered()) {
                        button.setBackground(Color.LIGHT_GRAY);
                        button.setOpaque(true);
                        button.setBorder(BorderFactory.createDashedBorder(Color.RED, 3, 4, 4, false));

                        if (room instanceof BossRoom) {
                            ImageIcon bossIcon = getIcon(15, 6);
                            button.setIcon(bossIcon);
                            button.setDisabledIcon(bossIcon);
                        }
                        if (room instanceof ShopRoom) {
                            ImageIcon shopIcon = getIcon(24, 3);
                            button.setIcon(shopIcon);
                            button.setDisabledIcon(shopIcon);
                        }
                    }

                    // if room is explored
                    if (room.isExplored()) {
                        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

                        // indicate room has items if it is explored and not special room
                        if (!(room instanceof ShopRoom || room instanceof BossRoom)) {
                            if (room.hasItems()) {
                                ImageIcon itemIcon = getIcon(2, 4);
                                button.setIcon(itemIcon);
                                button.setDisabledIcon(itemIcon);
                            } else {
                                button.setIcon(null);
                                button.setDisabledIcon(null);
                            }
                        }
                    }

                    // if room is adjacent to player
                    if (c.isAdjacent(playerPosition)) {
                        button.setEnabled(true);
                        button.setBackground(Color.YELLOW);
                    }

                    // if room is at player's position
                    if (c.equals(playerPosition)) {
                        button.setBackground(Color.GREEN);
                    }
                }
            }
        }
    }

    private ImageIcon getIcon(int row, int col) {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/sprites/items/items.png");
        return imageBuilder
            .sliceToSprite(32, 32, row, col)
            .scale(128, 128)
            .toImageIcon();
    }
}
