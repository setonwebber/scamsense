package com.example.scamsense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class activity_level extends AppCompatActivity {
    private ImageView scamImage;
    private ImageButton scamButton;
    private ImageButton safeButton;
    private ImageButton informationButton;
    private ImageButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize all values
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        scamImage = findViewById(R.id.scamImage);
        scamButton = findViewById(R.id.btnScam);
        safeButton = findViewById(R.id.btnSafe);
        informationButton = findViewById(R.id.btnInformation);
        menuButton = findViewById(R.id.menuButton);

        dataManager dataManager = com.example.scamsense.dataManager.getInstance();
        scamImagesVec scamImages = dataManager.getScamImages();
        levels levels = dataManager.getLevels();

        // load the screen
        loadScreen(scamImages);

        // when the scam button is clicked.
        scamButton.setOnClickListener(v -> {
            scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());
            scamButton.setImageResource(R.drawable.scam_clicked);
            safeButton.setImageResource(R.drawable.safe);

            if (currentImage.getScamStatus()){
                levels.getCurrentLevel().setRightAnswers(levels.getCurrentLevel().getRightAnswers() + 1);
                currentImage.setAnswer(true);
            } else{
                currentImage.setAnswer(false);
            }

            currentImage.setCompleted(true);
            popup_window(scamImages, v);
        });

        // when the safe button is clicked.
        safeButton.setOnClickListener(v -> {
            scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());
            scamButton.setImageResource(R.drawable.scam);
            safeButton.setImageResource(R.drawable.safe_clicked);

            if (!currentImage.getScamStatus()){
                levels.getCurrentLevel().setRightAnswers(levels.getCurrentLevel().getRightAnswers() + 1);
                currentImage.setAnswer(true);
            } else {
                currentImage.setAnswer(false);
            }

            currentImage.setCompleted(true);
            popup_window(scamImages, v);
        });

        informationButton.setOnClickListener(v -> {
            // a how to play instead. ?
        });

        menuButton.setOnClickListener(v-> {
            Intent intent=new Intent(activity_level.this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void loadScreen(scamImagesVec scamImages){
        scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());

        // If the current image hasnt been completed before.
        if (!currentImage.getCompleted()){
            // Set scam and safe buttons to default values.
            scamButton.setClickable(true);
            scamButton.setImageResource(R.drawable.scam);
            safeButton.setClickable(true);
            safeButton.setImageResource(R.drawable.safe);

            // load image
            loadImageFromAssets(scamImage, currentImage.getFileLocation());

            // set info button to inactive
            informationButton.setClickable(false);
        } else{
            // turn scam and safe buttons off
            scamButton.setClickable(false);
            safeButton.setClickable(false);

            // load overlay image
            loadImageFromAssets(scamImage, currentImage.getOverlayFileLocation());

            // set info button to inactive
            informationButton.setClickable(true);
        }
    }

    @SuppressLint("SetTextI18n")
    public void popup_window(scamImagesVec scamImages, View v){
        scamImage currentImage = scamImages.getScamImages().get(scamImages.getCurrentImageIndex());

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched outside
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                if (scamImages.nextImage()){
                    // because of how functions work, if nextImage is true it wouldve already iterated to the next image, if it's false, it runs the else and doesnt iterate, funny right.
                    loadScreen(scamImages);
                } else {
                    Intent intent=new Intent(activity_level.this, activity_levelComplete.class);

                    startActivity(intent);
                }
                return true;
            }
        });

        // Set the text to the subtext.
        TextView popupText = popupView.findViewById(R.id.popup_text);

        if (currentImage.getAnswer()) {
            popupText.setBackgroundColor(Color.parseColor("#68dd65"));
            popupText.setText("You got this image right!\n\n" + currentImage.getSubtext()); // change this message so its less shitty.
        } else {
            popupText.setBackgroundColor(Color.parseColor("#dd6565"));
            popupText.setText("You got this question wrong!\n\n" + currentImage.getSubtext()); // change this message so its less shitty.
        }
    }

    // function that loads images from assets folder.
    public void loadImageFromAssets(ImageView scamImageView, String filePath) {
        AssetManager assetManager = getAssets();
        try {
            // open the file from assets using the AssetManager
            InputStream inputStream = assetManager.open(filePath);

            // decode the input stream to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // set the Bipmap to the ImageView
            scamImageView.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            // catch for a java exception (just in case)
            e.printStackTrace();
        }
    }
}