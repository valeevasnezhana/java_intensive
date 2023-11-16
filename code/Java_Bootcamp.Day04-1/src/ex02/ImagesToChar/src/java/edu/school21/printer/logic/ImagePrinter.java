package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePrinter {
    public static void printImage(String whiteColor, String blackColor) {
        try {
            BufferedImage image =
                    ImageIO.read(new File("src/resources/it.bmp"));

            ColoredPrinter cp = new ColoredPrinter.Builder(0, false).build();

            int width = image.getWidth();
            int height = image.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixelColor = image.getRGB(x, y);
                    String color =
                            (pixelColor == Color.WHITE.getRGB()) ? whiteColor :
                                    blackColor;
                    cp.print(" ", Ansi.Attribute.NONE,
                            Ansi.FColor.valueOf(color),
                            Ansi.BColor.valueOf(color));
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println(
                    "Error reading the input image: " + e.getMessage());
        }
    }
}