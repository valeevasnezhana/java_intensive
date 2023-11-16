ImagesToChar - Console Image Printer

Description

This Java application converts a black-and-white BMP image into a console representation using
 specified characters for white and black pixels.

Usage

1. To compile the application, navigate to the project's root folder(ImagesToChar) and run
the following command:

    javac -d target -cp "lib/*" src/java/edu/school21/printer/app/ImageToCharApp.java src/java/edu/school21/printer/logic/ImagePrinter.java src/java/edu/school21/printer/logic/Args.java


2. Unpack libs to target

    cd target
    jar xf ../lib/jcommander-1.72.jar
    jar xf ../lib/JCDP-4.0.0.jar
    rm -rf META-INF
    cd ..


2. Make jar with manifest.txt

    jar cfm target/images-to-chars-printer.jar src/manifest.txt -C src/resources . -C target .


3. Run:

    java -jar target/images-to-chars-printer.jar --white= BLACK --black= RED
    <run_application>                            <white_replace> <black_replace>