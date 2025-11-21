package cpsc224;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import resources.weaponSprites;
public class TestWeaponSprites {

    public static void main(String[] args) {
        weaponSprites sprites = new weaponSprites();

        // Example: grab the tile at row 0, column 0
        BufferedImage tile = sprites.getItem(0, 0);

        // Create a simple window to display the tile
        JFrame frame = new JFrame("Sprite Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100); // enough to show 32x32 tile

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
