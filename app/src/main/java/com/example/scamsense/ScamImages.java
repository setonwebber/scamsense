package com.example.scamsense;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ScamImages {
    private List<ScamImage> ScamImages;
    private int imageCount;

    // Default constructor
    public ScamImages() {
        this.ScamImages = new ArrayList<>();
    }

    // Method to load images into the vector
    public void loadImages(AssetManager assetManager, int imageCount) {
        this.imageCount = imageCount;

        // loop through every subfolder in /scamimages/ (currently hardcoded to iteration imageCount amount of times, we can try change to actually check how many folders there is and go from there).
        for (int i = 1; i <= imageCount; i++) {

            // make a string of the directory path for the subfolder we're currently on
            String directoryPath = "scamImages/" + i;
            // Log.d("PRINTCONSOLE", "Attempting to load assets from: " + directoryPath);

            // try catch just in case lol :3
            try {
                // set variables for the scamImage object
                boolean isScam = false;
                String fileLocation = directoryPath + "/scam.png";
                String overlayFileLocation = directoryPath + "/overlay.png";
                String subtext = "";
                String indicators = "";
                String title = "";

                // set inputstream and reader variables.
                InputStream inputStream;
                BufferedReader reader;

                // open text.txt (contains isScam and title)
                inputStream = assetManager.open(directoryPath + "/text.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // read the first line to determine if it's a scam
                // compare the last 4 characters of the firstline of the text file against true.
                String isScamLine = reader.readLine();
                if (isScamLine != null && isScamLine.length() >= 13 && isScamLine.substring(9, 13).equals("true")) {
                    // change isScam to true.
                    isScam = true;
                }

                // read the second line to determine the title
                String titleLine = reader.readLine();
                titleLine.substring(9);
                title = titleLine;

                // close file, we're done with text.txt
                reader.close();


                // open text.txt (contains isScam and title)
                inputStream = assetManager.open(directoryPath + "/subtext.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // Read subtext.txt to get all the subtext.
                String subtextLines;
                // Loop through, while lines exist (stops when we reach the end).
                while ((subtextLines = reader.readLine()) != null) {
                    // Concatenate each line into the string subtext, each time with a line break.
                    subtext += subtextLines + "\n";
                }

                // close file, we're done with subtext.txt
                reader.close();

                // open text.txt (contains isScam and title)
                inputStream = assetManager.open(directoryPath + "/indicators.txt");
                reader = new BufferedReader(new InputStreamReader(inputStream));

                // read subtext.txt to get all the subtext.
                String indicatorLines;
                // loop through, while lines exist (stops when we reach the end).
                while ((indicatorLines = reader.readLine()) != null) {
                    // concatenate each line into the string subtext, each time with a line break.
                    indicators += indicatorLines + "\n";
                }

                // close file, we're done with indicators.txt
                reader.close();

                // create a ScamImage object and add it to the list
                ScamImage currentScam = new ScamImage(isScam, fileLocation, overlayFileLocation, subtext, indicators, title);
                ScamImages.add(currentScam);

                // Log.d("PRINTCONSOLE", "ScamImage loaded and added to list.");

            } catch (IOException e) {
                Log.e("PRINTCONSOLE", "Error loading assets from " + directoryPath, e);
            }
        }
    }

    // Getter for scamImages list
    public List<ScamImage> getScamImages() {
        return ScamImages;
    }

    public void clearAll() {
        ScamImages.clear();
    }
}
