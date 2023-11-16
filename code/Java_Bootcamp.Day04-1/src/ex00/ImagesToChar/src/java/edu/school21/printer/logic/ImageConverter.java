package edu.school21.printer.logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageConverter {

    public static char[][] convertImageToChar(String imagePath, char whiteChar, char blackChar) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();

            char[][] charArray = new char[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    charArray[y][x] = (rgb == Color.WHITE.getRGB()) ? whiteChar : blackChar;
                }
            }

            return charArray;
        } catch (IOException e) {
            System.err.println("Error reading the image: " + e.getMessage() + imagePath);
            return null;
        }
    }
}

