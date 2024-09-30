package com.example.scamsense;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionImages {
    private List<ScamImage> QuestionImages;
    private int currentImageIndex;

    // Default constructor
    public QuestionImages() {
        this.QuestionImages = new ArrayList<>();
        this.currentImageIndex = 0;
    }

    public void loadQuestions(int questions, ScamImages scamImages) {
        // reset currentimageindex
        currentImageIndex = 0;

        // make a list that will contain each number previous chosen, make sure there are no duplicates
        List<Integer> indexPrevious = new ArrayList<>();
        for(int i = 1; i <= questions; i++){
            int randomIndex;

            // Generate a random index and check for duplicates
            do {
                randomIndex = new Random().nextInt(scamImages.getScamImages().size());
            } while (indexPrevious.contains(randomIndex)); // If the number is in indexPrevious, reroll

            // Add the random image to the scamImages list
            QuestionImages.add(scamImages.getScamImages().get(randomIndex));

            // Add the index to the set to track previous selections
            indexPrevious.add(randomIndex);
        }
    }

    public void clearAll() {
        for(int i = 0; i <= QuestionImages.size(); i++){
            QuestionImages.get(i).setAnswer(false);
            QuestionImages.get(i).setCompleted(false);
        }
        QuestionImages.clear();
        currentImageIndex = 0;  // Optionally reset currentImageIndex if needed
    }

    // Method to move to the next image
    public boolean nextImage() {
        if (currentImageIndex + 1 == QuestionImages.size()){
            return false;
        } else {
            currentImageIndex = currentImageIndex + 1;
            return true;
        }
    }

    public List<ScamImage> getQuestionImages(){ return QuestionImages;}

    public int getCurrentImageIndex(){return currentImageIndex;};
}
