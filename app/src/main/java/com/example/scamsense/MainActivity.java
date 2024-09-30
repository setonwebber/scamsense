package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {
    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;

    int levelCount = 3;

    @SuppressLint({"SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init all activiy and viewer attributes
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        ((View) decorView).setSystemUiVisibility(uiOptions);

        // init datamanger
        dataManager dataManager = com.example.scamsense.dataManager.getInstance();

        // init all views from xml activity
        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);

        // load the levels for the application
        dataManager.getLevels.loadLevels();

        // if level1button is clicked
        level1Button.setOnClickListener(v ->{
            // load the level selected
            loadLevel(dataManager, 1);
        });

        // if level2button is clicked
        level2Button.setOnClickListener(v ->{
            // load the level selected
            loadLevel(dataManager, 2);
        });

        // if level3button is clicked
        level3Button.setOnClickListener(v ->{
            // load the level selected
            loadLevel(dataManager, 3);
        });
    }

    public void loadLevel(dataManager dataManager, int level){
        // clear the scamimages list and reload it
        dataManager.getScamImages().clearAll(); dataManager.getScamImages().loadImages(getAssets(), 29);

        // reset the rightanswers value for all levels and set the current level 
        dataManager.getLevels().resetRightAnswers(); dataManager.getLevels().setCurrentLevel(level - 1);

        // clear questionsimages and load questionsimages with the level selected
        dataManager.getQuestionImages().clearAll(); dataManager.getQuestionImages().loadQuestions(dataManager.getLevels().getCurrentLevel().getQuestions(), dataManager.getScamImages());

        // start the levelactivity
        Intent intent=new Intent(MainActivity.this, LevelActivity.class);
        startActivity(intent);
    }
}