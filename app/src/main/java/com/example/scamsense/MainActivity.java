package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;

    int levelCount = 3;

    @SuppressLint({"SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dataManager dataManager = com.example.scamsense.dataManager.getInstance();

        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);

        int[] questionCount = {5, 10, 20};
        // Loop to create levelCount number of Level objects
        for (int i = 0; i < levelCount; i++) {
            Level level = new Level(questionCount[i]);
            dataManager.getLevels().addLevel(level);
        }

        level1Button.setOnClickListener(v ->{
            loadLevel(dataManager, 1);
        });

        level2Button.setOnClickListener(v ->{
            loadLevel(dataManager, 2);
        });

        level3Button.setOnClickListener(v ->{
            loadLevel(dataManager, 3);
        });
    }

    public void loadLevel(dataManager dataManager, int level){
        dataManager.getScamImages().clearAll();
        dataManager.getScamImages().loadImages(getAssets(), 19);
        dataManager.getLevels().resetRightAnswers();
        dataManager.getLevels().setCurrentLevel(level - 1);
        dataManager.getScamImages().loadQuestions(dataManager.getLevels().getCurrentLevel().getQuestions());

        Intent intent=new Intent(MainActivity.this, LevelActivity.class);
        startActivity(intent);
    }
}