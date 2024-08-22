package com.example.scamsense;

public class scamImage {
    private boolean isScam;
    private String fileLocation;
    private String overlayFileLocation;
    private String subtext;

    // Default constructor
    public scamImage() {
        this.isScam = false;
        this.fileLocation = "";
        this.overlayFileLocation = "";
        this.subtext = "";
    }

    // Parameterized constructor
    public scamImage(boolean isScam, String fileLocation, String overlayFileLocation, String subtext) {
        this.isScam = isScam;
        this.fileLocation = fileLocation;
        this.overlayFileLocation = overlayFileLocation;
        this.subtext = subtext;
    }

    // Getter for fileLocation
    public String getFileLocation() {
        return fileLocation;
    }

    // Getter for overlayFileLocation
    public String getOverlayFileLocation() {
        return overlayFileLocation;
    }

    // Getter for subtext
    public String getSubtext() {
        return subtext;
    }

    // Getter for scam status
    public boolean getScamStatus() {
        return isScam;
    }
}
