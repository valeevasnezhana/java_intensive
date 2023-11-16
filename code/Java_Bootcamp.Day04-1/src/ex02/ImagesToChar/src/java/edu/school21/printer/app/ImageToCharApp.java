package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.ImagePrinter;


public class ImageToCharApp {

    public static void main(String[] args) {

        Args arguments = new Args();
        JCommander.newBuilder().addObject(arguments).build().parse(args);

        ImagePrinter.printImage(arguments.getWhiteColor(),
                arguments.getBlackColor());
    }
}