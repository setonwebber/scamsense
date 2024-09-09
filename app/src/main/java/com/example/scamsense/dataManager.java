package com.example.scamsense;

public class dataManager {
    private static dataManager instance;
    private scamImagesVec scamImagesVec;
    private levels levels;

    private dataManager() {
        scamImagesVec = new scamImagesVec();
        levels = new levels();
    }

    public static synchronized dataManager getInstance() {
        if (instance == null) {
            instance = new dataManager();
        }
        return instance;
    }

    public scamImagesVec getScamImages() {
        return scamImagesVec;
    }

    public levels getLevels() {
        return levels;
    }
}
