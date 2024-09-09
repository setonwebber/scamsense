package com.example.scamsense;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageButton;

public class activity_levelComplete extends AppCompatActivity {

    private ImageButton menuButton;
    private ImageButton homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_complete);
        homeButton = findViewById(R.id.back_to_home);

        homeButton.setOnClickListener(v-> {
            Intent intent=new Intent(activity_levelComplete.this, MainActivity.class);
            startActivity(intent);
        });
    }
}