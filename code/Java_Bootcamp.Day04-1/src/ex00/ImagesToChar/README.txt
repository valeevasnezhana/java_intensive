ImagesToChar - Console Image Printer

Description

This Java application converts a black-and-white BMP image into a console representation using
 specified characters for white and black pixels.

Usage

1. To compile the application, navigate to the project's root folder(ImagesToChar) and run
the following command:

    javac -d target/ src/java/edu/school21/printer/app/ImageToCharApp.java src/java/edu/school21/printer/logic/ImageConverter.java

2. Run:

    java -cp target/ edu.school21.printer.app.ImageToCharApp src/resources/it.bmp . 0
    <run_application>                                        <image_path>         <white_char> <black_char>