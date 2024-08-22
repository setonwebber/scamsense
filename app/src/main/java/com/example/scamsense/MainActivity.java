package com.example.scamsense;

import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView scamImage;
    private Button scamButton;
    private Button safeButton;
    private Button nextButton;
    private Button informationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scamImage = findViewById(R.id.scamImage);
        scamButton = findViewById(R.id.btnScam);
        safeButton = findViewById(R.id.btnSafe);
        //nextButton = findViewById(R.id.btnNext);
        //informationButton = findViewById(R.id.btnInformation);

        scamImagesVec scamImages = new scamImagesVec();
        Log.d("PRINTCONSOLE", "object create");
        scamImages.loadVector(getAssets());
        Log.d("PRINTCONSOLE", "loadvector");
        loadImageFromAssets(scamImage, scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getFileLocation());
        Log.d("PRINTCONSOLE", "loadimage");

        scamButton.setOnClickListener(v -> {
            Log.d("PRINTCONSOLE", String.valueOf(scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getScamStatus()));
            Log.d("PRINTCONSOLE", scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getSubtext());
            if (scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getScamStatus()){
                // some type of comfirmation that the answer was correct
                Log.d("PRINTCONSOLE", "scam button pressed, answer correct.");
            } else{
                // some type of comfirmation that the answer was incorrect
                Log.d("PRINTCONSOLE", "scam button pressed, answer incorrect.");
            }
            loadImageFromAssets(scamImage, scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getOverlayFileLocation());
            // updateScamImage();
        });

        safeButton.setOnClickListener(v -> {
            Log.d("PRINTCONSOLE", String.valueOf(scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getScamStatus()));
            Log.d("PRINTCONSOLE", scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getSubtext());
            if (!scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getScamStatus()){
                // some type of comfirmation that the answer was correct
                Log.d("PRINTCONSOLE", "safe button pressed, answer correct.");
            } else{
                // some type of comfirmation that the answer was incorrect
                Log.d("PRINTCONSOLE", "safe button pressed, answer incorrect.");
            }

            loadImageFromAssets(scamImage, scamImages.getScamImages().get(scamImages.getCurrentImageIndex()).getOverlayFileLocation());
            // make the next button visible.
            // make the information button visible.
        });

//        nextButton.setOnClickListener(v -> {
//            nextImage();
//            loadImageFromAssets(scamImage, getFileLocation());
//            // make the next button invisible.
//            // make the information button invisible.
//        });
//
//        informationButton.setOnClickListener(v -> {
//            // make the information{getSubtext()} visible in a nice area.
//
//            // if click again, hide the information.
//        });
    }

    public void loadImageFromAssets(ImageView scamImageView, String filePath) {
        AssetManager assetManager = getAssets();
        try {
            // open the file from assets using the AssetManager
            InputStream istr = assetManager.open(filePath);
            // decode the input stream to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(istr);
            // set the Bipmap to the ImageView
            scamImageView.setImageBitmap(bitmap);
            istr.close();
        } catch (IOException e) {
            // catch for a java exception (just in case)
            e.printStackTrace();
        }
    }
}