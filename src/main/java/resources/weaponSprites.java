package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class weaponSprites {

    private BufferedImage sheet;
    private BufferedImage[][] items; //array [row][column]

    private final int TILE_WIDTH = 32;
    private final int TILE_HEIGHT = 32;
    private final int ROWS = 26;
    private final int COLS = 11;

    public weaponSprites() {
        loadSheet("/images/items.png");
        sliceSheet();
    }

    
    private void loadSheet(String imageLocal) {
        try {
            sheet = ImageIO.read(getClass().getResource(imageLocal));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Slice the sprite sheet into individual tiles
    private void sliceSheet() {
        items = new BufferedImage[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                items[row][col] = sheet.getSubimage(
                    col * TILE_WIDTH,
                    row * TILE_HEIGHT,
                    TILE_WIDTH,
                    TILE_HEIGHT
                );
            }
        }
    }

    // Access individual tiles
    public BufferedImage getItem(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return null;
        return items[row][col];
    }
}
