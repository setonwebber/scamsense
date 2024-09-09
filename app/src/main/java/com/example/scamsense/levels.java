package com.example.scamsense;

import java.util.ArrayList;
import java.util.List;

public class levels {
    private List<level> levelList;
    private level currentLevel;

    public levels() {
        levelList = new ArrayList<>();
    }

    public void addLevel(level level) {
        levelList.add(level);
    }

    public level getLevel(int index) {
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

    public level getCurrentLevel() {
        return currentLevel;
    }

    public List<level> getlevelList() {
        return levelList;
    }
}
