package com.example.scamsense;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "ScamSensePrefs";
    private static final String KEY_LEVEL_INDEX = "level_index";

    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;

    int levelCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dataManager dataManager = com.example.scamsense.dataManager.getInstance();



        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);

        int[] questionCount = {5, 10, 20, 30, 40, 50};

        // Loop to create levelCount number of Level objects
        for (int i = 0; i < levelCount; i++) {
            level level = new level(questionCount[i]);
            dataManager.getLevels().addLevel(level);
        }

        level1Button.setOnClickListener(v ->{
            dataManager.getScamImages().clearAll();
            dataManager.getScamImages().loadVector(getAssets(), 25);
            dataManager.getLevels().resetRightAnswers();
            // loads scamimages vector
            dataManager.getLevels().setCurrentLevel(0);
            dataManager.getScamImages().loadQuestions(dataManager.getLevels().getCurrentLevel().getQuestions());

            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });

        level2Button.setOnClickListener(v ->{
            dataManager.getScamImages().clearAll();
            dataManager.getScamImages().loadVector(getAssets(), 25);
            dataManager.getLevels().resetRightAnswers();
            dataManager.getLevels().setCurrentLevel(1);
            dataManager.getScamImages().loadQuestions(dataManager.getLevels().getCurrentLevel().getQuestions());
            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });

        level3Button.setOnClickListener(v ->{
            dataManager.getScamImages().clearAll();
            dataManager.getScamImages().loadVector(getAssets(), 25);
            dataManager.getLevels().resetRightAnswers();
            dataManager.getLevels().setCurrentLevel(2);
            dataManager.getScamImages().loadQuestions(dataManager.getLevels().getCurrentLevel().getQuestions());
            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });


    }
}