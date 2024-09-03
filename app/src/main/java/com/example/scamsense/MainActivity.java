package com.example.scamsense;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static scamImagesVec scamImages = new scamImagesVec();

    private ImageButton level1Button;
    private ImageButton level2Button;
    private ImageButton level3Button;

    levels level1 = new levels(5);
    levels level2 = new levels(10);
    levels level3 = new levels(20);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        level1Button = findViewById(R.id.level1Button);

        // load all the stars according to the users wins, this will be called everytime the user goes back to the main screen

        level1Button.setOnClickListener(v ->{
            // loads scamimages vector
            scamImages.loadVector(getAssets(), 1);
            Intent intent=new Intent(MainActivity.this, activity_level.class);
            startActivity(intent);
        });


    }
}