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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



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
        
        if (roomButtons[0][0].getWidth() > 0) {
            System.out.println("Room cell size = " +
            roomButtons[0][0].getWidth() + " x " +
            roomButtons[0][0].getHeight());
        }

        
        for (int r = 0; r < level.getRoomLength(); r++) {
        for (int c = 0; c < level.getRoomLength(); c++) {

            Room room = rooms[r][c];
            JButton btn = roomButtons[r][c];
            Coordinate coord = new Coordinate(r, c);

            btn.setEnabled(false);
            btn.setOpaque(false);
            btn.setIcon(null);
            btn.setDisabledIcon(null);
            btn.setBackground(new Color(0,0,0,0)); // fully transparent

            if (room == null) continue;

            // -----------------------
            // Room is DISCOVERED
            // -----------------------
            if (room.isDiscovered()) {
                btn.setOpaque(true);
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setBorder(BorderFactory.createDashedBorder(Color.RED, 3, 4, 4, false));
            }

            // -----------------------
            // Room is EXPLORED → show door art (unless special room)
            // -----------------------
            if (room.isExplored()) {
                btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

                // Special rooms override door art
                if (room instanceof BossRoom) {
                    ImageIcon boss = getIcon(15, 6);
                    btn.setIcon(boss);
                    btn.setDisabledIcon(boss);
                }
                else if (room instanceof ShopRoom) {
                    ImageIcon shop = getIcon(24, 3);
                    btn.setIcon(shop);
                    btn.setDisabledIcon(shop);
                }
                else {
                    // NORMAL ROOM → show door-layout image
                    ImageIcon base = baseRoomImage(r, c);
                    double rot = computeRotation(r, c);
                    ImageIcon rotated = rotate(base, rot);

                    btn.setIcon(rotated);
                    btn.setDisabledIcon(rotated);
                }
            }

            // -----------------------
            // Adjacent rooms are moveable
            // -----------------------
            if (coord.isAdjacent(playerPosition)) {
                btn.setEnabled(true);
                btn.setBackground(Color.YELLOW);
            }

            // -----------------------
            // Player position room
            // -----------------------
            if (coord.equals(playerPosition)) {
                btn.setBackground(Color.GREEN);
            }
        }
    }
        
        
        
        /* 
        for (int i = 0; i < level.getRoomLength(); i++) {   
            for (int j = 0; j < level.getRoomLength(); j++) {
                Room room = rooms[i][j];
                JButton button = roomButtons[i][j];
                Coordinate c = new Coordinate(i, j);

                // default value
                button.setEnabled(false);


                /* 
                if (room != null) {

                // -------------------------
                // NEW: determine door count
                // -------------------------
                int doors = countDoors(i, j);
                ImageIcon roomIcon = null;

                // choose correct room art
                switch (doors) {
                    case 1 -> roomIcon = new ImageIcon(getClass().getResource("/images/rooms/oneDoor.png"));
                    case 2 -> roomIcon = new ImageIcon(getClass().getResource("/images/rooms/twoDoor.png"));
                    case 3 -> roomIcon = new ImageIcon(getClass().getResource("/images/rooms/threeDoor.png"));
                    case 4 -> roomIcon = new ImageIcon(getClass().getResource("/images/rooms/fourDoor.png"));
                }

                // default icon for discovered rooms
                if (roomIcon != null) {
                    button.setIcon(roomIcon);
                    button.setDisabledIcon(roomIcon);
                }
            }
            
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
        }*/
    }

    private ImageIcon getIcon(int row, int col) {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/sprites/items/items.png");
        return imageBuilder
            .sliceToSprite(32, 32, row, col)
            .scale(128, 128)
            .toImageIcon();
    }

    private ImageIcon loadImage(String path) {
        return new ImageIcon(getClass().getResource(path));
    }

        private ImageIcon rotate(ImageIcon icon, double degrees) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rotated.createGraphics();

        g2.rotate(Math.toRadians(degrees), w / 2.0, h / 2.0);
        g2.drawImage(icon.getImage(), 0, 0, null);
        g2.dispose();
        return new ImageIcon(rotated);
    }

    private boolean hasUp(int r, int c)    { return r > 0 && rooms[r-1][c] != null; }
    private boolean hasDown(int r, int c)  { return r < rooms.length - 1 && rooms[r+1][c] != null; }
    private boolean hasLeft(int r, int c)  { return c > 0 && rooms[r][c-1] != null; }
    private boolean hasRight(int r, int c) { return c < rooms.length - 1 && rooms[r][c+1] != null; }

    private ImageIcon baseRoomImage(int r, int c) {

        boolean u = hasUp(r,c);
        boolean d = hasDown(r,c);
        boolean l = hasLeft(r,c);
        boolean rgt = hasRight(r,c);

        int doors = (u?1:0) + (d?1:0) + (l?1:0) + (rgt?1:0);

        return switch (doors) {
            case 1 -> loadImage("/images/rooms/oneDoor.png");

            case 2 -> {
                // opposite doors → straight hallway
                boolean opposite = (u && d) || (l && rgt);
                if (opposite) {
                    yield loadImage("/images/rooms/twoDoor_opposite.png");
                }
                // corner hallway
                yield loadImage("/images/rooms/twoDoor_adjacent.png");
            }

            case 3 -> loadImage("/images/rooms/threeDoor.png");
            case 4 -> loadImage("/images/rooms/fourDoor.png");
            default -> null;
        };
    }

    private double computeRotation(int r, int c) {

        boolean u = hasUp(r,c);
        boolean d = hasDown(r,c);
        boolean l = hasLeft(r,c);
        boolean rgt = hasRight(r,c);
        int doors = (u?1:0) + (d?1:0) + (l?1:0) + (rgt?1:0);

        // 1 door → rotate so the door faces its neighbor
        if (doors == 1) {
            if (rgt) return 0;
            if (d) return 90;
            if (l) return 180;
            if (u) return 270;
        }

        // 2 adjacent → rotate corner piece
        if (doors == 2 && !((u && d) || (l && rgt))) {
            if (u && rgt) return 0;
            if (rgt && d) return 90;
            if (d && l) return 180;
            if (l && u) return 270;
        }

        // 2 opposite → rotate straight hallway
        if (doors == 2) {
            if (l && rgt) return 0;    // vertical
            if (u && d) return 90; // horizontal
        }

        // 3 door → rotate so missing door points DOWN
        if (doors == 3) {
            if (!d) return 0;
            if (!l) return 90;
            if (!u) return 180;
            if (!rgt) return 270;
        }

        return 0;
    }

    private int countDoors(int r, int c) {
        int doors = 0;

        if (r > 0 && rooms[r-1][c] != null) doors++;                 // up
        if (r < rooms.length - 1 && rooms[r+1][c] != null) doors++;  // down
        if (c > 0 && rooms[r][c-1] != null) doors++;                 // left
        if (c < rooms.length - 1 && rooms[r][c+1] != null) doors++;  // right

        return doors;
    }   
}
