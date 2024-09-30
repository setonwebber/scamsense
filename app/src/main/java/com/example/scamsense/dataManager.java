package com.example.scamsense;

public class dataManager {
    private static dataManager instance;
    private ScamImages ScamImages;
    private Levels levels;
    private QuestionImages QuestionImages;

    private dataManager() {
        ScamImages = new ScamImages();
        levels = new Levels();
        QuestionImages = new QuestionImages();
    }

    public static synchronized dataManager getInstance() {
        if (instance == null) {
            instance = new dataManager();
        }
        return instance;
    }

    public ScamImages getScamImages() {
        return ScamImages;
    }

    public Levels getLevels() {
        return levels;
    }

    public QuestionImages getQuestionImages() {return QuestionImages;}
}
