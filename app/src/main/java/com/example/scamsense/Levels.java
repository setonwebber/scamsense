package com.example.scamsense;

import java.util.ArrayList;
import java.util.List;

public class Levels {
    private List<Level> levelList;
    private Level currentLevel;

    public Levels() {
        levelList = new ArrayList<>();
    }

    public void addLevel(Level level) {
        levelList.add(level);
    }

    public Level getLevel(int index) {
        if (index >= 0 && index < levelList.size()) {
            return levelList.get(index);
        }
        return null;
    }

    public void setCurrentLevel(int index) {
        if (index >= 0 && index < levelList.size()) {
            currentLevel = levelList.get(index);
            System.out.println("Current level set to Level " + (index + 1));
        } else {
            System.out.println("Invalid level index");
        }
    }

    public void resetRightAnswers() {
        for (Level level : levelList) {
            if (level != null) {
                level.setRightAnswers(0);
            }
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public List<Level> getlevelList() {
        return levelList;
    }

}
