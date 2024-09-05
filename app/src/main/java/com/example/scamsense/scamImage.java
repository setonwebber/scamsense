package com.example.scamsense;

public class scamImage {
    private boolean isScam;
    private String fileLocation;
    private String overlayFileLocation;
    private String subtext;
    private boolean completed;
    private boolean answer;

    // Default constructor
    public scamImage() {
        this.isScam = false;
        this.fileLocation = "";
        this.overlayFileLocation = "";
        this.subtext = "";
        this.completed = false;
        this.answer = false;
    }

    // Parameterized constructor
    public scamImage(boolean isScam, String fileLocation, String overlayFileLocation, String subtext) {
        this.isScam = isScam;
        this.fileLocation = fileLocation;
        this.overlayFileLocation = overlayFileLocation;
        this.subtext = subtext;
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
    public String getSubtext() {
        return subtext;
    }

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
