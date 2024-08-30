package com.example.scamsense;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class scamImagesVec {
    private List<scamImage> scamImages;
    private int currentImageIndex;
    private int imageCount;

    // Default constructor
    public scamImagesVec() {
        this.scamImages = new ArrayList<>();
        this.currentImageIndex = 0;
        this.imageCount = 2;
    }

    // Method to load images into the vector
    public void loadVector(AssetManager assetManager) {
        Log.d("PRINTCONSOLE", "loadVector variables loaded.");

        for (int i = 1; i <= imageCount; i++) {
            String directoryPath = "scamImages/" + i;
            Log.d("PRINTCONSOLE", "Attempting to load assets from: " + directoryPath);

            try {
                boolean isScam = false;
                String fileLocation = directoryPath + "/scam.png";
                String overlayFileLocation = directoryPath + "/overlay.png";
                String subtext = "";

                // Open the text file
                InputStream inputStream = assetManager.open(directoryPath + "/text.txt");
                Log.d("PRINTCONSOLE", "input stream opened.");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.d("PRINTCONSOLE", "Text file opened.");

                // Read the first line to determine if it's a scam
                String firstLine = reader.readLine();
                if (firstLine != null && firstLine.length() >= 13 && firstLine.substring(9, 13).equals("true")) {
                    isScam = true;
                }

                // Read the rest of the file and append to subtext
                String line;
                StringBuilder subtextBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    subtextBuilder.append(line).append("\n");
                }
                subtext = subtextBuilder.toString();

                // Close the reader
                reader.close();

                // Create a ScamImage object and add it to the list
                scamImage currentScam = new scamImage(isScam, fileLocation, overlayFileLocation, subtext);
                scamImages.add(currentScam);
                Log.d("PRINTCONSOLE", "ScamImage loaded and added to list.");

            } catch (IOException e) {
                Log.e("PRINTCONSOLE", "Error loading assets from " + directoryPath, e);
            }
        }
    }

    // Method to move to the next image
    public void nextImage() {
        currentImageIndex = currentImageIndex + 1;
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
