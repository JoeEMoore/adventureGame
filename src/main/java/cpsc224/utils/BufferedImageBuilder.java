package cpsc224.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Constructs a BufferedImage
 */
public class BufferedImageBuilder {

    private BufferedImage image;

    public BufferedImageBuilder(BufferedImage image) {
        this.image = image;
    }

    public BufferedImageBuilder(String path) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public ImageIcon toImageIcon() {
        return new ImageIcon(image);
    }

    /**
     * Flips the image horizontally.
     */
    public BufferedImageBuilder flipHorizontally() {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the pixel from the original image at (width - 1 - x, y)
                // and set it to the flipped image at (x, y)
                flippedImage.setRGB(x, y, image.getRGB(width - 1 - x, y));
            }
        }

        image = flippedImage;
        return this;
    }

    public BufferedImageBuilder scale(int scaleX, int scaleY) {
        // scaling returns a normal Image
        Image scaledImage = image.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);

        // Draw the Image back to BufferedImage
        image = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = image.createGraphics();
        bGr.drawImage(scaledImage, 0, 0, null);
        bGr.dispose();

        return this;
    }

    public BufferedImageBuilder sliceToSprite(int tileWidth, int tileHeight, int row, int col) {
        image = image.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);

        return this;
    }
}
