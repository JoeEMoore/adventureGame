package cpsc224.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

    public static ImageIcon getImageIcon(Object o, String path, int scaleX, int scaleY, boolean flipHorizontally) throws NullPointerException {
        ImageIcon icon = new ImageIcon(o.getClass().getClassLoader().getResource(path));
        Image image = icon.getImage();

        // flip image horizontally
        if (flipHorizontally) {
            image = flipHorizontally(toBufferedImage(image));
        }

        // scale image
        Image scaledImage = image.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);

        icon.setImage(scaledImage);
        return icon;
    }

    public static ImageIcon getImageIcon(Object o, String path) {
        return getImageIcon(o, path, 1, 1, false);
    }

    public static ImageIcon getImageIcon(Object o, String path, int scaleX, int scaleY) {
        return getImageIcon(o, path, scaleX, scaleY, false);
    }

/**
 * Converts a given Image into a BufferedImage
 *
 * @param img The Image to be converted
 * @return The converted BufferedImage
 */
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    /**
     * Flips an image horizontally.
     * @param originalImage the image to flip
     * @return the flipped image
     */
    public static BufferedImage flipHorizontally(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, originalImage.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the pixel from the original image at (width - 1 - x, y)
                // and set it to the flipped image at (x, y)
                flippedImage.setRGB(x, y, originalImage.getRGB(width - 1 - x, y));
            }
        }
        return flippedImage;
    }

     public static BufferedImage loadSheet(String resourcePath) {
        try {
            BufferedImage sheet = ImageIO.read(ImageUtils.class.getResource(resourcePath));
            if (sheet == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
/**
     * Slices a sprite sheet into a 2D array of tiles
     * @param sheet the loaded sprite sheet
     * @param tileWidth width of a single tile
     * @param tileHeight height of a single tile
     * @param rows number of rows
     * @param cols number of columns
     * @return 2D array [row][col] of BufferedImages
     */
    public static BufferedImage[][] sliceSheet(BufferedImage sheet, int tileWidth, int tileHeight, int rows, int cols) {
        BufferedImage[][] tiles = new BufferedImage[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                tiles[row][col] = sheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
            }
        }
        return tiles;
    }

    public static BufferedImage getItem(BufferedImage[][] tiles, int row, int col) {
    if (tiles == null || row < 0 || row >= tiles.length || col < 0 || col >= tiles[0].length) {
        return null;
    }
    return tiles[row][col];
}


    

}
