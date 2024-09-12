package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelCompleteActivity extends AppCompatActivity {
    private TextView score;
    private TextView responseText;
    private ImageButton homeButton;
    private ImageView congratsStars;

    @SuppressLint({"SetTextI18n", "SourceLockedOrientationActivity"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level_complete);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dataManager dataManager = com.example.scamsense.dataManager.getInstance();
        Levels levels = dataManager.getLevels();

        homeButton = findViewById(R.id.homeButton);
        score = findViewById(R.id.score);
        responseText = findViewById(R.id.responseText);
        congratsStars = findViewById(R.id.congrats_stars);

        // get the percentage of correct answers (use float as its in the decimals)
        float scorePercent = (float) (levels.getCurrentLevel().getRightAnswers() * 100) / levels.getCurrentLevel().getQuestions();

        // set the score text
        score.setText(levels.getCurrentLevel().getRightAnswers() + "/" + levels.getCurrentLevel().getQuestions());

        // set stars/text to the corresponding response according to the score percentage
        if (scorePercent == 100) {
            responseText.setText("Perfect! Incredible job.");
            congratsStars.setImageResource(R.drawable.congrats_3_stars);
        } else if (scorePercent >= 75) {
            responseText.setText("So close! Good Effort.");
            congratsStars.setImageResource(R.drawable.congrats_2_stars);
        } else if (scorePercent >= 50) {
            responseText.setText("You're getting there!");
            congratsStars.setImageResource(R.drawable.congrats_2_stars);
        } else if (scorePercent >= 25) {
            responseText.setText("Nice Try! Try Again?");
            congratsStars.setImageResource(R.drawable.congrats_1_stars);
        } else {
            responseText.setText("Try again?");
            congratsStars.setImageResource(R.drawable.congrats_0_stars);
        }

        homeButton.setOnClickListener(v-> {
            Intent intent=new Intent(LevelCompleteActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}