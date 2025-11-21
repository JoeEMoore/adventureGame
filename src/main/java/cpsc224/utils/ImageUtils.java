package cpsc224.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageUtils {

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
     * Flips an image horizontally
     * @param originalImage
     * @return
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
}
