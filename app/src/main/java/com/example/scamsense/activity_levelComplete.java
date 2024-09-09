package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_levelComplete extends AppCompatActivity {
    private TextView score;
    private TextView responseText;
    private ImageButton homeButton;
    private ImageView congratsStars;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataManager dataManager = com.example.scamsense.dataManager.getInstance();
        levels levels = dataManager.getLevels();

        setContentView(R.layout.activity_level_complete);

        homeButton = findViewById(R.id.homeButton);
        score = findViewById(R.id.score);
        responseText = findViewById(R.id.responseText);
        congratsStars = findViewById(R.id.congrats_stars);

        float scorePercent = (float) levels.getCurrentLevel().getRightAnswers() / levels.getCurrentLevel().getQuestions();

        score.setText(levels.getCurrentLevel().getRightAnswers() + "/" + levels.getCurrentLevel().getQuestions());

        if (scorePercent == 1.0) {
            responseText.setText("Perfect! Incredible job.");
            congratsStars.setImageResource(R.drawable.congrats_3_stars);
        } else if (scorePercent >= 0.75) {
            responseText.setText("So close! Good Effort.");
            congratsStars.setImageResource(R.drawable.congrats_2_stars);
        } else if (scorePercent >= 0.50) {
            responseText.setText("You're getting there!");
            congratsStars.setImageResource(R.drawable.congrats_2_stars);
        } else if (scorePercent >= 0.25) {
            responseText.setText("Nice Try! Try Again?");
            congratsStars.setImageResource(R.drawable.congrats_1_stars);
        } else {
            responseText.setText("Try again?");
            congratsStars.setImageResource(R.drawable.congrats_0_stars);
        }

        homeButton.setOnClickListener(v-> {
            Intent intent=new Intent(activity_levelComplete.this, MainActivity.class);
            startActivity(intent);
        });
    }
}