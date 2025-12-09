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
import cpsc224.views.buttons.RoomButton;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class LevelPanel extends JPanel implements GamePanel {

    Level level;
    Player player;
    GamePanel mapPanel;
    Room[][] rooms;
    RoomButton[][] roomButtons;
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
        roomButtons = new RoomButton[rooms.length][rooms.length];
        setOpaque(false);
    }

    private void layoutComponents() {

        setLayout(new GridLayout(level.getRoomLength(), level.getRoomLength(), 1, 1));

        for (int i = 0; i < level.getRoomLength(); i++) {
            for (int j = 0; j < level.getRoomLength(); j++) {
                RoomButton button = new RoomButton(rooms, new Coordinate(i, j));
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

        for (int r = 0; r < level.getRoomLength(); r++) {
            for (int c = 0; c < level.getRoomLength(); c++) {
                roomButtons[r][c].updateDisplay(playerPosition);
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
