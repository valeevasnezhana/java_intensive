package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = "--white=")
    private String whiteColor;

    @Parameter(names = "--black=")
    private String blackColor;

    public String getWhiteColor() {
        return whiteColor;
    }

    public String getBlackColor() {
        return blackColor;
    }
}
