package cpsc224.views.buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import cpsc224.Game;
import cpsc224.creatures.Player;
import cpsc224.levels.Coordinate;
import cpsc224.levels.rooms.BossRoom;
import cpsc224.levels.rooms.Room;
import cpsc224.levels.rooms.shop.ShopRoom;
import cpsc224.utils.BufferedImageBuilder;

public class RoomButton extends JButton {

    private Room[][] rooms;
    private Coordinate position;
    Room room;

    public RoomButton(Room[][] rooms, Coordinate position) {
        this.rooms = rooms;
        this.position = position;
        room = rooms[position.getRow()][position.getCol()];
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public void updateDisplay() {
        Coordinate playerPosition = Game.getInstance().getPlayer().getCurrentPosition();
        setEnabled(false);
        setOpaque(false);

        // -----------------------
        // Adjacent rooms are moveable
        // -----------------------
        if (room != null && position.isAdjacent(playerPosition))
            setEnabled(true);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (room == null)
            return;

        int imageX = (getWidth() - 128) / 2;
        int imageY = (getHeight() - 128) / 2;

        // set image to room if explored, fog if only discovered
        if (room.isExplored()) {
            Image roomImage = rotate(baseRoomImage(position), computeRotation(position)).getImage();
            g.drawImage(roomImage, 0, 0, getWidth(), getHeight(), this);

            // add item icon if room has items and is explored
            if (room.hasItems()) {
                g.drawImage(loadSprite(2, 4), imageX, imageY, 128, 128, this);
            }
        } else if (room.isDiscovered()) {
            Image fog = loadImage("/images/rooms/undiscovered.png");
            g.drawImage(fog, 0, 0, getWidth(), getHeight(), this);
        }

        // boss and shop icons
        if (room.isDiscovered()) {
            if (room instanceof BossRoom) {
                g.drawImage(loadSprite(15, 6), imageX, imageY, 128, 128, this);
            } else if (room instanceof ShopRoom) {
                g.drawImage(loadSprite(24, 3), imageX, imageY, 128, 128, this);
            }
        }

        // player icon
        Player player = Game.getInstance().getPlayer();
        if (position.equals(player.getCurrentPosition()))
            g.drawImage(player.getIcon().getImage(), imageX, imageY, 128, 128, this);
    }

    private Image loadImage(String path) {
        return new BufferedImageBuilder(path).getImage();
    }

    private Image loadSprite(int row, int col) {
        BufferedImageBuilder imageBuilder = new BufferedImageBuilder("/sprites/items/items.png");
        return imageBuilder
                .sliceToSprite(32, 32, row, col)
                .scale(128, 128)
                .getImage();
    }

    private ImageIcon rotate(Image image, double degrees) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);

        BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rotated.createGraphics();

        g2.rotate(Math.toRadians(degrees), w / 2.0, h / 2.0);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return new ImageIcon(rotated);
    }

    private boolean hasUp(int r, int c)    { return r > 0 && rooms[r-1][c] != null; }
    private boolean hasDown(int r, int c)  { return r < rooms.length - 1 && rooms[r+1][c] != null; }
    private boolean hasLeft(int r, int c)  { return c > 0 && rooms[r][c-1] != null; }
    private boolean hasRight(int r, int c) { return c < rooms.length - 1 && rooms[r][c+1] != null; }

    private Image baseRoomImage(Coordinate coordinate) {
        int row = coordinate.getRow();
        int col = coordinate.getCol();

        boolean up = hasUp(row, col);
        boolean down = hasDown(row, col);
        boolean left = hasLeft(row, col);
        boolean right = hasRight(row, col);

        int numDoors = (up ? 1 : 0 ) + (down ? 1 : 0) + (left ? 1 : 0) + (right ? 1 : 0);

        return switch (numDoors) {

            case 1 -> {
                if (up)      yield loadImage("/images/rooms/oneDoor_Up.png");
                if (down)      yield loadImage("/images/rooms/oneDoor_Up.png");     // rotated in computeRotation
                if (left)      yield loadImage("/images/rooms/oneDoor_Right.png");  // rotated in computeRotation
                if (right)    yield loadImage("/images/rooms/oneDoor_Right.png");
                yield null;
            }

            case 2 -> {
                // Opposite
                if (up && down)      yield loadImage("/images/rooms/twoDoor_Up_Down.png");
                if (left && right)    yield loadImage("/images/rooms/twoDoor_Left_Right.png");

                // Corner rooms 
                if (up && right)    yield loadImage("/images/rooms/twoDoor_Right_Up.png");
                if (up && left)      yield loadImage("/images/rooms/twoDoor_Left_Up.png");
                if (down && right)    yield loadImage("/images/rooms/twoDoor_Right_Down.png");
                if (down && left)      yield loadImage("/images/rooms/twoDoor_Left_Down.png");

                yield null;
            }

            case 3 -> {
                boolean missingUp    = !up;
                boolean missingDown  = !down;
                boolean missingLeft  = !left;
                boolean missingRight = !right;

                if (missingDown)  yield loadImage("/images/rooms/threeDoor_Missing_Down.png");
                if (missingLeft)  yield loadImage("/images/rooms/threeDoor_Missing_Left.png");

                if (missingUp)    yield loadImage("/images/rooms/threeDoor_Missing_Down.png"); // rotation fixes it
                if (missingRight) yield loadImage("/images/rooms/threeDoor_Missing_Left.png"); // rotation fixes it

                yield null;
            }

            case 4 -> loadImage("/images/rooms/fourDoor.png");

            default -> null;
        };
    }


    private double computeRotation(Coordinate coordinate) {

        int row = coordinate.getRow();
        int col = coordinate.getCol();

        boolean up = hasUp(row, col);
        boolean down = hasDown(row, col);
        boolean left = hasLeft(row, col);
        boolean right = hasRight(row, col);

        int doors = (up ?1:0) + (down ?1:0) + (left ?1:0) + (right ?1:0);

        
        // 1 DOOR — rotation needed
        // -------------------------
        if (doors == 1) {
            if (up)   return 0;
            if (down)   return 180;
            if (right) return 0;
            if (left)   return 180;
        }


        if (doors == 2) {
            return 0;
        }


        if (doors == 3) {

            if (!down)   return 0;    // base Missing_Down image
            if (!left)   return 0;    // base Missing_Left image

            if (!up)   return 180;  // flip Missing_Down
            if (!right) return 180;  // flip Missing_Left
        }

        return 0;
    }
}
