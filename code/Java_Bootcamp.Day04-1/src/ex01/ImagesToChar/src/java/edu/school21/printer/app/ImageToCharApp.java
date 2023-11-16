package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

public class ImageToCharApp {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(
                    "Usage: java ImageToCharApp <image_path> <white_char> <black_char>");
            return;
        }

        String imagePath = args[0];
        char whiteChar = args[1].charAt(0);
        char blackChar = args[2].charAt(0);

        char[][] imageCharArray =
                ImageConverter.convertImageToChar(imagePath, whiteChar,
                        blackChar);

        if (imageCharArray != null) {
            for (char[] row : imageCharArray) {
                for (char pixel : row) {
                    System.out.print(pixel);
                }
                System.out.println();
            }
        }
    }
}