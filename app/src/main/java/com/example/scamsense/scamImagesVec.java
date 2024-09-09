package com.example.scamsense;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class scamImagesVec {
    private List<scamImage> scamImages;
    private List<scamImage> scamImagesAssets;
    private int currentImageIndex;
    private int imageCount;

    // Default constructor
    public scamImagesVec() {
        this.scamImages = new ArrayList<>();
        this.scamImagesAssets = new ArrayList<>();  // Initialize the scamImagesAssets list
        this.currentImageIndex = 0;
    }

    // Method to load images into the vector
    public void loadVector(AssetManager assetManager, int imageCount) {
        Log.d("PRINTCONSOLE", "loadVector variables loaded.");
        this.imageCount = imageCount;
        // loop through every subfolder in /scamimages/ (currently hardcoded to iteration imageCount amount of times, we can try change to actually check how many folders there is and go from there).
        for (int i = 1; i <= imageCount; i++) {
            // make a string of the directory path for the subfolder we're currently on
            String directoryPath = "scamImages/" + i;
            Log.d("PRINTCONSOLE", "Attempting to load assets from: " + directoryPath);

            // try catch just in case lol :3
            try {
                // set variables for the scamImage object, isScam and subtext are placehoders for now, where the fileLocations will lead to the files (make sure the naming stays consistent
                boolean isScam = false;
                String fileLocation = directoryPath + "/scam.png";
                String overlayFileLocation = directoryPath + "/overlay.png";
                String subtext = "";

                // open the text file
                InputStream inputStream = assetManager.open(directoryPath + "/text.txt");
                Log.d("PRINTCONSOLE", "input stream opened.");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.d("PRINTCONSOLE", "Text file opened.");

                // read the first line to determine if it's a scam
                String firstLine = reader.readLine();

                // compare the last 4 characters of the firstline of the text file against true.
                if (firstLine != null && firstLine.length() >= 13 && firstLine.substring(9, 13).equals("true")) {
                    // change isScam to true.
                    isScam = true;
                } // else isScam is already set to false so we dont need to do anything here.

                // read the rest of the file and append to subtext
                String line;
                StringBuilder subtextBuilder = new StringBuilder();
                // loop through, while lines exist (stops when we reach the end).
                while ((line = reader.readLine()) != null) {
                    // append each line into the string subtext, each time with a line break.
                    subtextBuilder.append(line).append("\n");
                }
                // turn stringbuilder into a string... (it was like a list thing before idk its weird but it works)
                subtext = subtextBuilder.toString();

                // close the reader
                reader.close();

                // create a ScamImage object and add it to the list
                scamImage currentScam = new scamImage(isScam, fileLocation, overlayFileLocation, subtext);
                scamImagesAssets.add(currentScam);
                Log.d("PRINTCONSOLE", "ScamImage loaded and added to list.");

            } catch (IOException e) {
                Log.e("PRINTCONSOLE", "Error loading assets from " + directoryPath, e);
            }
        }
    }

    public void loadQuestions(int questions ) {
        currentImageIndex = 0;
        Log.d("mainactivityerror", "currentimageindex");
        Set<Integer> indexPrevious = new HashSet<>();
        for(int i = 1; i <= questions; i++){
            int randomIndex;
            Log.d("mainactivityerror", "randomindex");
            // Generate a random index and check for duplicates
            do {
                randomIndex = new Random().nextInt(imageCount);
                Log.d("mainactivityerror", "new random number");// Generate a random number between 0 and imageCount-1
            } while (indexPrevious.contains(randomIndex)); // If the number is in indexPrevious, reroll

            // Add the random image to the scamImages list
            scamImages.add(scamImagesAssets.get(randomIndex));
            Log.d("mainactivityerror", "add random number");

            // Add the index to the set to track previous selections
            indexPrevious.add(randomIndex);
        }
    }
    public void clearQuestions() {
        scamImages.clear();
        scamImagesAssets.clear();
        currentImageIndex = 0;  // Optionally reset currentImageIndex if needed
    }

    public void clearAll() {
        scamImages.clear();
        scamImagesAssets.clear();
        currentImageIndex = 0;  // Optionally reset currentImageIndex if needed
    }

    // Method to move to the next image
    public boolean nextImage() {
        if (currentImageIndex + 1 == scamImages.size()){
            return false;
        } else {
            currentImageIndex = currentImageIndex + 1;
            return true;
        }
    }

    public void backImage(){
        currentImageIndex = currentImageIndex - 1;
    }

    // Getter for currentImageIndex
    public int getCurrentImageIndex() {
        return currentImageIndex;
    }

    // Getter for scamImages list
    public List<scamImage> getScamImages() {
        return scamImages;
    }
}
