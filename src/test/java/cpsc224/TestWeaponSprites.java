package cpsc224;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestWeaponSprites {

    public static void main(String[] args) {
        // Load the sprite sheet
        BufferedImage sheet = ImageUtils.loadBufferedImage("/images/items.png");

        // Slice the sheet into a 2D array of tiles
        BufferedImage[][] tiles = ImageUtils.sliceSheet(sheet, 32, 32, 26, 11);

        // Grab a specific tile (row 0, column 0)
        BufferedImage tile = ImageUtils.getSprite(tiles, 0, 0);

        // Create a simple window to display the tile
        JFrame frame = new JFrame("Sprite Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(150, 150); // enough to show 32x32 tile comfortably

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (tile != null) {
                    g.drawImage(tile, 10, 10, null); // draw at x=10, y=10
                }
            }
        };

        frame.add(panel);
        frame.setVisible(true);
    }
}
