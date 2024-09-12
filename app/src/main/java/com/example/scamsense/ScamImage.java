package com.example.scamsense;

public class ScamImage {
    private boolean isScam;
    private String fileLocation;
    private String overlayFileLocation;
    private String subtext;
    private String scamIndicators;
    private String title;


    private boolean completed;
    private boolean answer;

    // Default constructor
    public ScamImage() {
        this.isScam = false;
        this.fileLocation = "";
        this.overlayFileLocation = "";
        this.subtext = "";
        this.scamIndicators = "";
        this.title = "";

        // these variables are always false placeholders, these will be set later.
        this.completed = false;
        this.answer = false;
    }

    // Parameterized constructor
    public ScamImage(boolean isScam, String fileLocation, String overlayFileLocation, String subtext, String scamIndicators, String title) {
        this.isScam = isScam;
        this.fileLocation = fileLocation;
        this.overlayFileLocation = overlayFileLocation;
        this.subtext = subtext;
        this.scamIndicators = scamIndicators;
        this.title = title;

        // these variables are always false placeholders, these will be set later.
        this.completed = false;
        this.answer = false;
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
    public String getSubtext() { return subtext; }

    // Getter for subtext
    public String getScamIndicators() {
        return scamIndicators;
    }

    // Getter for subtext
    public String getTitle() { return title; }

    // Getter for scam status
    public boolean getScamStatus() {
        return isScam;
    }

    // Setter for completed
    public void setCompleted(boolean comp){ completed = comp; }

    // Setter for answer
    public void setAnswer(boolean answer) { this.answer = answer;}

    // Getter for completed
    public boolean getCompleted(){return completed;}

    // Getter for answer
    public boolean getAnswer(){return answer;}
}
