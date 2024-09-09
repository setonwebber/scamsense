package com.example.scamsense;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static scamImagesVec scamImages = new scamImagesVec();
    public static levels levels = new levels();

    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;

    int levelCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        scamImages.getScamImages().clear();

        level1Button = findViewById(R.id.level1Button);
        level2Button = findViewById(R.id.level2Button);
        level3Button = findViewById(R.id.level3Button);

        int[] questionCount = {5, 10, 20, 30, 40, 50};

        // Loop to create levelCount number of Level objects
        for (int i = 0; i < levelCount; i++) {
            level level = new level(questionCount[i]);
            levels.addLevel(level);
        }

        // load all the stars according to the users wins, this will be called everytime the user goes back to the main screen

        level1Button.setOnClickListener(v ->{
            // loads scamimages vector
            levels.setCurrentLevel(0);
            scamImages.loadVector(getAssets(), levels.getCurrentLevel().getQuestions());
            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });

        level2Button.setOnClickListener(v ->{
            // loads scamimages vector
            levels.setCurrentLevel(1);
            scamImages.loadVector(getAssets(), levels.getCurrentLevel().getQuestions());
            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });

        level3Button.setOnClickListener(v ->{
            // loads scamimages vector
            levels.setCurrentLevel(2);
            scamImages.loadVector(getAssets(), levels.getCurrentLevel().getQuestions());
            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });


    }
}